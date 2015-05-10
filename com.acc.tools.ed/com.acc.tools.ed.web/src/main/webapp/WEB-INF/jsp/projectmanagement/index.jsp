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
<script src="<%=request.getContextPath()%>/script/libs/edb-common.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/edb-object-model.js"></script>
</head>
<body id="mainBody">
	<%@ include file="/WEB-INF/jsp/projectmanagement/projects.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/editProjects.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/release.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/editRelease.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/addVacationRequest.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectmanagement/updateVacationRequest.jsp" %>
	<%@ include file="/WEB-INF/jsp/projectwork/addTaskPopup.jsp"%>
	<%@ include file="/WEB-INF/jsp/projectwork/editTaskPopup.jsp"%>		
		
	
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
								<div id="projectWorkTab">
									<!-- SUB MENU-->
									<%@ include file="/WEB-INF/jsp/projectwork/submenu.jsp"%>
									<div id="pwMainContainer" 
										style="width: 100%; padding-top: 10px; height: 100%; overflow: auto;">
										<jsp:include page="/WEB-INF/jsp/projectwork/myTasks.jsp" flush="true"></jsp:include>
									</div>
								</div>
								<div id="resourceManagementTab">
									<!-- SUB MENU-->
									<%@ include file="/WEB-INF/jsp/resourcemanagement/submenu.jsp"%>
									<div id="rmMainContainer" style="margin-top: 20px;">
										<jsp:include page="/WEB-INF/jsp/resourcemanagement/resourceManagement.jsp" flush="true"></jsp:include>
									</div>
								</div>
							</jstl:when>
							<jstl:otherwise><!-- DEVELOPER -->
								<div id="projectWorkTab">
									<!-- SUB MENU-->
									<%@ include file="/WEB-INF/jsp/projectwork/submenu.jsp"%>
									<div id="pwMainContainer" 
										style="width: 100%; padding-top: 10px; height: 100%; overflow: auto;">
										<jsp:include page="/WEB-INF/jsp/projectwork/myTasks.jsp" flush="true"></jsp:include>
									</div>
								</div>
							</jstl:otherwise>
					   </jstl:choose>
				</div>
			</td>
		</tr>
		<!-- footer -->
		<%@ include file="/WEB-INF/jsp/includes/footer.jsp"%>
	</table>
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