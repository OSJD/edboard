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
		<c:if test="${not empty status}">
			<div class="boxmsg border-boxmsg" style="width: 580px;color: red;margin-left: 650px;margin-top: 200px;position: absolute;">
				${status}
			</div>
		</c:if>
		<div id="login" class="login"> 
			<div class="loginheader">Engagement Dashboard Login</div>
			<form action="login.do" method="post" name="frm2">
					<table align="center" class="logindetail">
						<tr>
							<td>Enterprise Id:</td>
							<td><input name="enterpriseId" class="textbox" /></td>
							
						</tr>
						<tr>
							<td colspan="2">
								<div style="margin-left: 70px;width: 50px;float: left;"><input type="submit" value="Login" id="btn2" name="btn2" form="frm2" class="button"/></div>
							</td>
					</table>
					<input type="hidden" name="lastLoginForm" id="lastLoginForm" value=<%= System.currentTimeMillis() %> />
			</form>
		</div>
	</body>
</html>

