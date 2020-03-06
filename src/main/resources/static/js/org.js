var simplePapers=[];
var orgs=[];
$(document).ready(function() {
    getRequest(
        '/org/get',
        function (res) {
            orgs = res.content;
            display(orgs);
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

    $("#org-search").click(function () {
        var name=$('#org_1').val();
        var num=$('#org_2').val();
        getRequest(
            '/org/search?name='+name+'&num='+num,
            function (res) {
                orgs= res.content;
                display(orgs);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    })

    function display(orgList) {
        var orgInfo = "";
        for (let org of orgList) {
            orgInfo += "<tr></tr><td>" + org[0] + "</td>" +
                "<td >" + org[1]+ "</td>" ;

            orgInfo +=
                "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='orgClick(" + org[0] + ")'>机构详情</button>" + "</td></tr>";
        }
        $('#org-list').html(orgInfo);
    }
});
function orgClick(name){
    $('.box-mask, .box-modal').css('display', 'block');
}