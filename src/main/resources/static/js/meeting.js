
var meeting=[];
$(document).ready(function() {
getRequest(
    '/paper/meeting',
    function (res) {
        meeting = res.content||[];

        var tableData = meeting.map(function (item) {
            return item.paper_num;
        });
        var nameList = meeting.map(function (item) {
            return item.publication_title;
        });
        var ids = meeting.map(function (item) {
            return item.id;
        });
        var option = {
            title: {
                text: '会议活跃度排行',
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
        var RateChart = echarts.init($("#meeting-rating")[0]);
        RateChart.setOption(option);
        RateChart.on('click', function (params) {
            var id=ids[params.dataIndex];
            window.open("/view/meeting-detail?meeting-id="+id);

        });
    },
    function (error) {
        alert(JSON.stringify(error));
    }
);
});