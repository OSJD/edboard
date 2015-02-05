<%@ page language="java" contentType="text/html;   charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
    <%@ page import="java.sql.*" %>
    <%@ page import="com.acc.tools.ed.integration.dto.Milestone" %>
     <%@ page import="com.acc.tools.ed.integration.helper.EDBDataConnection" %>
    	
     <%
    	     	response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" );   response.setDateHeader( "Expires", 0 );
    	     %>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="Links.jsp"></jsp:include>

 

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>

.imgLink{border: none};
 </style>
<title>jQuery UI Datepicker - Default functionality</title>
<link rel="stylesheet"
    href="css\jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
       $(function() {
              $("#datepicker").datepicker({ dateFormat: 'yy-mm-dd'});
       });
       $(function() {
           $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd'});
    });
       $(document).ready(function() {
           $(".edit").click(function() {
        	
        	 	var dad=$(this).parent().parent();
        	 	var id=$(dad).attr("id");
        	 	 var edate=$(dad).children().eq(-3).html();
        	 	 $(dad).children().eq(0).replaceWith("<td><input type='hidden' name='hid' value='"+id+"'/></td>");
        	 	$(dad).children().eq(-3).replaceWith("<td><input type='text' value='"+edate+"' id='three' name='edate' /></td>");
        	 	 var sdate=$(dad).children().eq(-4).html()
        	 	$(dad).children().eq(-4).replaceWith("<td><input type='text' value='"+sdate+"' id='two' name='sdate' /></td>"); 
                  // var milestone $(dad).children().eq(-5).html().replaceWith("<input type='text' value='"+"' id='two' name='sdate' />"); 

                  var namedad=$(dad).children().eq(-6);
                  var name=$(namedad).children().html();
                  $(dad).children().eq(-6).replaceWith("<input type='text' value='"+name+"' id='two' name='a' />"); 
                  var edit=$(dad).children().eq(-2).html();
                  $(dad).children().eq(-2).replaceWith("<td><input type='image' src='save.gif' alt='resources\submit' width='20' height='20' /></td>"); 
                   //$("edit").hide();
                   /* var urlText= $("#one").html();
                   alert(urlText);
                   $("#one").replaceWith("<input type='text' value='"+urlText+"' name='a'/>"); */


                 
             
           });
       });
     
    	
    	 
</script>
</head>
<script type="text/javascript" src="a.js"></script>
<%
	response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" );   response.setDateHeader( "Expires", 0 );
%>
</head>
<body>

	<%
		try{

	Connection connection =  EDBDataConnection.createConnection();
	       Statement statement = connection.createStatement() ;

	      ResultSet resultset =null;
	%>

<a href="createMilestone.do">New Milestone</a>


	<form name="logForm1" method="post" action="AddHLTasks.do">
		<table>
			<tr>
				<th colspan="8">Add High Level Task:</th>
			</tr>
			<tr>
				<td>Task Type</td>
				<td><select name="tasktype">
						<option>Design</option>
						<option>Build</option>
						<option>Implement</option>
						<option>Test</option>
						<option>Deploy</option>
				</select></td>
				<td>HL Task Name:</td>
				<td><input type="text" name="hlname" /></td>
				<td>Milestone :</td>
				<td><select name="list">
						<%
      	List<Milestone> list=(List<Milestone>) request.getAttribute("milestones");
        int loop=list.size();
        while(loop>0)
        { 
        Milestone mile=list.get(loop-1);
        %>
						<option value="<%= mile.getProj_id()%>">
							<%= mile.getProj_name()%>
						</option>
						<%
        loop--;
        } 
        %>
				</select></td>
				<td>Starting on:</td>
				<td><input type="date" name="sdate" id="datepicker" /></td>
				<td>Ending on:</td>
				<td><input type="date" name="edate" id="datepicker1" /></td>
			</tr>
			<tr>
				<td colspan="7"></td>
				<td><input type="submit" value="Add" /></td>


			</tr>
			<%}catch(Exception e)
        {
        	e.printStackTrace();
        } %>

			<!-- <-- hard code-->
			<%-- <%session.setAttribute("enterprise_id", "murli.gavarasana");%> --%>


		</table>
	</form>

	<br>
	<br>
	<br>
<div align="center">${deleteMessage }  </div>
	<form method="post" action="update.do">
		<table>
			<tr bgcolor="#66CC99">
				<th scope="col">  </th>		
				<th scope="col">Task Type</th>
				<th scope="col">Task Name</th>
				<th scope="col">Milestone Name</th>

				<th scope="col">Start Date</th>

				<th scope="col">End Date</th>



			</tr>

			<c:forEach items="${allEvents}" var="event">
			
				<tr id="${event[5]}">
				<td>
				</td>
				
					<td align="center"> 
										<input type="hidden" value="${event[6]}" name="mid" />
						${event[4]}</td>
					<c:url var="url" value="success.do">
						<c:param name="id" value="${event[0]}"></c:param>
						<c:param name="ml" value="${event[1]}"></c:param>
						<c:param name="sdate" value="${event[2]}"></c:param>
						<c:param name="edate" value="${event[3]}"></c:param>
						<c:param name="hid" value="${event[5]}"></c:param>
						<c:param name="mid" value="${event[6]}"></c:param>
					</c:url>
					<td align="center"><a href="${url }" id="one">${event[0]}
					</a></td>
					<td align="center"><a
						href="searchLProjDetails.do?ml=${event[1]}">${event[1]}</a></td>
					<td align="center">${event[2]}</td>
					<td align="center">${event[3]}</td>
					




					<td><a href="#" class="edit"><img class="imgLink" src="resources\edit.gif" width="20" height="20"></a></td>
					<!-- <input type="submit" value="Submit" id="submit"
						style="visibility: hidden;" /> -->
					<td><a href="deleteHLTask.do?hid=${event[5]}"><img class="imgLink" src="resources\ico_delete.gif" width="20" height="20"></a></td>
					</tr>
					
			</c:forEach>




		</table>
	</form>
</body>
</html>