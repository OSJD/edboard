<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>
			
<div>
	<table class="ebdtable" style="width: 100%;">
		<tr>
			<th colspan="2" style="width:50px;"><a href="#" id="vacationRequestBtn"><img class="imgLink" 	alt="Vacation Request" src="./resources/addnews.gif" width="20px;"></a></th>
			<th colspan="9">Vacation Request Status</th>
		</tr>
		<tr>
			<th colspan="2"></th>
			<th>Resource Name</th>
			<th>Request Date</th>
			<th>Vacation Type</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Comments</th>
			<th>Status</th>
			<th>Supervisor Comments</th>
		</tr>
	</table>
</div>
			
			
