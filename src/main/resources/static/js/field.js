
$(document).ready(function() {

    getRequest(
        '/field/getTop10?mode=3',
        function (res) {
            var fields = res.content;
            display(fields);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    function display(fieldList) {
        var fieldInfo = "";
        n=0;
        for (let field of fieldList) {
            fieldInfo += "<tr></tr><td>" + field.field_name + "</td>" ;

            fieldInfo +=
                "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:16px;' onclick='orgClick("+ field.id +")'>研究方向详情</button>" + "</td></tr>";
            n+=1;
            if(n>1000){break}
        }
        $('#field-list').html(fieldInfo);
    }
});
function orgClick(id){
    window.open("/view/field-detail?field-id="+id);
}
