package com.xinsane.auth.account.controller;

import com.xinsane.auth.account.entity.AuthSiteEntity;
import com.xinsane.auth.account.entity.LoginAuthTokenEntity;
import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.filter.CsrfFilter;
import com.xinsane.auth.account.helper.StringHelper;
import com.xinsane.auth.account.repository.LoginAuthTokenRepository;
import com.xinsane.auth.account.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class UserController implements CsrfFilter.CsrfInterface {

    private final SiteRepository siteRepository;
    private final LoginAuthTokenRepository loginAuthTokenRepository;

    @Autowired
    public UserController(SiteRepository siteRepository, LoginAuthTokenRepository loginAuthTokenRepository) {
        this.siteRepository = siteRepository;
        this.loginAuthTokenRepository = loginAuthTokenRepository;
    }

    @RequestMapping("login")
    public String login(Model model) {
        return "user/login";
    }

    @RequestMapping("register")
    public String register(Model model) {
        return "user/register";
    }

    @RequestMapping("reset_password")
    public String reset_password(Model model) {
        return "user/reset-password";
    }

    @RequestMapping("admin")
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping("auth_redirect")
    public String auth_redirect(@RequestParam int site_id,
                                @RequestParam String redirect_url,
                                RedirectAttributes attr,
                                HttpSession session) throws Exception {
        AuthSiteEntity site = siteRepository.queryFirstBySiteId(site_id);
        if (site == null)
            throw new Exception("site id not found");

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            attr.addAttribute("login", "false");
        else {
            String token = StringHelper.randomString(32);
            loginAuthTokenRepository.save(new LoginAuthTokenEntity()
                    .setUserId(user.getUserId())
                    .setTokenCode(token)
                    // 生成的 token 5分钟内有效
                    .setExpireTime(new Timestamp(System.currentTimeMillis() + 5*60*1000)));
            attr.addAttribute("login", "true");
            attr.addAttribute("token", token);
        }
        return "redirect:" + site.getSiteUrl() + redirect_url;
    }

}
