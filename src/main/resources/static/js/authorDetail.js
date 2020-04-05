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
            $('#authorPaperNum').text(decodeURI(author.paper_num));
            $('#authorCitationSum').text(decodeURI(author.citation_sum));
            $('#authorDetail-num').text("total papers");
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
                words+=word+"<br>";
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

    getRequest(
        '/author/relevant-org?id='+id,
        function (res) {
            orgName= res.content;
            $('#relevant-org-num').text(orgName.length);
            var names="";
            var i=0;
            for(let p of orgName){
                i++;
                if(i>5)break;
                names+=p+"<br>";
            }
            $('#RelevantOrg').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/relevant-author?id='+id,
        function (res) {
            authorName= res.content;
            $('#relevant-author-num').text(authorName.length);
            var names="";
            var i=0;
            for(let p of authorName){
                i++;
                if(i>10)break;
                names+=p;
                if(i%2==1)
                    names+="&nbsp;&nbsp;&nbsp;;&nbsp;&nbsp;&nbsp;";
                else
                    names+="<br>";
            }
            $('#RelevantAuthor').html(names);
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