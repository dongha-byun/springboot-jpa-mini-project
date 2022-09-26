function addComment(articleId){
    var param = {
        content : $("#content").val(),
        parentId : $("#parentId").val(),
    };
    $.ajax({
        url : "/comment/add/" + articleId,
        type : "POST",
        data : param,
    })
    .done(function(fragment){
        $("#content").val("");
        $("#commentList").replaceWith(fragment);
    });
}