var meeting;
$(document).ready(function() {
    var id=getUrlParameter('meeting-id');//alert(name);
    getRequest(
        '/paper/getMeetingById?id='+id,
        function (res) {
            meeting= res.content;
            $('#meeting-name').text(meeting.publication_title);
            $('#meeting-year').text(meeting.publication_year);
            $('#meetingPaperNum').text(meeting.paper_num);
            $('#meetingCitationSum').text(meeting.citation_sum);
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


