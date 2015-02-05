<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.Map,java.util.Iterator,java.util.Set,java.util.List"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@page import="com.acc.tools.ed.integration.dto.HrsResource"%>
<!DOCTYPE html >
<html>
<head>
<style>
a.menu:link, a.menu:visited {
    display: block;
    font-weight: bold;
    color: #ffffff;
    background-color: #98bf21;
    width: 120px;
    text-align: center;
    padding: 4px;
    text-decoration: none;
}

a.menu:hover, a.menu:active {
    background-color: #7A991A;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
    href="css\jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
    $(function() {
        $("#tabs")
                .tabs(
                        {
                            beforeLoad : function(event, ui) {
                                ui.jqXHR
                                        .error(function() {
                                            ui.panel
                                                    .html("");
                                        });
                            }
                        });
    });

</script>
 
</head>




<title>Insert title here</title>
</head>
<body>
<table align="center">

<tr height="8%" width="100%">
<td>
<img src="resources/HomePage.jpg" width="80%"  />
</td>
<td width="50px"> </td>
<td>
<table align="right" style="border:1px solid black; table-layout: fixed;"><tr> <td>Logged in as:</td>
			
			<td ><%=session.getAttribute("enterprise_id")%></td>
			
			</tr>
			<tr>
			
			<td >Role:</td>
			
			<td ><%=session.getAttribute("role")%></td>
			</tr>
			<td> </td>
			<td ><a href="logout.do">Logout</a></td>
		</tr>
	</table>
</td><tr>
	<td><img alt="" src="line.jpg"> </td>
</tr>
</tr>
<tr height="80%" width="100%">
<td>
<%
/* Map<Integer,String> tasks=((Map<Integer,String>) request.getAttribute("subTasks"));

Set<Integer> taskIds=tasks.keySet();
Iterator i=tasks.entrySet().iterator(); */
//String role=(String)session.getAttribute("role");
String tl="TL";
String am="AM";
String man="manager";
String emp="";
String role = (String) session.getAttribute("role");
%>
	                <div id="tabs">
                    <ul id="edTabs">
                        <jstl:choose>
                            <jstl:when test="<%=role.equalsIgnoreCase(tl)%>">
                                <li id="one"><a href="#resourceTab1" id="resourceTab">Developer</a></li>
                                <li id="two"><a href="#highLevelTaskTab1" id="highLevelTaskTab">Supervisor</a></li>
                                <li id="three"><a href="#reportsLeadTab1" id="reportsLeadTab">Report</a></li>
                            </jstl:when>
                            <jstl:when
                                test="<%=role.equalsIgnoreCase(man)
                            || role.equalsIgnoreCase(am)%>">
                                <li><a href="#" id="resourceTab">Developer</a></li>
                                <li><a href="#" id="highLevelTaskTab">Supervisor</a></li>
                                <li><a href="#" id="reportsLeadTab">Report</a></li>
<!--                                   <li><a href="#" id="managerTab">Report</a></li>  -->
                            </jstl:when>
                            <jstl:otherwise>
                               <li><a href="#resourceTab1" id="resourceTab">Developer</a></li>
                            </jstl:otherwise>
                        </jstl:choose>
                    </ul>
                    <div id="edPanes" style="border-bottom: none;">
                        <div id="resourceTab1">
                                          <!--  -->
                                      </div>
                                      <div id="highLevelTaskTab1">
                                      <!--   -->
                                      </div>
                                      <div id="reportsLeadTab1">
                                      <!-- -->
                                      </div>
                                  </div>
                </div>
            </td>
        </tr>
        <tr height="10%">
            <td></td>
        </tr>
    </table>

    <script>
   

        $("#tabs").tabs();
        

     
       
        $("#resourceTab").click(function() {
              
           
        document.location.href = "./Resource.do";
        });
        $("#highLevelTaskTab").click(function() {
        
            document.location.href = "./highLevelTask.do";
        });
        $("#reportsLeadTab").click(function() {
              
            document.location.href = "./reportsLead.do";
        });
        $("#managerTab").click(function() {
            document.location.href = "./Manager.do";
        });

       if("${tabName}" == "DE") {
          $( "#tabs" ).tabs({ active: 0 });
       }

       if("${tabName}" == "TL") {
          $( "#tabs" ).tabs({ active: 1 });
       }

       if("${tabName}" == "RE") {
          $( "#tabs" ).tabs({ active: 2 });
       }
        
        /*     var index = $('#tabs li').index($('#one'));
            alert(index);
            tabs.tabs( "option", "active", index ); */
        
          /*   $("#tabs").tabs("option", "active", index); */

    </script>

	
</td>
</tr>
<tr height="10%">
<td>
</td>
</tr>
</table>


</body>
</html>