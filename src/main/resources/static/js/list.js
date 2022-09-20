function articleList(boardId){
    var param = {
        boardId : boardId
    };
    $.ajax({
        url : "/board/list/" + boardId,
        type : "GET",
        data : param,
    })
    .done(function(fragment){
        $("#articleListTable").replaceWith(fragment);
    });
}