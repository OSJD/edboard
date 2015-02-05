<script src="<%=request.getContextPath()%>/script/submenu-actions.js"></script>
<!-- SUB MENU-->
<%@ include file="/WEB-INF/jsp/resourcemanagement/submenu.jsp"%>

<div id="rmMainContainer" style="margin-top: 20px;">
	<jsp:include page="/WEB-INF/jsp/resourcemanagement/resourceManagement.jsp" flush="true"></jsp:include>
</div>