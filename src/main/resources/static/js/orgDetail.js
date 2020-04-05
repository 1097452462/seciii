var org;
var papers=[];
$(document).ready(function() {
    var id=getUrlParameter('org-id');//alert(name);

    getRequest(
        '/org/getById?id='+id,
        function (res) {
            org= res.content;
            $('#orgDetail-name').text(decodeURI(org.org_name));
            $('#orgDetail-num').text(org.paper_num+"  total papers");
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/authorNum?id='+id,
        function (res) {
            var authorNum=res.content;
            $('#authorNum').text(decodeURI(authorNum));
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
        '/org/topKeyword?id='+id,
        function (res) {
            var topKeyword=res.content;
            var words="";

            for(let word of topKeyword){
                words+=word+";<br>";
            }
            $('#Top5keyword').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/topAuthor?id='+id,
        function (res) {
            var topauthor=res.content;
            var words="";

            for(let author of topauthor){
                words+=author.author_name+";<br>";
            }
            $('#TopAuthor').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/getTopPaper?id='+id,
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
    $("#org-detail-paper").click(function () {
        getRequest(
            '/org/getSimplepaperById?id='+id,
            function (res) {
                papers= res.content;
                display(papers);
                document.getElementById("org-detail-table").style.display="block";
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
    $("#org-detail-paper-close").click(function () {
        document.getElementById("org-detail-table").style.display="none";
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
}

function display(paperList) {
    var paperInfo = "";

    for (let paper of paperList) {
        paperInfo += "<tr></tr><td>" + paper.document_title + "</td>" +
            "<td >" + paper.author_Affiliations+ "</td>" +
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
            "    font-size:13px;' onclick='paperClick(" + paper.id + ")'>论文详情</button>" + "</td></tr>";
    }

    $('#orgDetail-list').html(paperInfo);
}
function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}