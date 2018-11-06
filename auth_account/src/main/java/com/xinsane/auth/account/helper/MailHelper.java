package com.xinsane.auth.account.helper;

import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

@Component
public class MailHelper {
    @Value("${config.mail.from.name}")
    private String fromName;
    @Value("${config.mail.from.address}")
    private String fromAddress;

    private final JavaMailSender mailSender;
    @Autowired
    public MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public ApiResult sendTextMail(String to, String subject, String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setFrom(MimeUtility.encodeText(fromName) + " <" + fromAddress + ">");
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            mailSender.send(simpleMailMessage);
        } catch (UnsupportedEncodingException | MailException e) {
            e.printStackTrace();
            return new ApiResult(1, e.getMessage());
        }
        return new ApiResult();
    }

    public ApiResult sendCodeMail(String to, String code) {
        return sendTextMail(to, "【梦的天空之城】验证码", "【梦的天空之城】您的验证码为：" + code +
                "。如非本人操作，请忽略本邮件。");
    }
}
