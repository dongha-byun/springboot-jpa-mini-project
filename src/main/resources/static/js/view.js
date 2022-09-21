function deleteArticle(articleId){
    var f = document.createElement('form');
    f.action = "/article/" + articleId + "/delete";
    f.method = "POST";

    document.body.appendChild(f);

    f.submit();
}