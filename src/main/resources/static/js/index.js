var papers=[];
var n=0;
$(document).ready(function() {

    getRequest(
        '/paper/get',
        function (res) {
            papers = res.content;
            //display(papers);
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
        n=0;
        for (let paper of paperList) {
            paperInfo += "<tr></tr><td width='25%'>" + paper.document_title + "</td>" +
                "<td width='10%'>" + paper.authors.substr(0, 30)+"..." + "</td>" +
                "<td width='4%'>" + paper.publication_Year+ "</td>" +
                "<td width='17%'>" + paper.author_Affiliations.substr(0, 50)+"..." + "</td>" +
                "<td width='15%'>" + paper.publication_Title.substr(0, 50)+"..." + "</td>" +
                "<td width='24%'>" + paper.author_Keywords.substr(0, 50)+"..." + "</td>";
            paperInfo +=
                "<td><button  type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='paperClick(" + paper.id + ")'>论文详情</button>" + "</td></tr>";
            paperInfo +=
            "<td>"+"<button id=\"flip\" onclick='detailClick("+n+")'>查看详情</button>"+
            "<p id=\"panel\" hidden>"+"<br>"+paper.author_Affiliations.substr(0, 50)+"</p>"
            "</td></tr>";
            n+=1;
        }
        $('#paper-list').html(paperInfo);
    }


});

function detailClick(id){
    //id=id-2;
    //id=len(list)-1-id;
    $("p").eq(id).slideToggle("slow");
}

function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}


