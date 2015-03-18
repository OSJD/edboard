<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script
	src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>

<div>
	<form id="vacationStatusForm">
		<table class="ebdtable" style="width: 100%; margin-top: 10px;">
			<tr>

				<th>Resource Name</th>
				<th>Request Date</th>
				<th>Vacation Type</th>
				<th>Back Up</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Comments</th>
				<th>Status</th>
				<th>Supervisor Comments</th>
				<th>Action</th>
				<th colspan="2" style="width: 20px;"><a href="#"
					id="vacationRequestBtn"><img class="imgLink"
						alt="Vacation Request" src="./resources/addnews.gif" width="20px;"></a></th>
			</tr>

			<c:forEach var="vacationDetails" items="${calendar}"
				varStatus="myIndex">
				<tr>

					<td id="vctnresourceName_${vacationDetails.vacationId}">${vacationDetails.resourceName}</td>
					<td id="vctnStartDate_${vacationDetails.vacationId}">${vacationDetails.startDate}</td>
					<td id="vctnType_${vacationDetails.vacationId}">
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
					</td>
					<td>backup Name</td>
					<c:choose>
						<c:when test="${vacationDetails.role eq 'DEVLP'}">
							<c:choose>
								<c:when test="${vacationDetails.viewFlag eq 'TRUE'}">
									<td><input type = "date" id="vacationEditStartDate_${vacationDetails.vacationId}" name = "startDate" value="${vacationDetails.startDate}" class="vctnEditStartDate"/></td>
									<td><input type = "date" id="vacationEditEndDate_${vacationDetails.vacationId}" name = "endDate" value="${vacationDetails.endDate}" class="vctnEditEndDate"/></td>
									<td><input type="text" name="vacationComments" id="vctnComments_${vacationDetails.vacationId}"	class="textbox" value="${vacationDetails.comments}" /></td>
									<td>${vacationDetails.status}</td>
									<td>${vacationDetails.approverComments}</td>
									<td><a href="#" id="${vacationDetails.vacationId}" class="vacationEditSubmit" style="width: 60px;">Update</a>  
									 <a href="#" id="${vacationDetails.vacationId}" class="vacationDeleteSubmit" style="width: 60px;">Delete</a></td>
									<td></td>
								</c:when>
								<c:otherwise>
									
									<td id="vctnStartDate_${vacationDetails.vacationId}">${vacationDetails.startDate}</td>
									<td id="vctnEndtDate_${vacationDetails.vacationId}">${vacationDetails.endDate}</td>
									<td id="vctnComments_${vacationDetails.vacationId}">${vacationDetails.comments}</td>
									<td id ="approvalType_${vacationDetails.vacationId}">${vacationDetails.status}</td>
									<td></td>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>
						
						</c:when>
						<c:otherwise>
							<td>backup</td>
							<td id="vctnStartDate_${vacationDetails.vacationId}">${vacationDetails.startDate}</td>
							<td id="vctnEndtDate_${vacationDetails.vacationId}">${vacationDetails.endDate}</td>
							<td id="vctnComments_${vacationDetails.vacationId}">${vacationDetails.comments}</td>

							<td><select id="approvalType_${vacationDetails.vacationId}"
								class="textbox" name="status">
									<c:choose>
										<c:when test="${vacationDetails.status eq 'Approved'}">
											<option value="Approved">Approved</option>
											<option value="Rejected">Rejected</option>
											<option value="OnHold">OnHold</option>
										</c:when>

										<c:when test="${vacationDetails.status eq 'Rejected'}">
											<option value="Rejected">Rejected</option>
											<option value="Approved">Approved</option>
											<option value="OnHold">OnHold</option>
											
										</c:when>
										<c:when test="${vacationDetails.status eq 'OnHold'}">
											<option value="OnHold">OnHold</option>
											<option value="Approved">Approved</option>
											<option value="Rejected">Rejected</option>
										</c:when>
										<c:otherwise>
											<option value="0">Approval Status</option>
											<option value="Approved">Approved</option>
											<option value="Rejected">Rejected</option>
											<option value="OnHold">OnHold</option>
										</c:otherwise>
									</c:choose>
									
									
							</select></td>

							<td><textarea rows="3" cols="40" name="approverComments"
									id="VctnApproverComments_${vacationDetails.vacationId}">${vacationDetails.approverComments}</textarea>
							</td>
							<td  style="background-image: none;background-color: #b5cfd2;border-width: 1px;border-style: solid;border-color: #999999;"><a href="#" id="${vacationDetails.vacationId}"
								class="vacationApproveSubmit" style="width: 75px;">Update </a></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
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


