<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>
			
<div>
	<form id="vacationStatusForm">
		<table class="ebdtable" style="width: 100%; margin-top: 10px;">
			<tr>
				<th colspan="2" style="width:20px;"><a href="#" id="vacationRequestBtn"><img class="imgLink" 	alt="Vacation Request" src="./resources/addnews.gif" width="20px;"></a></th>
				<th>Resource Name</th>
				<th>Request Date</th>
				<th>Vacation Type</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Comments</th>
				<th>Status</th>
				<th>Supervisor Comments</th>
				<th>Action</th>
			</tr>
			
			<tr>
				<c:forEach var="vacationDetails" items="${calendar}">
					<td>E</td>
					<td>D</td>
					<td>${vacationDetails.resourceName}<input type="hidden" name="vacationId" value="${vacationDetails.vacationId}"> </td>
					<td>${vacationDetails.startDate}</td>
					<td>${vacationDetails.vacationType}</td>
					<td>${vacationDetails.startDate}</td>
					<td>${vacationDetails.endDate}</td>
					<td>${vacationDetails.comments}</td>
					<td>${vacationDetails.status}</td>
					<td>
						<textarea value="${vacationDetails.approverComments}" name = "approverComments" rows="3" cols="50" id="approverComments"></textarea>
					</td>
					<td>
						<a id="vacationApproveSubmit" href="#">Update</a>
					</td>
				</c:forEach>
			</tr>
		</table>
	</form>
</div>
<c:if test="${empty calendar}">
<div id="viewVacationDetails">
	<div class="boxmsg border-boxmsg" style="width: 700px;color: red;">
	    Click on add icon to submit vacation request.Work with your team and identify your backup on your absence.
	    <b class="border-notch notch"></b>
	    <b class="notch"></b>
	</div>
</div>
</c:if>
			
			
