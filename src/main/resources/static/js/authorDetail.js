var author;
var papers=[];
$(document).ready(function() {
    var id=getUrlParameter('author-id');//alert(name);

    getRequest(
        '/author/getById?id='+id,
        function (res) {
            author= res.content;
            $('#authorDetail-name').text(decodeURI(author.author_name));
            $('#authorDetail-org').text(decodeURI(author.org));
            $('#authorDetail-num').text(author.paper_num+"  total papers");
        },
        function (error) {
            alert(JSON.stringify(error));
        }


    );
    getRequest(
        '/author/getCitationSum?id='+id,
        function (res) {
            var CitationSum=res.content;
            $('#authorCitationSum').text(decodeURI(CitationSum));
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/getTopKeyword?id='+id,
        function (res) {
            var TopKeyword=res.content;
            var words="";

            for(let word of TopKeyword){
                words+=word+";<br><br>";
            }
            $('#Top5keyword').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/getTopPaper?id='+id,
        function (res) {
            papers= res.content;
            var names="";
            for(let p of papers){
                names+=p.document_title+";<br>";
            }
            $('#Top5paper').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    $("#author-detail-paper").click(function () {
        getRequest(
            '/author/getSimplepaperById?id='+id,
            function (res) {
                papers= res.content;
                display(papers);
                document.getElementById("author-detail-table").style.display="block";
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
    $("#author-detail-paper-close").click(function () {
        document.getElementById("author-detail-table").style.display="none";
    });

});
function getUrlParameter(name){
    name = name.replace(/[]/,"\[").replace(/[]/,"\[").replace(/[]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null ) return ""; else {
        return results[1];
    }
};

function display(paperList) {
    var paperInfo = "";

    for (let paper of paperList) {
        paperInfo += "<tr></tr><td>" + paper.document_title + "</td>" +
            "<td >" + paper.authors+ "</td>" +
            "<td >" + paper.publication_Year+ "</td>" +
            "<td>" + paper.publication_Title.substr(0, 50)+"..." + "</td>" +
            "<td >" + paper.author_Keywords.substr(0, 50)+"..." + "</td>";
        paperInfo +=
            "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
            "border: 2px solid #4CAF50;" +
            "color: white;\n" +
            "    padding: 7px 15px;\n" +
            "    text-align: center;\n" +
            " border-radius: 6px;\n" +
            "    text-decoration: none;\n" +
            "    display: inline-block;\n" +
            "    font-size:13px;' onclick='paperClick(" + paper.id+ ")'>论文详情</button>" + "</td></tr>";
    }

    $('#authorDetail-list').html(paperInfo);

}
function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}