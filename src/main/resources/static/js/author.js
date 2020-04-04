var methodId;
var authors=[];
var method=[];
$(document).ready(function() {
    /*
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
*/
    GetInit();
    initSelect();


    $("#author-search").click(function () {
        var name=$('#author_1').val();
        var num=$('#author_2').val();
        if(name.length==0&&num.length==0)
            alert("You can not search for nothing! Please input at least one identity");
        else {
            getRequest(
                '/author/search?name=' + name + '&num=' + num,
                function (res) {
                    authors = res.content;
                    display(authors);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });

    function display(List) {
        var Info = "";
        for (let a of List) {
            Info += "<tr></tr><td>" + a.author_name + "</td>" +
                "<td >" + a.org+ "</td>"+
                "<td >" + a.paper_num+ "</td>" ;

            Info +=
                "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +
                "    padding: 7px 15px;\n" +
                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:16px;' onclick='authorClick("+ a.id + ")'>作者详情</button>" + "</td></tr>";
        }
        $('#author-list').html(Info);
    }

    function initSelect(){

        // 过滤条件变化后重新查询
        $('#method-select').change (function () {
            methodId=$(this).children('option:selected').val();
            getRating();
        });
    }

    function GetInit() {
            getRequest(
                '/author/rating?methodId='+1,
                function (res) {
                    method = res.content||[];

                    var tableData = method.map(function (item) {
                        return item.paper_num;
                    });
                    var nameList = method.map(function (item) {
                        return item.author_name;
                    });

                    var option = {
                        title: {
                            text: '作者排行',
                            subtext: '数据来自学术网站'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },

                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value',
                            boundaryGap: [0, 0.01]
                        },
                        yAxis: {
                            type: 'category',
                            data: nameList.reverse()
                        },
                        series: [
                            {
                                type: 'bar',
                                data: tableData.reverse()
                            }
                        ]
                    };
                    var RateChart = echarts.init($("#author-rating")[0]);
                    RateChart.setOption(option);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );

    }

    function getRating(){
        getRequest(
            '/author/rating?methodId='+methodId,
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    if(methodId==1)return item.paper_num;
                    if(methodId==2)return item.citation_sum;
                    if(methodId==3)return item.point;
                });
                var nameList = method.map(function (item) {
                    return item.author_name;
                });

                var option = {
                    title: {
                        text: '作者排行',
                        subtext: '数据来自学术网站'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: nameList.reverse()
                    },
                    series: [
                        {
                            type: 'bar',
                            data: tableData.reverse()
                        }
                    ]
                };
                var RateChart = echarts.init($("#author-rating")[0]);
                RateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});
function authorClick(id){
    window.open("/view/author-detail?author-id="+id);
}

