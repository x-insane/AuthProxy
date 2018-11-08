$("form").submit(function() {
    var button = $(this).find(".submit");
    if (button.hasClass("disabled"))
        return;
    var tip = $(this).find(".result");
    var password = $(this).find("#password").val();
    var repeat = $(this).find("#repeat-password").val();
    if (password !== repeat)
        return tip.text("两次输入的密码不一致");
    button.addClass("disabled");
    tip.text("");
    $.post("/api/user/reset_password/submit", {
        password: password
    }).done(function(res) {
        if (res.error === 0) {
            setTimeout(function() {
                location.href = "/login";
            }, 3000);
            return tip.text("密码重置成功，3秒后跳转到登录页面")
        }
        tip.text(res.msg);
        button.removeClass("disabled")
    }).fail(function() {
        tip.text("网络错误，请稍后再试")
    })
});