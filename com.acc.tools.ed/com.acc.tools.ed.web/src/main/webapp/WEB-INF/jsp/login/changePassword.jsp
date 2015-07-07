<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%   response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" );   response.setDateHeader( "Expires", 0 ); response.setHeader("Cache-Control","no-store");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>



</head>
<body>


	<div id="updatePassword" title="Password Update" class="login">
		<div class="loginheader">Password Update</div>
		<form id="passwordUpdateForm" action="<c:url value='updatePwd.do'/>"
			method="post">
			<table class="ebdtable" >
				
				<tr>
					<th style="width: 150px;">Old Password</th>
					<td><input type="password" name="oldPassword" id="oldPassword"
						class="textbox" /> <%-- <input type="hidden" id="updateEmpId" name="empId" value ="${employeeId} "> --%>

					</td>
				</tr>

				<tr>
					<th style="width: 150px;">New Password</th>
					<td><input type="password" name="newPassword" id="newPassword"
						class="textbox" /></td>
				</tr>
				<tr>
					<th style="width: 150px;">Confirm New Password</th>
					<td><input type="password" name="confirmPassword"
						id="confirmPassword" class="textbox" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Update Password" id="btn2"
						name="btn2" class="button" onclick="return validate();" /></td>

				</tr>

			</table>
		</form>
	</div>

</body>
<script type="text/javascript">
function validate()
{
    var a = document.getElementById("oldPassword");
    var b = document.getElementById("newPassword");
    var c = document.getElementById("confirmPassword");
    var valid = true;
    if(a.value.length==0)
    	{
    	alert("Enter a valid old password!!");
    				return false;  
    	}
    if(b.value.length==0)
        {
            alert("Enter a valid new password!");
            
            return false;  
        }
    if(c.value.length==0)
    	{
    	alert("Confirm passowrd cannot be empty");
    	
    	return false;  
    	}
    if(b.value!= c.value)
    	{
    	alert("Passwords are not matching!!");
    	return false;
    	}
    return true;  
    }
   </script>
</html>

