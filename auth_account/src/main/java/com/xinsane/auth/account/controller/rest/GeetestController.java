package com.xinsane.auth.account.controller.rest;

import com.geetest.sdk.GeetestLib;
import com.xinsane.auth.account.helper.direct.RequestHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/geetest")
public class GeetestController {

    @Value("${config.geetest.id}")
    private String geetest_id;

    @Value("${config.geetest.key}")
    private String geetest_key;

    @RequestMapping("start_captcha")
    public String start_captcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(geetest_id, geetest_key, true);

        HttpSession session = request.getSession();
        String userId = session.getId();

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("client_type", "web");
        // web:电脑上的浏览器；
        // h5:手机上的浏览器，包括移动应用内完全内置的web_view；
        // native：通过原生SDK植入APP应用的方式
        param.put("ip_address", RequestHelper.getIpAddress(request)); // 传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        session.setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userId设置到session中
        session.setAttribute("geetest_userid", userId);

        return gtSdk.getResponseStr();
    }

    public boolean verify_captcha(GeetestLib.RequestData data, HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(geetest_id, geetest_key, true);

        String challenge = data.getChallenge();
        String validate = data.getValidate();
        String secCode = data.getSecCode();

        HttpSession session = request.getSession();
        // 从session中获取gt-server状态
        Integer server_status = (Integer) session.getAttribute(gtSdk.gtServerStatusSessionKey);
        if (server_status == null)
            return false;
        int gt_server_status_code = server_status;
        //从session中获取userId
        String userId = (String)session.getAttribute("geetest_userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId); // 网站用户id
        param.put("client_type", "web");
        // web:电脑上的浏览器；
        // h5:手机上的浏览器，包括移动应用内完全内置的web_view；
        // native：通过原生SDK植入APP应用的方式
        param.put("ip_address", RequestHelper.getIpAddress(request)); // 传输用户请求验证时所携带的IP

        int gtResult;
        if (gt_server_status_code == 1) {
            // gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhancedValidateRequest(challenge, validate, secCode, param);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, secCode);
        }

        return gtResult == 1;
    }
}
