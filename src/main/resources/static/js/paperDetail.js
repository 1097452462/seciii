
$(document).ready(function() {
     var id=getUrlParameter('paper-id')
    getRequest(
        '/paper/getById?id='+id,
        function (res) {
            mypaper = res.content;
            display(mypaper);
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
function display(p) {
    var info = "";

    info += "<tr><td>" + "document_title:   " + "</td>" + "<td>" ;
    if(p.document_title!=="") info+=  p.document_title;     info+= "</td></tr>";

    info +="<tr><td>" + "authors:   " + "</td>" + "<td>" ;
    if(p.authors!=="") info+=  p.authors;     info+= "</td></tr>";

    info +="<tr><td>" + "publication_year:   " + "</td>" + "<td>";
    if(  p.publication_Year !=="") info+=  p.publication_Year;     info+= "</td></tr>";

    info +="<tr><td>" + "publication_title:   " + "</td>" + "<td>";
    if(  p.publication_Title !=="") info+=  p.publication_Title;     info+= "</td></tr>";

    info +="<tr><td>" + "Author_Affiliations:   " + "</td>" + "<td>";
    if(p.author_Affiliations !=="") info+= p.author_Affiliations;     info+= "</td></tr>";

    info +="<tr><td>" + "Publication_Title:   " + "</td>" + "<td>";
    if( p.publication_Title!=="") info+=  p.publication_Title;     info+= "</td></tr>";

    info +="<tr><td>" + "date_Added_To_Xplore:   " + "</td>" + "<td>";
    if(p.date_Added_To_Xplore!=="")info+=  p.date_Added_To_Xplore;  info+= "</td></tr>";

    info +="<tr><td>" + "Volume:   " + "</td>" + "<td>";
    if( p.volume !=="") info+=  p.volume;     info+= "</td></tr>";

    info +="<tr><td>" + "Issue:   " + "</td>" + "<td>";
    if( p.issue !=="") info+=   p.issue ;     info+= "</td></tr>";

    info +="<tr><td>" + "Start_Page:   " + "</td>" + "<td>";
    if( p.start_Page !=="") info+=  p.start_Page ;     info+= "</td></tr>";

    info +="<tr><td>" + "End_Page:   " + "</td>" + "<td>" ;
    if(p.end_Page!=="") info+= p.end_Page;     info+= "</td></tr>";

    info += "<tr><td>" + "Abstract:   " + "</td>" + "<td>" ;
    if(p.abstract!=="") info+=  p.abstract;     info+= "</td></tr>";

    info +="<tr><td>" + "ISSN:   " + "</td>" + "<td>" ;
    if(p.issn!=="") info+=  p.issn;     info+= "</td></tr>";

    info +="<tr><td>" + "ISNBs:   " + "</td>" + "<td>";
    if(  p.isbns !=="") info+=  p.isbns;     info+= "</td></tr>";

    info +="<tr><td>" + "DOI:   " + "</td>" + "<td>";
    if(  p.doi !=="") info+=  p.doi;     info+= "</td></tr>";

    info +="<tr><td>" + "Funding_Information:   " + "</td>" + "<td>";
    if(p.funding_Information !=="") info+= p.funding_Information;     info+= "</td></tr>";

    info +="<tr><td>" + "PDF_Link:   " + "</td>" + "<td>";
    if( p.pdf_Link!=="") info+=  p.pdf_Link;     info+= "</td></tr>";

    info +="<tr><td>" + "Author_Keywords:   " + "</td>" + "<td>";
    if(p.author_Keywords!=="")info+=  p.author_Keywords;  info+= "</td></tr>";

    info +="<tr><td>" + "IEEE_Terms:   " + "</td>" + "<td>";
    if( p.ieee_Terms !=="") info+=  p.ieee_Terms;     info+= "</td></tr>";

    info +="<tr><td>" + "INSPEC_Controlled_Terms:   " + "</td>" + "<td>";
    if( p.inspec_Controlled_Terms !=="") info+=   p.inspec_Controlled_Terms ;     info+= "</td></tr>";

    info +="<tr><td>" + "inspec_Non_Controlled_Terms:   " + "</td>" + "<td>";
    if( p.inspec_Non_Controlled_Terms !=="") info+=  p.inspec_Non_Controlled_Terms ;     info+= "</td></tr>";

    info +="<tr><td>" + "Mesh_Terms:   " + "</td>" + "<td>" ;
    if(p.mesh_Terms!=="") info+= p.mesh_Terms;     info+= "</td></tr>";

    info +="<tr><td>" + "Article_Citation_Count:   " + "</td>" + "<td>";
    if(  p.article_Citation_Count !=="") info+=  p.article_Citation_Count;     info+= "</td></tr>";

    info +="<tr><td>" + "Reference_Count:   " + "</td>" + "<td>";
    if(p.reference_Count !=="") info+= p.reference_Count;     info+= "</td></tr>";

    info +="<tr><td>" + "License:   " + "</td>" + "<td>";
    if( p.license!=="") info+=  p.license;     info+= "</td></tr>";

    info +="<tr><td>" + "Online_Date:   " + "</td>" + "<td>";
    if(p.online_Date!=="")info+=  p.online_Date;  info+= "</td></tr>";

    info +="<tr><td>" + "Issue_Date:   " + "</td>" + "<td>";
    if( p.issue_Date !=="") info+=  p.issue_Date;     info+= "</td></tr>";

    info +="<tr><td>" + "Meeting_Date:   " + "</td>" + "<td>";
    if( p.meeting_Date !=="") info+=   p.meeting_Date ;     info+= "</td></tr>";

    info +="<tr><td>" + "Publisher:   " + "</td>" + "<td>";
    if( p.publisher !=="") info+=  p.publisher ;     info+= "</td></tr>";

    info +="<tr><td>" + "Document_Identifier:   " + "</td>" + "<td>" ;
    if(p.document_Identifier!=="") info+= p.document_Identifier;     info+= "</td></tr>";


    $('#paper-detail').html(info);
}