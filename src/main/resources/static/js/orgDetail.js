var org;
var papers=[];
$(document).ready(function() {
    var id=getUrlParameter('org-id');//alert(name);

    getRequest(
        '/org/getById?id='+id,
        function (res) {
            org= res.content;
            $('#orgDetail-name').text(decodeURI(org.org_name));
            $('#orgPaperNum').text(org.paper_num);
            $('#orgCitationSum').text(org.citation_sum);
            $('#orgAuthorNum').text(org.author_num);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );


    getRequest(
        '/org/topKeyword?id='+id,
        function (res) {
            var topKeyword=res.content;
            var words="";

            for(let word of topKeyword){
                words+=word+";<br>";
            }
            $('#Top5keyword').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/getTopPaper?id='+id,
        function (res) {
            papers= res.content;
            var names="";
            var i=0;
            for(let p of papers){
                i++;
                if(i==3)break;
                names+=p.document_title+";<br><br>";
            }
            $('#Top5paper').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/getRelatedAuthors?id='+id,
        function (res) {
            var authorName=res.content;
            $('#relevant-author-num').text(authorName.length);
            var names="";
            var i=0;
            for(let p of authorName){
                i++;
                if(i>8)break;
                names+=p;
                if(i%4==0)
                    names+="<br>";
                else
                    names+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            $('#RelevantAuthor').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/getRelatedOrgs?id='+id,
        function (res) {
            var RelatedOrgs=res.content;
            $('#relevant-org-num').text(RelatedOrgs.length);
            var words="";
            var i=0;
            for(let word of RelatedOrgs){
                words+=word+";<br><br>";
                i++;
                if(i==3)break;
            }
            $('#RelevantOrg').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/topAuthor?id='+id,
        function (res) {
            var topauthor=res.content;
            var words="";
            var i=0;
            for(let author of topauthor){
                words+=author.author_name;
                i++;
                if(i%3==0)
                    words+="<br>";
                else
                    words+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            words+="<br>";
            $('#TopAuthor').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/org/history?id='+id,
        function (res) {
            var dd=res.content;
            drawBar(dd);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/org/interest?id='+id,
        function (res) {
            var list=res.content;
            var pp=[];
            for(let l of list){
                var e={
                    value:parseInt(l[1]),
                    name:l[0]
                };
                pp.push(e);
            }
            drawPie(pp);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

});
function getUrlParameter(name){
    name = name.replace(/[]/,"\[").replace(/[]/,"\[").replace(/[]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null ) return ""; else {
        return results[1];
    }
}

function drawBar(dd) {
    option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '2%',
            right: '2%',
            bottom: '1%',
            top:'5%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['1988', '1989', '1990', '1991', '1992', '1993', '1993','1995','1996','1997','1998','1999','2000',
                    '2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012','2013','2014',
                    '2015','2016','2017','2018','2019','2020'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                min: 0,
                max: 100,
                interval: 20,
            }
        ],
        series: [
            {
                type: 'bar',
                barWidth: '60%',
                data: dd
            }
        ]
    };
    var myChart = echarts.init(document.getElementById("orgDetail-bar"));
    myChart.setOption(option);
}
function drawPie(pp){
    option = {

        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        grid: {
            left: '5%',
            right: '5%',
            bottom: '5%',
            top:'5%',
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [

            {
                name: '面积模式',
                type: 'pie',
                radius: [30, 180],
                center: ['50%', '50%'],
                roseType: 'area',
                data: pp,
                label:{            //饼图图形上的文本标签
                    normal:{
                        show:true,
                        textStyle : {
                            fontWeight : 300 ,
                            fontSize : 15    //文字的字体大小
                        },
                    }
                },
            }
        ]
    };
    var myChart = echarts.init(document.getElementById("orgDetail-pie"));
    myChart.setOption(option);
}