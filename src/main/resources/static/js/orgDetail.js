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
    })
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
            "    font-size:13px;' onclick='paperClick(" + paper.paper_id + ")'>论文详情</button>" + "</td></tr>";
    }

    $('#orgDetail-list').html(paperInfo);
}
function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}