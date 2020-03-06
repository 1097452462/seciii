var papers=[];
$(document).ready(function() {


    $("#uploadFile").click(function() {
        var file=document.getElementById('newFile');

        upload(file);

    });


    function upload(file) {alert("upload");

        let formData = new FormData()
        let temp = file.files[0]
        if (temp){
            formData.append('file',temp)
            $.ajax({
                url:"/paper/addFile",
                type:"POST",
                data: formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success: function(){
                    window.location.reload();
                    alert("成功");
                }
            })
        }
    }



    getRequest(
        '/paper/get',
        function (res) {
            papers=res.content;
            display(papers);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $("#search").click(function() {
        var searchform={
            authors:$('#s_author').val(),
            author_Affiliations:$('#s_department').val(),
            publication_Title:$('#s_meeting').val(),
            author_Keywords:$('#s_pointWord').val()
        };

        postRequest(
            '/paper/search',
            searchform,
            function (res) {
               papers=res.content;
               display(papers);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    });




    $('.close').click(function(){
        $('.box-mask, .box-modal').css('display', 'none');
    })

    window.onkeydown = function (event){ // console.log(event);
        if(event.keyCode == 27){ // 如果键盘按下 ESC 同样退出
            $('.box-mask, .box-modal').css('display', 'none')
        }
    }

    function display(paperList){
        var paperInfo = "";
        for(let paper of paperList){
            paperInfo += "<tr></tr><td>" + paper.authors  + "</td>" +
                "<td >" +paper.author_Affiliations + "</td>" +
                "<td>" + paper.publication_Title+"</td>" +
                "<td>"  + paper.author_Keywords + "</td>" ;
            paperInfo+= "<td style='color: #0eaf26 '>"+"</td>"+
                "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n"+
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:13px;' onclick='paperClick("+paper.id+")'>查看详情</button>"+"</td></tr>";
        }
        $('#paper-list').html(paperInfo);
    }

})

function paperClick(id){
    $('.box-mask, .box-modal').css('display', 'block');
}