var method=[];
var methodId;
$(document).ready(function() {
    GetInit();
    initSelect();


    function initSelect(){

        // 过滤条件变化后重新查询
        $('#method-select').change (function () {
            methodId=$(this).children('option:selected').val();
            getRating();
        });
    }

    function GetInit() {
        getRequest(
            '/field/getTop10?mode=1',
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    return item.paper_num;
                });
                var nameList = method.map(function (item) {
                    return item.field_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '研究方向活跃度排行',
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
                var RateChart = echarts.init($("#field-rating")[0]);
                RateChart.setOption(option);
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/field-detail?field-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );

    }
    function getRating(){
        getRequest(
            '/field/getTop10?mode='+methodId,
            function (res) {
                method = res.content||[];

                var tableData = method.map(function (item) {
                    if(methodId==1)return item.paper_num;
                    if(methodId==2)return item.citation_sum;
                    if(methodId==3)return item.point;
                });
                var nameList = method.map(function (item) {
                    return item.field_name;
                });
                var ids = method.map(function (item) {
                    return item.id;
                }).reverse();
                var option = {
                    title: {
                        text: '机构活跃度排行',
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
                var RateChart = echarts.init($("#field-rating")[0]);
                RateChart.setOption(option);
                RateChart.on('click', function (params) {
                    var id=ids[params.dataIndex];
                    window.open("/view/field-detail?field-id="+id);

                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});
function fieldClick(id){
    window.open("/view/field-detail?field-id="+id);
}
