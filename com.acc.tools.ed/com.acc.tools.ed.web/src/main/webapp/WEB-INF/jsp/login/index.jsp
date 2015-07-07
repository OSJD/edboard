<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-store");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
</head>
<body>


	<div id="login" class="login">

		<div class="loginheader">Engagement Dashboard Login</div>
		<form action="<c:url value='j_spring_security_check'/>" method="post"
			name="frm2">
			<table align="center" class="logindetail">
				<tr>
					<td>Enterprise Id:</td>
					<td><input name="username" class="textbox" /></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input name="password" class="textbox" type="password" /></td>


				</tr>
				<tr>
					<td align="center" colspan="2">
						<div >
							<input type="submit" value="Login" id="btn2" name="btn2"
								form="frm2" class="button" />
						</div>
						
					</td>
				</tr>
				
			</table>
			<br/>
			<div style="font-size:12px; color:red; font-weight:bold">
							
					<c:if test="${not empty status}">						
							${status}
					</c:if>
					<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
								
        				Your login attempt was not successful due to <br />
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
     
					</c:if>
				</div>
			<input type="hidden" name="lastLoginForm" id="lastLoginForm"
				value=<%=System.currentTimeMillis()%> />
		</form>
	</div>
</body>
</html>

