var simplePapers=[];
var authors=[];
$(document).ready(function() {
    getRequest(
        '/author/get',
        function (res) {
            authors = res.content;
            display(authors);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/paper/getSimple',
        function (res) {
            simplePapers = res.content;
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $("#author-search").click(function () {
        var name=$('#author_1').val();
        var num=$('#author_2').val();
        getRequest(
            '/author/search?name='+name+'&num='+num,
            function (res) {
                authors= res.content;
                display(authors);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    })

    function display(List) {
        var Info = "";
        for (let a of List) {
            Info += "<tr></tr><td>" + a[0] + "</td>" +
                "<td >" + a[1]+ "</td>" ;

            Info +=
                "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='authorClick(&quot;"+ a[0] + "&quot;)'>作者详情</button>" + "</td></tr>";
        }
        $('#author-list').html(Info);
    }
});
function authorClick(name){
    window.open("/view/author-detail?author-name="+name);
}