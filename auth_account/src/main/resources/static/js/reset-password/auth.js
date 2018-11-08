$(".item-frame > header").click(function() {
    var frame = $(this.parentNode);
    var open = frame.hasClass("close");
    $(".item-frame").addClass("close");
    if (open)
        frame.removeClass("close");
});

(function(){
    function onSend(target, api_path) {
        var counter = 0;
        var button = target.find(".send");
        var tip = target.find(".result");
        button.click(function() {
            if (button.hasClass("disabled"))
                return;
            button.addClass("disabled");
            if (target.hasClass("email-frame"))
                tip.text("发送邮件大约需要20秒，请耐心等待");
            else
                tip.text("");
            $.post(api_path).done(function(res) {
                if (res.error === 0) {
                    counter = 30;
                    setTimeout(function loop() {
                        if (counter === 0) {
                            button.removeClass("disabled");
                            button.text("发送验证码");
                        } else {
                            button.text("发送验证码(" + counter + ")");
                            counter --;
                            setTimeout(loop, 1000);
                        }
                    }, 0);
                    tip.text("发送成功，请查收验证码")
                } else {
                    tip.text(res.msg);
                    button.removeClass("disabled")
                }
            }).fail(function() {
                tip.text("网络错误，请稍后再试")
            })
        });
    }

    onSend($(".phone-frame"), "/api/user/reset_password/notify_phone");
    onSend($(".email-frame"), "/api/user/reset_password/notify_email")
})();

$(".item-frame form").submit(function() {
    var button = $(this).find(".submit");
    var frame = $(this).parents(".item-frame");
    var code = frame.find("input[name=code]").val();
    var tip = frame.find(".result");
    if (button.hasClass("disabled"))
        return;
    button.addClass("disabled");
    tip.text("");
    $.post("/api/user/reset_password/code", {
        code: code
    }).done(function(res) {
        if (res.error === 0)
            return location.href = "/forget-password/reset";
        tip.text(res.msg);
        button.removeClass("disabled")
    }).fail(function() {
        tip.text("网络错误，请稍后再试")
    })
});
