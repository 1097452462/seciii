var papers=[];
$(document).ready(function() {

    getRequest(
        '/paper/get',
        function (res) {
            papers = res.content;
            display(papers);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $("#paper-search").click(function () {
        var searchform = {
            document_title:$('#index_0').val(),
            authors: $('#index_1').val(),
            publication_Year:$('#index_2').val(),
            author_Affiliations: $('#index_3').val(),
            publication_Title: $('#index_4').val(),
            author_Keywords: $('#index_5').val()
        };

        postRequest(
            '/paper/search',
            searchform,
            function (res) {
                papers = res.content;
                display(papers);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    });


    $('.close').click(function () {
        $('.box-mask, .box-modal').css('display', 'none');
    })

    window.onkeydown = function (event) { // console.log(event);
        if (event.keyCode == 27) { // 如果键盘按下 ESC 同样退出
            $('.box-mask, .box-modal').css('display', 'none')
        }
    }

    function display(paperList) {
        var paperInfo = "";
        for (let paper of paperList) {
            paperInfo += "<tr></tr><td>" + paper.document_title + "</td>" +
                "<td >" + paper.authors.substr(0, 30)+"..." + "</td>" +
                "<td >" + paper.publication_Year+ "</td>" +
                "<td >" + paper.author_Affiliations.substr(0, 50)+"..." + "</td>" +
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
        $('#paper-list').html(paperInfo);
    }


});

function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}


