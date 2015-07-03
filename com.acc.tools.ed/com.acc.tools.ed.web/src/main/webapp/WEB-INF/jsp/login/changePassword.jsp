<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%   response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" );   response.setDateHeader( "Expires", 0 ); response.setHeader("Cache-Control","no-store");%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
</head>
	<body>


<div id="updatePassword" title="Password Update">
<div class="loginheader">Password Update</div>
				<form id="passwordUpdateForm" action="<c:url value='updatePwd.do'/>" method="post">
					<table class="ebdtable" style="width: 50%; margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Update Password</th>
						</tr>
						<tr>
							<th style="width: 75px;">Old Password</th>
							<td>
								<input type="password" name="oldPassword" id="oldPassword" class="textbox" />
								<%-- <input type="hidden" id="updateEmpId" name="empId" value ="${employeeId} "> --%>
							
							</td>
						</tr>
						
						<tr>
							<th style="width: 75px;">New Password</th>
							<td>
								<input type="password" name="newPassword" id="newPassword" class="textbox" />
							</td>
						</tr>
						<tr>
							<th style="width: 75px;">Confirm New Password</th>
							<td>
								<input type="password" name="newPassword" id="newPassword" class="textbox" />
							</td>
						</tr>
						<tr>
						<td>
						<input type="submit" value="Update Password" id="btn2" name="btn2" form="frm2" class="button"/>
						</td>
						
						</tr>
						
					</table>
				</form>
			</div>
			
			</body>
			</html>
			
