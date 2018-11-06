var handler = function (captchaObj) {
    captchaObj.appendTo('#captcha');
    captchaObj.onReady(function() {
        $("#wait").hide();
    });
    window.captchaObj = captchaObj;
    // 更多前端接口说明请参见：http://docs.geetest.com/install/client/web-front/
};

$.ajax({
    url: "/geetest/start_captcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
    type: "GET",
    dataType: "json",
    success: function (data) {
        $('#text').hide();
        $('#wait').show();

        // 调用 initGeetest 进行初始化
        // 参数1：配置参数
        // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
        initGeetest({
            // 以下 4 个配置参数为必须，不能缺少
            gt: data.gt,
            challenge: data.challenge,
            offline: !data.success, // 表示用户后台检测极验服务器是否宕机
            new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机

            product: "float", // 产品形式，包括：float，popup
            width: "280px"

            // 更多前端配置参数说明请参见：http://docs.geetest.com/install/client/web-front/
        }, handler);
    }
});

$("form").submit(function() {
    var tip = $(".result");
    if (!window.captchaObj || !window.captchaObj.getValidate())
        return tip.text("请先完成验证");
    var redirect = $(this).attr("href");
    $(".submit").addClass("loading");
    tip.text("");
    $.post(this.action, $(this).serialize())
        .done(function(data) {
            if (data.error === 0 && redirect)
                location.href = redirect;
            else
                tip.text(data.msg);
        })
        .fail(function (data) {
            tip.text("网络错误，请稍后再试");
        })
        .always(function() {
            $(".submit").removeClass("loading");
            if (window.captchaObj)
                window.captchaObj.reset();
        })
});