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
        if(!isValid(searchform))
            alert("You can not search for nothing! Please input at least one identity");
        else {
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
        }
    });

    function isValid(data){
        var cnt=0;
        if(data.document_title.length==0)cnt++;
        if(data.authors.length==0)cnt++;
        if(data.publication_Year.length==0)cnt++;
        if(data.author_Affiliations.length==0)cnt++;
        if(data.publication_Title.length==0)cnt++;
        if(data.author_Keywords.length==0)cnt++;
        if(cnt==6)return false;
        return true;
    }

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
            paperInfo += "<tr>" +
                "<td width='95%' style='font-size: 125%;text-align: left'>" + paper.document_title + "</td>" ;
                // +"<td width='10%'>" + paper.authors.substr(0, 30)+"..." + "</td>" +
                // "<td width='4%'>" + paper.publication_Year+ "</td>" +
                // "<td width='17%'>" + paper.author_Affiliations.substr(0, 50)+"..." + "</td>" +
                // "<td width='15%'>" + paper.publication_Title.substr(0, 50)+"..." + "</td>" +
                // "<td width='24%'>" + paper.author_Keywords.substr(0, 50)+"..." + "</td>";
            paperInfo +=
                "<td><button id=\"flip\" style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='detailClick("+n+")'>更多</button>"+"</td></tr>";
            paperInfo +="<tr><td><p id=\"panel\" hidden>"+
            "<br>" + "<a style='color: #ff3e31;'>Authors:"+"&nbsp; "+"</a>"+ paper.authors+
            "<br>" + "<a style='color: #ff3e31;'>Publication_Year:"+"&nbsp;"+"</a>"+paper.publication_Year+
            "<br>" + "<a style='color: #ff3e31;'>Author_Affiliations:"+"&nbsp; "+"</a>"+paper.author_Affiliations+
            "<br>" + "<a style='color: #ff3e31;'>Publication_Title:"+"&nbsp;   "+"</a>"+paper.publication_Title+
            "<br>" + "<a style='color: #ff3e31;'>Author_Keywords: "+"&nbsp;    "+"</a>"+paper.author_Keywords+
            "<br>" + "<a style='color: #ff3e31;'>Author_Affiliations:"+"&nbsp; "+"</a>"+paper.author_Affiliations+ "<br>"+
            "<br><button type='button'  style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='paperClick(" + paper.id + ")' >论文详情</button>" + "</p>"+ "</td></tr>";
            n+=1;
        }
        $('#paper-list').html(paperInfo);
    }


});

function detailClick(id){
    $("p").eq(id).slideToggle("slow");
}

function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
}



//a链接点击变色，再点其他回复原色
function changCss(obj) {
    var arr = document.getElementsByTagName("a");
    for (var i = 0; i < arr.length; i++) {
        if (obj == arr[i]) {       //当前页样式
            obj.style.backgroundColor = "#006B00";
            obj.style.color = "#ffffff";
        }
        else {
            arr[i].style.color = "";
            arr[i].style.backgroundColor = "";
        }
    }
}
