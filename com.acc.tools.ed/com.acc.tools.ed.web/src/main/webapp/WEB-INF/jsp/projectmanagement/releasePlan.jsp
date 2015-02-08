<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


	<td colspan="6"
		style="background-image: none; border-bottom-color: white;">
		<div style="width: 1000px; overflow: auto;">
			<table style="width: 100%; border-color: black;">
				<tr>
					<th style="width: 150px;">Resource</th>
					<c:forEach var="releasePlan"
						items="${releasePlan.releasePlanHeader.resourceWeekWorkPlan}">
						<th style="width: 300px;">
							<table style="width: 100%; height: 100%;">
								<tr>
									<th colspan="${releasePlan.value.size()}"
										style="text-align: center;">${releasePlan.key}</th>
								</tr>
								<tr>
									<c:forEach var="dayPlan" items="${releasePlan.value}">
										<th style="width: 70px;">${dayPlan.date}<br>(${dayPlan.day})
										</th>
									</c:forEach>
								</tr>
							</table>
						</th>
					</c:forEach>
				</tr>
				<c:forEach var="workPlan" items="${releasePlan.resourceWorkPlan}" varStatus="wrkStatus">
					<tr>
						<td>${workPlan.employeeName}<input type="hidden" id="resource${wrkStatus.index}" value="${workPlan.employeeId}"></td>
						<c:forEach var="weekPlan" items="${workPlan.resourceWeekWorkPlan}" varStatus="wkStatus">
							<td>
								<table style="width: 100%; height: 100%;">
									<tr>
										<c:forEach var="dayPlan" items="${weekPlan.value}">
											<td><input type="text" size="1" value="${dayPlan.hours}" id="resDayHour${wrkStatus.index}_${weekPlan.key}${dayPlan.day}"></input></td>
										</c:forEach>
									</tr>
								</table>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
	</td>
