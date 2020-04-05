var methodId;
var authors=[];
var method=[];
var n=0;
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
        n=0;
        for (let a of List) {
            Info += "<tr></tr><td class='aaa'>" + a.author_name + "</td>" +
                "<td class='bbb'>" + a.org+ "</td>"+
                "<td class='ccc'>" + a.paper_num+ "</td>" ;

            Info +=
                "<td class='ddd'><button type='button' style='background-color: #4CAF50; /* Green */\n" +
                "border: 2px solid #4CAF50;" +
                "color: white;\n" +

                "    text-align: center;\n" +
                " border-radius: 6px;\n" +
                "    text-decoration: none;\n" +
                "    display: inline-block;\n" +
                "    font-size:16px;' onclick='authorClick("+ a.id + ")'>详情</button>" + "</td></tr>";
            n+=1;
            if(n>1000){break}
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
                        text: '作者活跃度排行',
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
                            data: tableData.reverse(),
                            itemStyle: {
                                normal: {
                                    //这里是重点
                                    color: function(params) {
                                        //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                        var colorList = ['#c23531','#2f4554', '#d48265',
                                            '#91c7ae','#749f83', '#ca8622',
                                            '#B5C334','#FCCE10','#E87C25','#27727B'];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            }
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
                        text: '作者活跃度排行',
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
                        data: nameList.reverse(),

                    },
                    series: [
                        {
                            type: 'bar',
                            data: tableData.reverse(),
                            itemStyle: {
                                normal: {
                                    //这里是重点
                                    color: function(params) {
                                        //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                        var colorList = ['#c23531','#2f4554', '#d48265',
                                            '#91c7ae','#749f83', '#ca8622',
                                            '#B5C334','#FCCE10','#E87C25','#27727B'];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            }
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

