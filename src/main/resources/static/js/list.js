function articleList(boardId){
    $.ajax({
        url : "/board/list/" + boardId,
        type : "GET",
    })
    .done(function(fragment){
        $("#articleListTable").replaceWith(fragment);
    });
}