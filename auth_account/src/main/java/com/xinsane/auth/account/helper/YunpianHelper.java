package com.xinsane.auth.account.helper;

import com.xinsane.auth.account.transfer.ApiResult;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class YunpianHelper {

    @Value("${config.yunpian.apikey}")
    private String apikey;

    private YunpianClient client;

    @PostConstruct
    private void init() {
        client = new YunpianClient(apikey).init();
    }

    /**
     * 智能匹配模板接口发短信
     * @param phone 接受的手机号
     * @param text  短信内容
     */
    private ApiResult sendSms(String phone, String text) {
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, text);
        Result<SmsSingleSend> r = client.sms().single_send(param);
        if (r.isSucc())
            return new ApiResult();
        return new ApiResult(r.getCode(), r.getMsg());
    }

    /**
     * 通过接口发送短信验证码
     * @param phone 接受的手机号
     * @param code  验证码
     */
    public ApiResult sendSmsCode(String phone, String code) {
        return sendSms(phone, "【梦的天空之城】您的验证码是" + code + "。如非本人操作，请忽略本短信");
    }
}
