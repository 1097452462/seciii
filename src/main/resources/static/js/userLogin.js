$(document).ready(function() {
    $("#loginButton").click(function() {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            alert("用户名或密码不能为空");
            return;
        }
        postRequest(
            '/user/userLogin',
            formData,
            function (res) {
                //alert(res.success);
                window.location.href="main/home"
            },
            function (error) {
                alert(error);
            });

    });

    function getLoginForm() {
        return {
            username: $('#input-username').val(),
            password: $('#input-password').val()
        };
    }
    function validateLoginForm(data){
        var ans=true;
        if(!data.username||!data.password)
            ans=false;
        return ans;
    }
});