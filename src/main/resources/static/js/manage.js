
$(document).ready(function() {
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
                url: "/paper/addFile",
                type: "POST",
                data: formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success: function () {
                    window.location.reload();
                    alert("成功");
                }
            })
        }
    }
})