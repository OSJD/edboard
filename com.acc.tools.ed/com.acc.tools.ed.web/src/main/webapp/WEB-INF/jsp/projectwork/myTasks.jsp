<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/edb-common.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/edb-object-model.js"></script>

	<table class="ebdtable">
		<tr>
			<th style="width: 70px; font-weight: bold;">Release Name</th>
			<th style="width: 70px; font-weight: bold;">Release Desc</th>
			<th style="width: 70px; font-weight: bold;">Project Name</th>
			<th style="width: 70px; font-weight: bold;">Release Start Date</th>
			<th style="width: 70px; font-weight: bold;">Release End Date</th>
			<th style="width: 70px; font-weight: bold;">Status</th>
			<th style="width: 70px; font-weight: bold;">%Completed</th>
		</tr>
		<c:forEach items="${projData}" var="project">
			<c:forEach items="${project.releases}" var="release">
				<tr>
					<td style="width: 130px;"><a href="#" class="releaseRow"
						id="${release.releaseId}">${release.releaseName}<div
								id="comptree${release.releaseId}"
								style="float: left; clear: both; width: 20px; margin-left: 5px;">[+]</div></a></td>
					<td style="width: 130px;">${release.releaseDesc}</td>
					<td style="width: 130px;">${project.projectName}</td>
					<td style="width: 130px;">${release.releaseStartDate}</td>
					<td style="width: 130px;">${release.releaseEndDate}</td>
					<td style="width: 130px;">In Progress</td>
					<td style="width: 130px;">50</td>
				</tr>
				<tr id="release${release.releaseId}" class="componentData">
					<td style="background-image: none; background-color: white;"
						colspan="7">
						<table class="innertable2" style="width: 100%;">
							<tr>
								<th style="width: 145px;">Component Name</th>
								<th style="width: 75px;">Component Phase</th>
								<th style="width: 280px;">Functional Desc</th>
								<th style="width: 280px;">Assigned Work Desc</th>
								<th style="width: 80px;">Start Date</th>
								<th style="width: 80px;">End Date</th>
								<th style="width: 80px;">Status</th>
								<th style="width: 80px;">%Completed</th>
								<th colspan="2" style="width: 10px;">Actions</th>
							</tr>
							<c:forEach items="${release.components}" var="component">
								<tr>
									<input type="hidden" id="projName${component.componentId}" value="${project.projectName}" />
									<input type="hidden" id="releaseName${component.componentId}" value="${release.releaseName}" />
									<input type="hidden" id="componentName${component.componentId}" value="${component.componentName}" />
									<input type="hidden" id="assignedWork${component.componentId}" value="${component.workDesc}" />
									<input type="hidden" id="startDate${component.componentId}" value="${component.startDate}" />
									<input type="hidden" id="endDate${component.componentId}" value="${component.endDate}" />
									
									<td><a href="#" id="${component.componentId}"
										class="componentRow"><div
												id="tasktree${component.componentId}"
												style="float: left; clear: both; width: 20px; margin-left: 5px;">[+]</div>${component.componentName}</a></td>
									<td>Requirements</td>
									<td>${component.functionalDesc}</td>
									<td>${component.workDesc}</td>
									<td>${component.startDate}</td>
									<td>${component.endDate}</td>
									<td>In Progress</td>
									<td>60</td>
									<td>
										<a href="#" class="addTaskPopup" projectId="${project.projectId}" id="${component.componentId}">
											<img class="imgLink" alt="add Task" src="./resources/addnews.gif">
										</a>
									</td>
									<td>De</td>
								</tr>
								<tr id="component${component.componentId}" class="taskData">
									<td colspan="10" style="background-color: white;">
										<table class="innertable1"
											id="taskTable${component.componentId}">
											<tbody>
												<tr>
													<th style="width: 150px;">Task Name</th>
													<th style="width: 320px;">Task Description</th>
													<th style="width: 75px;">Work Type</th>
													<th style="width: 75px;">Create Date</th>
													<th style="width: 75px;">Start Date</th>
													<th style="width: 75px;">End Date</th>
													<th style="width: 60px;">Status</th>
													<th style="width: 60px;">System Status</th>
													<th style="width: 220px;">System Comments</th>
													<th colspan="2" style="width: 40px;">Actions</th>

												</tr>

												<c:choose>
													<c:when test="${empty component.taskFormList}">
														<tr id="NoTask${component.componentId}">
															<td colspan="11"
																style="font-weight: bold; text-align: center;">
																<div id="noComponetMsg" class="boxmsg border-boxmsg"
																	style="margin-left: 50px; width: 780px; color: red;">
																	<p>The assigned work is not yet started.</p>
																	<b class="border-notch notch"></b> <b class="notch"></b>
																</div>
															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<c:forEach var="tasks" items="${component.taskFormList}">
															<tr id="taskDatta_${tasks.taskId}" style="width: 100%;">
																<td>${tasks.taskName}<input type="hidden"
																	id="taskIdValue" value="${tasks.taskId}" />
																</td>
																<td>${tasks.taskDesc}</td>
																<td>${tasks.workType}</td>
																<td>${tasks.taskStartDate}</td>
																<td>${tasks.taskStartDate}</td>
																<td>${tasks.taskEndDate}</td>
																<td>${tasks.taskStatus}</td>
																<td></td>
																<td></td>
																<td>
																	<a href="#" class="editTaskPopup" taskId="${tasks.taskId}" id="${component.componentId}">
																		<img class="imgLink" alt="edit Task" src="./resources/edit.gif" width="20px;">
																	</a>
																</td>
																<td><a href="#" id="deleteTask"
																	onclick="deleteTask('${tasks.taskId}');"><img class="imgLink"
																		alt="Delete Task" src="./resources/delete.gif"
																		width="20px;"></a></td>
															</tr>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
