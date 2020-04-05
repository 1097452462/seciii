
$(document).ready(function() {
    var id = getUrlParameter('field-id');
    var field;
    var topPaper=[];
    var topAuthor=[];
    var topOrg=[];
    getRequest(
        '/field/getById?id=' + id,
        function (res) {
            field = res.content;
            $('#field-name').text(field.field_name);
            $('#field-paperNum').text(field.paper_num);
            $('#field-CitationSum').text(field.citation_sum);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/field/getTopAuthors?id=' + id,
        function (res) {
            topAuthor = res.content;
            var words="";

            for(let word of topAuthor){
                words+=word.author_name+";<br><br>";
            }
            $('#TopAuthor').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );

    getRequest(
        '/field/getTopOrgs?id=' + id,
        function (res) {
            topOrg = res.content;
            var words="";

            for(let word of topOrg){
                words+=word.org_name+";<br><br>";
            }
            $('#TopOrgs').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
    getRequest(
        '/field/getTopPapers?id=' + id,
        function (res) {
            topPaper = res.content;
            var words="";

            for(let word of topPaper){
                words+=word.document_title+";<br><br>";
            }
            $('#Top5paper').html(words);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    )
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