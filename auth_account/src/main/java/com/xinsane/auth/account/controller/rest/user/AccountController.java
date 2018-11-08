package com.xinsane.auth.account.controller.rest.user;

import com.geetest.sdk.GeetestLib;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xinsane.auth.account.controller.rest.GeetestController;
import com.xinsane.auth.account.entity.*;
import com.xinsane.auth.account.entity_helper.AuthHelper;
import com.xinsane.auth.account.helper.CheckFormatHelper;
import com.xinsane.auth.account.helper.MailHelper;
import com.xinsane.auth.account.helper.StringHelper;
import com.xinsane.auth.account.helper.YunpianHelper;
import com.xinsane.auth.account.repository.AuthRepository;
import com.xinsane.auth.account.repository.ConfigRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class AccountController {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final AuthHelper authHelper;
    private final ConfigRepository configRepository;
    private final YunpianHelper yunpianHelper;
    private final MailHelper mailHelper;
    private final GeetestController geetestController;

    @Autowired
    public AccountController(UserRepository userRepository, AuthHelper authHelper,
                             AuthRepository authRepository, ConfigRepository configRepository,
                             YunpianHelper yunpianHelper, MailHelper mailHelper,
                             GeetestController geetestController) {
        this.userRepository = userRepository;
        this.authHelper = authHelper;
        this.authRepository = authRepository;
        this.configRepository = configRepository;
        this.yunpianHelper = yunpianHelper;
        this.geetestController = geetestController;
        this.mailHelper = mailHelper;
    }

    @RequestMapping("get_user_info")
    public ApiResult get_user_info(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        return ApiResult.builder()
        .set("user", ApiResult.element()
            .set("username", user.getUsername())
            .set("phone", user.getPhone())
            .set("email", user.getEmail())
        )
        .set("auth", userRepository.getUserAuth(user.getUserId()))
        .build();
    }

    @RequestMapping("login")
    public ApiResult login(@RequestParam String username, @RequestParam String password,
                           HttpServletRequest request, HttpSession session) {
//        if (!geetestController.verify_captcha(GeetestLib.RequestData.loadFromParam(request), request))
//            return new ApiResult(403, "未通过人机验证");

        UserEntity user = userRepository.findUserByLoginName(username);
        if (user == null || !user.getPassword().equals(password))
            return new ApiResult(403, "账号或密码错误");
        if (!userRepository.hasAuth(user.getUserId(), "login"))
            return new ApiResult(401, "该账号已被禁止登录");

        session.setAttribute("user", user);

        return new ApiResult();
    }

    @RequestMapping("register")
    public ApiResult register(@RequestParam String username, @RequestParam String password,
                              HttpServletRequest request, HttpSession session) {
        if (!geetestController.verify_captcha(GeetestLib.RequestData.loadFromParam(request), request))
            return new ApiResult(403, "未通过人机验证");

        String open_register = configRepository.value("open_register");
        if (open_register == null || !open_register.equals("true"))
            return new ApiResult(403, "暂不允许用户注册");

        if (!CheckFormatHelper.checkUsername(username) || username.length() < 2 || username.length() > 16)
            return new ApiResult(401, "该用户名不符合规范，请使用2-16个汉字、字母、数字、-和_的组合");

        UserEntity user = userRepository.findFirstByUsername(username);
        if (user != null)
            return new ApiResult(402, "该用户名已被注册");

        user = userRepository.save(new UserEntity().setUsername(username).setPassword(password));
        authHelper.addAuthWithoutCheck(user.getUserId(), authRepository.getAuthIdByAuthKey("login"));
        session.setAttribute("user", user);

        return new ApiResult();
    }

    @RequestMapping("modify_password")
    public ApiResult modify_password(@RequestBody String json, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        String old_password = object.get("old_password").getAsString();
        String new_password = object.get("new_password").getAsString();
        if (user.getPassword().equals(old_password))
            userRepository.save(user.setPassword(new_password));
        else
            return new ApiResult(403, "旧密码错误");
        return new ApiResult();
    }

    @RequestMapping("bind_phone_send_message")
    public ApiResult bind_phone_send_message(@RequestBody String json, HttpSession session,
                                             HttpServletRequest request) {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        if (session.getAttribute("bind_phone_captcha_pass") == null) {
            if (geetestController.verify_captcha(GeetestLib.RequestData.loadFromJson(object), request))
                session.setAttribute("bind_phone_captcha_pass", true);
            else
                return new ApiResult(403, "未通过人机验证");
        }

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        if (user.getPhone() != null)
            return new ApiResult(405, "您的账号已绑定手机号：" + user.getPhone());

        String phone = object.get("phone").getAsString();
        if (!CheckFormatHelper.checkPhone(phone))
            return new ApiResult(401, "手机号格式不正确");
        if (userRepository.existsByPhone(phone))
            return new ApiResult(402, "该手机号已绑定过账号");

        String old_phone = (String) session.getAttribute("bind_phone");
        String code = (String) session.getAttribute("bind_phone_code");
        if (!phone.equals(old_phone) || code == null) {
            code = StringHelper.randomNumberCode6();
            session.setAttribute("bind_phone", phone);
            session.setAttribute("bind_phone_code", code);
        }

        return yunpianHelper.sendSmsCode(phone, code);
    }

    @RequestMapping("bind_phone_by_code")
    public ApiResult bind_phone_by_code(@RequestBody String json, HttpSession session,
                                        HttpServletRequest request) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        if (user.getPhone() != null)
            return new ApiResult(405, "您的账号已绑定手机号：" + user.getPhone());

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        String phone = object.get("phone").getAsString();
        String code = object.get("code").getAsString();
        if (!phone.equals(session.getAttribute("bind_phone")))
            return new ApiResult(403, "请先获取验证码");
        if (userRepository.existsByPhone(phone))
            return new ApiResult(402, "该手机号已绑定过账号");
        if (!code.equals(session.getAttribute("bind_phone_code"))) {
            Object times = session.getAttribute("bind_phone_code_error_times");
            int bind_phone_code_error_times = 0;
            if (times != null)
                bind_phone_code_error_times = (int) times;
            bind_phone_code_error_times ++;
            if (bind_phone_code_error_times > 5) {
                session.removeAttribute("bind_phone");
                session.removeAttribute("bind_phone_code");
                session.removeAttribute("bind_phone_code_error_times");
                return new ApiResult(201, "请重新获取验证码");
            }
            session.setAttribute("bind_phone_code_error_times", bind_phone_code_error_times);
            return new ApiResult(403, "验证码错误");
        }

        session.removeAttribute("bind_phone");
        session.removeAttribute("bind_phone_code");
        session.removeAttribute("bind_phone_code_error_times");
        session.removeAttribute("bind_phone_captcha_pass");
        userRepository.save(user.setPhone(phone));
        return new ApiResult();
    }

    @RequestMapping("bind_email_send_message")
    public ApiResult bind_email_send_message(@RequestBody String json, HttpSession session,
                                             HttpServletRequest request) {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        if (session.getAttribute("bind_email_captcha_pass") == null) {
            if (geetestController.verify_captcha(GeetestLib.RequestData.loadFromJson(object), request))
                session.setAttribute("bind_email_captcha_pass", true);
            else
                return new ApiResult(403, "未通过人机验证");
        }

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        if (user.getEmail() != null)
            return new ApiResult(405, "您的账号已绑定邮箱：" + user.getEmail());

        String email = object.get("email").getAsString();
        if (!CheckFormatHelper.checkEmail(email))
            return new ApiResult(401, "邮箱格式不正确");
        if (userRepository.existsByEmail(email))
            return new ApiResult(402, "该邮箱已绑定过账号");

        String old_email = (String) session.getAttribute("bind_email");
        String code = (String) session.getAttribute("bind_email_code");
        if (!email.equals(old_email) || code == null) {
            code = StringHelper.randomNumberCode6();
            session.setAttribute("bind_email", email);
            session.setAttribute("bind_email_code", code);
        }

        return mailHelper.sendCodeMail(email, code);
    }

    @RequestMapping("bind_email_by_code")
    public ApiResult bind_email_by_code(@RequestBody String json, HttpSession session,
                                        HttpServletRequest request) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "请先登录");
        if (user.getEmail() != null)
            return new ApiResult(405, "您的账号已绑定邮箱：" + user.getEmail());

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        String email = object.get("email").getAsString();
        String code = object.get("code").getAsString();
        if (!email.equals(session.getAttribute("bind_email")))
            return new ApiResult(403, "请先获取验证码");
        if (userRepository.existsByEmail(email))
            return new ApiResult(402, "该手机号已绑定过账号");
        if (!code.equals(session.getAttribute("bind_email_code"))) {
            Object times = session.getAttribute("bind_email_code_error_times");
            int bind_email_code_error_times = 0;
            if (times != null)
                bind_email_code_error_times = (int) times;
            bind_email_code_error_times ++;
            if (bind_email_code_error_times > 5) {
                session.removeAttribute("bind_email");
                session.removeAttribute("bind_email_code");
                session.removeAttribute("bind_email_code_error_times");
                return new ApiResult(201, "请重新获取验证码");
            }
            session.setAttribute("bind_email_code_error_times", bind_email_code_error_times);
            return new ApiResult(403, "验证码错误");
        }

        session.removeAttribute("bind_email");
        session.removeAttribute("bind_email_code");
        session.removeAttribute("bind_email_code_error_times");
        session.removeAttribute("bind_email_captcha_pass");
        userRepository.save(user.setEmail(email));
        return new ApiResult();
    }

    @RequestMapping("reset_password/start")
    public ApiResult start_reset_password(@RequestParam String username,
                                          HttpServletRequest request, HttpSession session) {
        if (!geetestController.verify_captcha(GeetestLib.RequestData.loadFromParam(request), request))
            return new ApiResult(403, "未通过人机验证");

        UserEntity user = userRepository.findUserByLoginName(username);
        if (user == null)
            return new ApiResult(404, "该账号不存在");
        if (!userRepository.hasAuth(user.getUserId(), "login"))
            return new ApiResult(401, "该账号已被禁止登录");

        session.setAttribute("reset_password_user_id", user.getUserId());
        return new ApiResult();
    }

    @RequestMapping("reset_password/notify_phone")
    public ApiResult reset_password_notify_phone(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("reset_password_user_id");
        if (userId == null)
            return new ApiResult(201, "请先完成账号选择");
        UserEntity user = userRepository.findFirstByUserId(userId);
        if (user == null)
            return new ApiResult(404, "该账号已被删除");
        if (user.getPhone() == null)
            return new ApiResult(403, "该账号没有绑定手机号");

        String code = (String) session.getAttribute("reset_password_code");
        if (code == null) {
            code = StringHelper.randomNumberCode6();
            session.setAttribute("reset_password_code", code);
        }
        return yunpianHelper.sendSmsCode(user.getPhone(), code);
    }

    @RequestMapping("reset_password/notify_email")
    public ApiResult reset_password_notify_email(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("reset_password_user_id");
        if (userId == null)
            return new ApiResult(201, "请先完成账号选择");
        UserEntity user = userRepository.findFirstByUserId(userId);
        if (user == null)
            return new ApiResult(404, "该账号已被删除");
        if (user.getEmail() == null)
            return new ApiResult(403, "该账号没有绑定邮箱");

        String code = (String) session.getAttribute("reset_password_code");
        if (code == null) {
            code = StringHelper.randomNumberCode6();
            session.setAttribute("reset_password_code", code);
        }
        return mailHelper.sendCodeMail(user.getEmail(), code);
    }

    @RequestMapping("reset_password/code")
    public ApiResult reset_password_by_code(@RequestParam String code, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("reset_password_user_id");
        if (userId == null)
            return new ApiResult(101, "请先完成账号选择");
        UserEntity user = userRepository.findFirstByUserId(userId);
        if (user == null)
            return new ApiResult(404, "该账号已被删除");

        String bind_code = (String) session.getAttribute("reset_password_code");
        if (bind_code == null)
            return new ApiResult(403, "请先获取验证码");
        Integer error_times = (Integer) session.getAttribute("reset_password_code_error_times");
        if (error_times == null)
            error_times = 0;
        if (!bind_code.equals(code)) {
            error_times = error_times + 1;
            if (error_times >= 5) {
                session.removeAttribute("reset_password_code");
                session.removeAttribute("reset_password_code_error_times");
                return new ApiResult(201, "请重新获取验证码");
            }
            session.setAttribute("reset_password_code_error_times", error_times);
            return new ApiResult(403, "验证码错误");
        }

        session.setAttribute("reset_password_auth", true);
        return new ApiResult();
    }

    @RequestMapping("reset_password/submit")
    public ApiResult reset_password_submit(@RequestParam String password, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("reset_password_user_id");
        if (userId == null)
            return new ApiResult(101, "请先完成账号选择");
        UserEntity user = userRepository.findFirstByUserId(userId);
        if (user == null)
            return new ApiResult(404, "该账号已被删除");
        if (session.getAttribute("reset_password_auth") == null)
            return new ApiResult(403, "未通过验证");

        userRepository.save(user.setPassword(password));
        session.removeAttribute("reset_password_auth");
        session.removeAttribute("reset_password_user_id");
        return new ApiResult();
    }
}
