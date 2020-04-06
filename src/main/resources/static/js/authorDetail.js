var author;
var papers=[];
$(document).ready(function() {
    var id=getUrlParameter('author-id');//alert(name);

    getRequest(
        '/author/getById?id='+id,
        function (res) {
            author= res.content;
            $('#authorDetail-name').text(decodeURI(author.author_name));
            $('#authorDetail-org').text(decodeURI(author.org));
            $('#authorPaperNum').text(decodeURI(author.paper_num));
            $('#authorCitationSum').text(decodeURI(author.citation_sum));
            $('#authorDetail-num').text("total papers");
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/getTopKeyword?id='+id,
        function (res) {
            var TopKeyword=res.content;
            var words="";

            for(let word of TopKeyword){
                words+=word+"<br>";
            }
            $('#Top5keyword').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/author/getTopPaper?id='+id,
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
        '/author/relevant-org?id='+id,
        function (res) {
            orgName= res.content;
            $('#relevant-org-num').text(orgName.length);
            var names="";
            var i=0;
            for(let p of orgName){
                i++;
                if(i>3)break;
                names+=p+"<br><br>";
            }
            $('#RelevantOrg').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/relevant-author?id='+id,
        function (res) {
            authorName= res.content;
            $('#relevant-author-num').text(authorName.length);
            var names="";
            var i=0;
            for(let p of authorName){
                i++;
                if(i>10)break;
                names+=p;
                if(i%2==1)
                    names+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                else
                    names+="<br>";
            }
            $('#RelevantAuthor').html(names);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    $("#author-detail-paper").click(function () {
        getRequest(
            '/author/getSimplepaperById?id='+id,
            function (res) {
                papers= res.content;
                display(papers);
                document.getElementById("author-detail-table").style.display="block";
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });
    $("#author-detail-paper-close").click(function () {
        document.getElementById("author-detail-table").style.display="none";
    });


    getRequest(
        '/author/history?id='+id,
        function (res) {
           var dd=res.content;
           drawBar(dd);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/author/interest?id='+id,
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
};

function display(paperList) {
    var paperInfo = "";

    for (let paper of paperList) {
        paperInfo += "<tr></tr><td>" + paper.document_title + "</td>" +
            "<td >" + paper.authors+ "</td>" +
            "<td >" + paper.publication_Year+ "</td>" +
            "<td>" + paper.publication_Title.substr(0, 50)+"..." + "</td>" +
            "<td >" + paper.author_Keywords.substr(0, 50)+"..." + "</td>";
        paperInfo +=
            "<td><button type='button' style='background-color: #4CAF50; /* Green */\n" +
            "border: 2px solid #4CAF50;" +
            "color: white;\n" +
            "    padding: 7px 15px;\n" +
            "    text-align: center;\n" +
            " border-radius: 6px;\n" +
            "    text-decoration: none;\n" +
            "    display: inline-block;\n" +
            "    font-size:13px;' onclick='paperClick(" + paper.id+ ")'>论文详情</button>" + "</td></tr>";
    }

    $('#authorDetail-list').html(paperInfo);

}
function paperClick(id){
    window.open("/view/paper-detail?paper-id="+id);
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
                max: 20,
                interval: 5,
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
    var myChart = echarts.init(document.getElementById("authorDetail-bar"));
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
                radius: [30, 200],
                center: ['50%', '50%'],
                roseType: 'area',
                data: pp
            }
        ]
    };
    var myChart = echarts.init(document.getElementById("authorDetail-pie"));
    myChart.setOption(option);
}
