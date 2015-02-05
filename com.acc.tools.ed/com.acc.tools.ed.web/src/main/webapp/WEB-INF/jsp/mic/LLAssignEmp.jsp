<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ page import="java.sql.*" %>
       
         <%@ page import="com.acc.tools.ed.integration.helper.EDBDataConnection" %>
           <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
           <jsp:include page="Links.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

.imgLink{border: none};
 </style>
    <%
    	try{

    Connection connection =  EDBDataConnection.createConnection();
           Statement statement = connection.createStatement() ;

          ResultSet resultset =null;
    %>
<%
       resultset= statement.executeQuery("select e.emp_id,e.emp_entrps_id from emp_dtl e where e.emp_tl_id='"+session.getAttribute("enterprise_id")+"'") ;%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
       href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<title>Insert title here</title>
</head>
<body>



<script>

$(document).ready(function() {
    $(".edit").click(function() {
            
    	var dad=$(this).parent().parent();
	 	var id=$(dad).attr("id");
	 	
	 	 var emp_id1=$(dad).children().eq(1).html();
	 	
	 	 var desc=$(dad).children().eq(2).html();
	 	 
	 	 
	 	  
         
	 	
	 	 $(dad).children().eq(1).replaceWith("<td><input type='hidden' name='aid' value='"+id+"'/><select name='emp_id1'><% resultset.beforeFirst(); while(resultset.next()){ %>"
	 		 +" <option  value=\"<%= resultset.getString(1)%>\"><%= resultset.getString(2)%></option> "
	 		 +"<% } %> </select></td>");
	 	 
	 	 
	 	 
	 	$(dad).children().eq(2).replaceWith("<td><input type='text'  name='desc' value='"+desc+"' /></td>");
	 	 $(dad).children().eq(3).replaceWith("<td><input type='image' src='save.gif' alt='resources\submit' width='20' height='20' /></td>"); 
	 	var href=window.location.href;
	 	//trying to set emp_id as extra parameter, to use it in selecting the option previously selected.
	 	/* alert(href);
	 	window.location=href.split("#",200)[0]+"&name="+emp_id1+"#"; */
	 	 

    }); 
});


</script>



<table>
<tr>
<td width="20px"></td><td></td>
<td>
Assign Employee For Subtask: </td><td width="1px"></td><td>"<%=session.getAttribute("lltaskname") %> "</td><td>of High Level Task:</td> <td width="1px"></td><td>" <%=session.getAttribute("hlname") %>"
</td></tr>
</table>
<form action="lldetails.do">
<table>
<tr>
<td><input type="hidden" name="hltaskname" value="<%=session.getAttribute("hlname") %>" /></td>
<td><input type="hidden" name="lltaskname" value="<%=session.getAttribute("lltaskname") %>"/></td>
<td><input type="hidden" name="ml" value="<%=session.getAttribute("ml") %>" /></td>
</tr><tr>
<td>Description</td>
<td><input type="text" name="description"/> </td>
</tr>
<tr>
<td>Assign Employee:</td>
 <td><select name="emp_id"><%
 resultset.beforeFirst();
        while(resultset.next()){ %>
            <option value="<%= resultset.getString(1)%>"><%= resultset.getString(2)%></option>
        <% } %>
         </select> 
			 
      
			</td>
			</tr>
			<tr>
			<td></td>
			<td>
<input type="submit" value="ADD"/></td>
	<%}catch(Exception e)
        {
        	e.printStackTrace();
        } %>
</tr>




</table>



</form>


<br>
<br>
<br>
<br>

<form action="updateAssignEmp.do">
<table>
 	<tr bgcolor="#66CC99">
 	
					<th scope="col">Low Level Task Name</th>
					<th scope="col">Employee Assigned</th>
					
					<th scope="col">Description</th>
					
					
					
						
					
				</tr>
<c:forEach items="${allEvents}" var="event" >

					<tr id="${event[3]}">
					
					<td scope="col">
					${event[0]}
					</td>
						<td align="center">${event[1]}</td>
					<td align="center">${event[2]}</td>
							
						<td>
						<a href="#" class="edit"><img class="imgLink" src="resources\edit.gif" width="20" height="20"></a></td>
				<!-- 		
					<td><input type="submit" value="Submit" id="submit"
						style="visibility: hidden;" /> -->
						<td><a href="deleteAssignEmp.do?aid=${event[3]}"><img class="imgLink" src="resources\ico_delete.gif" width="20" height="20"></a></td>
								<td>
								
					</tr>
					</c:forEach>


</table>
</form>
</body>
</html>