$("form").submit(function() {
    var redirect = $(this).attr("href");
    $.post(this.action, $(this).serialize())
        .done(function(data) {
            console.log(data);
            if (data.error === 0 && redirect)
                location.href = redirect;
            else
                alert(data.msg);
        })
        .fail(function (data) {
            console.warn(data)
        })
});