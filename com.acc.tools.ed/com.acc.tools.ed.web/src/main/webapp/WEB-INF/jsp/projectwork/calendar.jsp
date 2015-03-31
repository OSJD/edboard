<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script
	src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>

<div>
	<form id="vacationStatusForm">
		<table id="vacationStatusFormTable" class="ebdtable" style="width: 100%; margin-top: 10px;">
			<tbody>
				<tr>
					<th colspan="2" style="width: 20px;">
						<a href="#" id="vacationRequestBtn"><img class="imgLink"
							alt="Vacation Request" src="./resources/addnews.gif" width="20px;"></a>
					</th>
					<th>Resource Name</th>
					<th>Request Date</th>
					<th>Vacation Type</th>
					<th>Back Up</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Comments</th>
					<th>Status</th>
					<th>Supervisor Comments</th>
				</tr>

				<c:forEach var="vacationDetails" items="${calendar}"
					varStatus="myIndex">
					<tr>
						<td style="width: 20px;">
							<a href="#" id="${vacationDetails.vacationId}" class="updateVacationDetail" style="width: 55px;">Update</a>
						</td>
						<td>
							<a href="#" id="${vacationDetails.vacationId}" class="deleteVacationDetail" style="width: 25px;">Delete </a>
						</td>
						<td><div id="resourceName${vacationDetails.vacationId}">${vacationDetails.resourceName}</div></td>
						<td><div id="requestDate${vacationDetails.vacationId}">${vacationDetails.createDate}</div></td>
						<td>
							<div id="vacationType${vacationDetails.vacationId}">
								<c:if test ="${vacationDetails.vacationType eq '-1'}">
									Vacation
								</c:if>
								<c:if test ="${vacationDetails.vacationType eq '-2'}">
									Sick Leave
								</c:if>
								<c:if test ="${vacationDetails.vacationType eq '-3'}">
									Optional Holiday
								</c:if>
								<c:if test ="${vacationDetails.vacationType eq '-4'}">
									Public Holiday
								</c:if>
							</div>
						</td>
						<td><div id="backUpResource${vacationDetails.vacationId}">${vacationDetails.backUpResource}</div></td>
						<td><div id="startDate${vacationDetails.vacationId}">${vacationDetails.startDate}</div></td>
						<td><div id="endDate${vacationDetails.vacationId}">${vacationDetails.endDate}</div></td>
						<td><div id="comments${vacationDetails.vacationId}">${vacationDetails.comments}</div></td>
						<td><div id="status${vacationDetails.vacationId}">${vacationDetails.status}</div></td>
						<td><div id="approverComments${vacationDetails.vacationId}">${vacationDetails.approverComments}</div><input type="hidden" id="supervisorId${vacationDetails.vacationId}" value="${vacationDetails.supervisorId}"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</div>
<c:if test="${empty calendar}">
	<div id="viewVacationDetails">
		<div class="boxmsg border-boxmsg" style="width: 700px; color: red;">
			Click on add icon to submit vacation request.Work with your team and
			identify your backup on your absence. <b class="border-notch notch"></b>
			<b class="notch"></b>
		</div>
	</div>
</c:if>


