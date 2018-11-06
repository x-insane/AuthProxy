package com.geetest.sdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * Java SDK
 *
 */
public class GeetestLib {
    private final String apiUrl = "http://api.geetest.com";
    private final String json_format = "1";

    /**
     * 极验验证二次验证表单数据 chllenge
     */
    public static final String fn_geetest_challenge = "geetest_challenge";

    /**
     * 极验验证二次验证表单数据 validate
     */
    public static final String fn_geetest_validate = "geetest_validate";

    /**
     * 极验验证二次验证表单数据 seccode
     */
    public static final String fn_geetest_seccode = "geetest_seccode";

    /**
     * 公钥
     */
    private String captchaId;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 是否开启新的failback
     */
    private boolean newFailback;

    /**
     * 返回字符串
     */
    private String responseStr = "";

    /**
     * 调试开关，是否输出调试日志
     */
    public boolean debugCode = true;

    /**
     * 极验验证API服务状态Session Key
     */
    public String gtServerStatusSessionKey = "gt_server_status";

    /**
     * 带参数构造函数
     */
    public GeetestLib(String captchaId, String privateKey, boolean newFailback) {
        this.captchaId = captchaId;
        this.privateKey = privateKey;
        this.newFailback = newFailback;
    }

    /**
     * 获取本次验证初始化返回字符串
     * @return 初始化结果
     */
    public String getResponseStr() {
        return responseStr;
    }

//    public String getVersionInfo() {
//        return "4.0";
//    }

    /**
     * 预处理失败后的返回格式串
     */
    private String getFailPreProcessRes() {
        Long rnd1 = Math.round(Math.random() * 100);
        Long rnd2 = Math.round(Math.random() * 100);
        String md5Str1 = md5Encode(rnd1 + "");
        String md5Str2 = md5Encode(rnd2 + "");
        String challenge = md5Str1 + md5Str2.substring(0, 2);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", 0);
        jsonObject.addProperty("gt", this.captchaId);
        jsonObject.addProperty("challenge", challenge);
        jsonObject.addProperty("new_captcha", this.newFailback);

        return jsonObject.toString();
    }

    /**
     * 预处理成功后的标准串
     *
     */
    private String getSuccessPreProcessRes(String challenge) {
        gtlog("challenge:" + challenge);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", 1);
        jsonObject.addProperty("gt", this.captchaId);
        jsonObject.addProperty("challenge", challenge);

        return jsonObject.toString();
    }

    /**
     * 验证初始化预处理
     * @return 1表示初始化成功，0表示初始化失败
     */
    public int preProcess(HashMap<String, String> data) {
        if (registerChallenge(data) != 1) {
            this.responseStr = this.getFailPreProcessRes();
            return 0;
        }
        return 1;
    }

    /**
     * 用captchaID进行注册，更新challenge
     * @return 1表示注册成功，0表示注册失败
     */
    private int registerChallenge(HashMap<String, String>data) {
        try {
            String userId = data.get("user_id");
            String clientType = data.get("client_type");
            String ipAddress = data.get("ip_address");

            String registerUrl = "/register.php";
            String getUrl = apiUrl + registerUrl + "?";
            String param = "gt=" + this.captchaId + "&json_format=" + this.json_format;

            if (userId != null)
                param = param + "&user_id=" + userId;
            if (clientType != null)
                param = param + "&client_type=" + clientType;
            if (ipAddress != null)
                param = param + "&ip_address=" + ipAddress;

            gtlog("GET_URL:" + getUrl + param);
            String result_str = readContentFromGet(getUrl + param);
            if ("fail".equals(result_str)) {
                gtlog("gtServer register challenge failed");
                return 0;
            }

            gtlog("result:" + result_str);
            JsonObject jsonObject = new JsonParser().parse(result_str).getAsJsonObject();
            String return_challenge = jsonObject.get("challenge").getAsString();
            gtlog("return_challenge:" + return_challenge);

            if (return_challenge.length() == 32) {
                this.responseStr = this.getSuccessPreProcessRes(this.md5Encode(return_challenge + this.privateKey));
                return 1;
            }
            else {
                gtlog("gtServer register challenge error");
                return 0;
            }
        } catch (Exception e) {
            gtlog(e.toString());
            gtlog("exception:register api");
        }
        return 0;
    }

    /**
     * 判断一个表单对象值是否为空
     */
    private boolean objHasContent(Object gtObj) {
        return gtObj != null && gtObj.toString().trim().length() != 0;
    }

    /**
     * 检查客户端的请求是否合法,三个只要有一个为空，则判断不合法
     */
    private boolean requestIsLegal(String challenge, String validate, String secCode) {
        return objHasContent(challenge) || objHasContent(validate) || objHasContent(secCode);
    }


    /**
     * 服务正常的情况下使用的验证方式,向gt-server进行二次验证,获取验证结果
     * @return 验证结果,1表示验证成功,0表示验证失败
     */
    public int enhancedValidateRequest(String challenge, String validate, String secCode, HashMap<String, String> data) {
        if (!requestIsLegal(challenge, validate, secCode))
            return 0;

        gtlog("request legitimate");

        String userId = data.get("user_id");
        String clientType = data.get("client_type");
        String ipAddress = data.get("ip_address");

        String validateUrl = "/validate.php";
        String postUrl = this.apiUrl + validateUrl;
        String param = String.format("challenge=%s&validate=%s&seccode=%s&json_format=%s",
                challenge, validate, secCode, this.json_format);

        if (userId != null)
            param = param + "&user_id=" + userId;
        if (clientType != null)
            param = param + "&client_type=" + clientType;
        if (ipAddress != null)
            param = param + "&ip_address=" + ipAddress;

        gtlog("param:" + param);

        String response = "";
        try {
            if (validate.length() <= 0)
                return 0;
            if (!checkResultByPrivate(challenge, validate))
                return 0;

            gtlog("checkResultByPrivate");
            response = readContentFromPost(postUrl, param);
            gtlog("response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject return_map = new JsonParser().parse(response).getAsJsonObject();
        String return_sec_code = return_map.get("seccode").getAsString();

        gtlog("md5: " + md5Encode(return_sec_code));
        return return_sec_code.equals(md5Encode(secCode)) ? 1 : 0;
    }

    /**
     * failback使用的验证方式
     * @return 验证结果,1表示验证成功,0表示验证失败
     */
    public int failbackValidateRequest(String challenge, String validate, String seccode) {
        gtlog("in failback validate");
        if (!requestIsLegal(challenge, validate, seccode))
            return 0;
        gtlog("request legitimate");
        return 1;
    }

    /**
     * 输出debug信息，需要开启debugCode
     */
    private void gtlog(String message) {
        if (debugCode)
            System.out.println("gtlog: " + message);
    }

    private boolean checkResultByPrivate(String challenge, String validate) {
        String encodeStr = md5Encode(privateKey + "geetest" + challenge);
        return validate.equals(encodeStr);
    }

    /**
     * 发送GET请求，获取服务器返回结果
     * @return 服务器返回结果
     */
    private String readContentFromGet(String URL) throws IOException {
        URL getUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();

        connection.setConnectTimeout(2000); // 设置连接主机超时（单位：毫秒）
        connection.setReadTimeout(2000); // 设置从主机读取数据超时（单位：毫秒）

        // 建立与服务器的连接，并未发送数据
        connection.connect();

        return getHttpResponseString(connection);
    }

    /**
     * 发送POST请求，获取服务器返回结果
     * @return 服务器返回结果
     */
    private String readContentFromPost(String URL, String data) throws IOException {

        gtlog(data);
        URL postUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) postUrl
                .openConnection();

        connection.setConnectTimeout(2000);// 设置连接主机超时（单位：毫秒）
        connection.setReadTimeout(2000);// 设置从主机读取数据超时（单位：毫秒）
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // 建立与服务器的连接，并未发送数据
        connection.connect();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        outputStreamWriter.write(data);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        return getHttpResponseString(connection);
    }

    private String getHttpResponseString(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == 200) {
            // 发送数据到服务器并使用Reader读取返回的数据
            StringBuilder sBuffer = new StringBuilder();

            byte[] buf = new byte[1024];
            InputStream inStream = connection.getInputStream();
            for (int n; (n = inStream.read(buf)) != -1;)
                sBuffer.append(new String(buf, 0, n, "UTF-8"));
            inStream.close();
            connection.disconnect(); // 断开连接

            return sBuffer.toString();
        }
        else
            return "fail";
    }

    /**
     * md5 加密
     */
    private String md5Encode(String plainText) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    public static class RequestData {
        private String challenge;
        private String validate;
        private String secCode;

        public static RequestData loadFromParam(HttpServletRequest request) {
            RequestData data = new RequestData();
            data.challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
            data.validate = request.getParameter(GeetestLib.fn_geetest_validate);
            data.secCode = request.getParameter(GeetestLib.fn_geetest_seccode);
            return data;
        }

        public static RequestData loadFromJson(JsonObject json) {
            RequestData data = new RequestData();
            data.challenge = json.get(GeetestLib.fn_geetest_challenge).getAsString();;
            data.validate = json.get(GeetestLib.fn_geetest_validate).getAsString();
            data.secCode = json.get(GeetestLib.fn_geetest_seccode).getAsString();
            return data;
        }

        public String getChallenge() {
            return challenge;
        }

        public String getValidate() {
            return validate;
        }

        public String getSecCode() {
            return secCode;
        }
    }
}

