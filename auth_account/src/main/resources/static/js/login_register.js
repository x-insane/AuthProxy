$("form").submit(function() {
    var redirect = $(this).attr("href");
    $(".submit").addClass("loading");
    $(".result").text("");
    $.post(this.action, $(this).serialize())
        .done(function(data) {
            if (data.error === 0 && redirect)
                location.href = redirect;
            else
                $(".result").text(data.msg);
        })
        .fail(function (data) {
            $(".result").text("网络错误，请稍后再试");
        })
        .always(function() {
            $(".submit").removeClass("loading");
        })
});