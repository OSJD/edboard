<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/projectmanagement-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
</head>
<body id="mainBody">
	<%@ include file="/WEB-INF/jsp/projectmanagement/projects.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/editProjects.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/release.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/editRelease.jsp" %>
	
	<table style="width: 100%;height: 100%;">
		<!-- header -->
		<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
		<!-- main container -->
		<tr>
			<td>
				<div id="tabs" style="width:100%;height:100%; padding-right: 0px;">
					<!-- MAIN MENU -->
					<%@ include file="/WEB-INF/jsp/includes/menu.jsp"%>

					   <jstl:choose>
							<jstl:when test="${edbUser.role !='Dvlp'}">
								<div id="projectPlanTab" style="clear: both;">
									<!-- SUB MENU-->
									<%@ include file="/WEB-INF/jsp/projectmanagement/submenu.jsp"%>
									<div id="ppMainContainer" style="margin-top: 20px;">
										<div style="width:100%;padding-top:10px; height: 650px; overflow: auto;">
											<jsp:include page="/WEB-INF/jsp/projectmanagement/projectPlan.jsp"
												flush="true"></jsp:include>
										</div>
									</div>
								</div>
							</jstl:when>
							<jstl:otherwise><!-- DEVELOPER -->
								<div id="projectPlanTab" style="clear: both;"></div>
							</jstl:otherwise>
					   </jstl:choose>
				</div>
			</td>
		</tr>
		<!-- footer -->
		<%@ include file="/WEB-INF/jsp/includes/footer.jsp"%>
	</table>
<div id="vacationRequestPopup" title="Vacation Request Form">
				<form id="vacationRequestForm">
					<table class="ebdtable" style="width: 100%; margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Vacation Request</th>
						</tr>
						<tr>
							<th style="width: 75px;">Vacation Type</th>
							<td><select class="text" style="width: 130px;"
								name="vacationType" id="vacationType">
									<option value="0">--select--</option>
									<option value="-1">Vacation</option>
									<option value="-2">Sick Leave</option>
									<option value="-3">Optional Holiday</option>
									<c:if test="${edbUser.role =='Admin'}">
										<option value="-4">Public Holiday</option>
									</c:if>
							</select></td>
						</tr>
						<c:if test="${edbUser.role =='Admin'}">
							<tr>
								<th>Location</th>
								<td><input type="radio" name="location"
									id="offShoreLocation" value="1" disabled="disabled">
									Off Shore <input type="radio" name="location"
									id="onShoreLocation" value="2" disabled="disabled"> On
									Shore</td>
							</tr>
						</c:if>
						<tr>
							<th>Start Date</th>
							<td><input type="text" name="vacationStartDate"
								id="vacationStartDate" class="textbox" /></td>
						</tr>
						<tr>
							<th>End Date</th>
							<td><input type="text" name="vacationEndDate" id="vacationEndDate"
								class="textbox" /><!-- <a href="#" id="buttonn1">Button2</a> --></td>
						</tr>
						<tr>
							<th>Backup</th>
							<td>
								<select class="text" style="width: 130px;"
									name="backupResource" id="backupResource">
										<option value="0">--select--</option>
										<option value="-1">Dimple</option>
										<option value="-2">Vidhya</option>
										<option value="-3">Vinoth</option>
								</select>
							</td>
						</tr>						
						<tr>
							<th colspan="2">Comments</th>
						</tr>
						<tr>
							<td colspan="2"><textarea name="comments" rows="5" cols="60"
									class="text"></textarea></td>
						</tr>

					</table>
				</form>
			</div>
</body>
</html>
<script>	
	
	var selectedTab="${selectedTab}";
	if(selectedTab.length==0)
		selectedTab=0;
	/**
	 * This function will be used to load and activate tabs
	 */
	
	$("#tabs").tabs({
		active: selectedTab,
		beforeLoad : function(event, ui) {
			ui.jqXHR.error(function(jqXHR) {
				alert(jqXHR.statusText);
				ui.panel.html("Application error! Please call help desk.");
			});
		}
	});
</script>