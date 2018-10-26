$(function () {
    var csrf_key = $("meta[name='_csrf_key']").attr("content");
    var csrf_token = $("meta[name='_csrf_token']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader("_csrf_key", csrf_key);
        xhr.setRequestHeader("_csrf_token", csrf_token);
    });
});