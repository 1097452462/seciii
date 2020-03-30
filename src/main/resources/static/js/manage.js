files=[];
$(document).ready(function() {
    getRequest(
        '/file/getFiles',
        function (res) {
            files = res.content;
            display(files);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    function display(fileList) {
        var fileInfo = "";
        for (let f of fileList) {
            fileInfo += "<tr><td>" + f + "</td></tr>";
        }
        $('#file-list').html(fileInfo);
    }


    $("#uploadFile").click(function () {
        var file = document.getElementById('newFile');
        upload(file);
    });

    function upload(file) {
        alert("upload");

        let formData = new FormData()
        let temp = file.files[0]
        if (temp) {
            formData.append('file', temp)
            $.ajax({
                url: "/file/addFile",
                type: "POST",
                data: formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success: function () {
                    window.location.reload();
                    alert("文件上传成功");
                }
            })
        }
    }

    $("#updateDB").click(function () {
        alert("数据库更新中");
        postRequest(
            '/file/updateDB',
            null,
            function (res) {
                alert("数据库更新成功");
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
})