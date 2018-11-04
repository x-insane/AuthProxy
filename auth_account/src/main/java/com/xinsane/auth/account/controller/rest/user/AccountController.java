package com.xinsane.auth.account.controller.rest.user;

import com.xinsane.auth.account.entity.*;
import com.xinsane.auth.account.entity_helper.AuthHelper;
import com.xinsane.auth.account.helper.CheckFormatHelper;
import com.xinsane.auth.account.repository.AuthRepository;
import com.xinsane.auth.account.repository.ConfigRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class AccountController {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final AuthHelper authHelper;
    private final ConfigRepository configRepository;

    @Autowired
    public AccountController(UserRepository userRepository, AuthHelper authHelper,
                             AuthRepository authRepository, ConfigRepository configRepository) {
        this.userRepository = userRepository;
        this.authHelper = authHelper;
        this.authRepository = authRepository;
        this.configRepository = configRepository;
    }

    @RequestMapping("login")
    public ApiResult login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        UserEntity user;
        if (CheckFormatHelper.checkEmail(username))
            user = userRepository.findFirstByEmail(username);
        else if (CheckFormatHelper.checkPhone(username))
            user = userRepository.findFirstByPhone(username);
        else
            user = userRepository.findFirstByUsername(username);

        if (user == null || !user.getPassword().equals(password))
            return new ApiResult(403, "账号或密码错误");
        if (!userRepository.hasAuth(user.getUserId(), "login"))
            return new ApiResult(401, "该账号已被禁止登录");

        session.setAttribute("user", user);

        return new ApiResult();
    }

    @RequestMapping("register")
    public ApiResult register(@RequestParam String username, @RequestParam String password, HttpSession session) {
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
    public ApiResult modify_password() {
        return new ApiResult();
    }

    @RequestMapping("bind_phone_send_message")
    public ApiResult bind_phone_send_message() {
        return new ApiResult();
    }

    @RequestMapping("bind_phone_by_code")
    public ApiResult bind_phone_by_code() {
        return new ApiResult();
    }

    @RequestMapping("bind_email_send_message")
    public ApiResult bind_email_send_message() {
        return new ApiResult();
    }

    @RequestMapping("bind_email_by_code")
    public ApiResult bind_email_by_code() {
        return new ApiResult();
    }

    @RequestMapping("reset_password_notify_phone")
    public ApiResult reset_password_notify_phone() {
        return new ApiResult();
    }

    @RequestMapping("reset_password_notify_email")
    public ApiResult reset_password_notify_email() {
        return new ApiResult();
    }

    @RequestMapping("reset_password_by_code")
    public ApiResult reset_password_by_code() {
        return new ApiResult();
    }
}
