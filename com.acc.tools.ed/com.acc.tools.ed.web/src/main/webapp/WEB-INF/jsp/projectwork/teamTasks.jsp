<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<%@ include file="/WEB-INF/jsp/projectwork/addTaskPopup.jsp"%>
	<table class="ebdtable" style="width: 100%;">
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
				
					<td style="width: 130px;"><a href="#" class="releaseRow" id="${release.releaseId}">${release.releaseName}<div id="comptree${release.releaseId}" style="float:left; clear: both;width: 20px;margin-left: 5px;">[+]</div></a></td>
					<td style="width: 130px;">${release.releaseDesc}</td>
					<td style="width: 130px;">${project.projectName}</td>
					<td style="width: 130px;">${release.releaseStartDate}</td>
					<td style="width: 130px;">${release.releaseEndDate}</td>
					<c:choose>
									<c:when test="${release.percentage eq 100 }">
										<td style="width: 130px;">COMPLETED</td>
									</c:when>
									<c:otherwise>
									<td style="width: 130px;">IN PROGRESS</td>
									</c:otherwise>
									</c:choose>
					<td style="width: 130px;">${release.percentage}%</td>
				</tr>
				<tr id="release${release.releaseId}" class="componentData">
					<td style="background-image: none; background-color: white;" colspan="7">
						<table class="innertable2" style="width: 100%;">
							<tr>
								<th style="width: 145px;">Developer Name</th>
							</tr>
							<c:forEach items="${release.teamTasks}" var="teamTask" varStatus="loop">
								<tr>
									<td><a href="#" id="${release.releaseId}${fn:replace(teamTask.key,' ', '')}" class="devRow"><div id="devtree${release.releaseId}${fn:replace(teamTask.key,' ', '')}" style="float:left; clear: both;width: 20px;margin-left: 5px;">[+]</div>${teamTask.key}</a></td>
								</tr>
								<tr id="devdev${release.releaseId}${fn:replace(teamTask.key,' ', '')}" class="compData">
									<td style="background-image: none; background-color: white;">
										<table class="innertable2" style="width: 100%;">
											<tr>
												<th style="width: 145px;">Component Name</th>
												<th style="width: 75px;">Component Phase</th>
												<th style="width: 560px;">Functional Desc</th>
												<th style="width: 560px;">Assigned Work Desc</th>
												<th style="width: 80px;">Start Date</th>
												<th style="width: 80px;">End Date</th>
												<th style="width: 80px;">Status</th>
												<th style="width: 80px;">%Completed</th>
												<th colspan="2" style="width: 10px;">Actions</th>
											</tr>
											<c:forEach items="${teamTask.value}" var="component">
												<tr>
													<td><a href="#" id="${component.componentId}${fn:replace(teamTask.key,' ', '')}" class="componentRow"><div id="tasktree${component.componentId}" style="float:left; clear: both;width: 20px;margin-left: 5px;">[+]</div>${component.componentName}</a></td>
													<td>Requirements</td>									
													<td>${component.functionalDesc}</td>
													<td>${component.workDesc}</td>
													<td>${component.startDate}</td>
													<td>${component.endDate}</td>
													<c:choose>
									<c:when test="${component.percentage eq 100 }">
									<td>COMPLETED</td>
									</c:when>
									<c:otherwise>
									<td>IN PROGRESS</td>
									</c:otherwise>
									</c:choose>
									
									<td >${component.percentage}%</td>
													<td>
														<a href="#" taskType="teamTasks" class="addTaskPopup" id="${component.componentId}"><img
															class="imgLink" alt="add comnponent" src="./resources/addnews.gif"></a>
													</td>
													<td>De</td>
												</tr>
												<tr id="component${component.componentId}${fn:replace(teamTask.key,' ', '')}" class="taskData">
													<td colspan="10" style="background-color: white;">
														<table class="innertable1" id="taskTable${component.componentId}" style="width: 100%;">
															<tr>
																<th style="width: 150px;">Task Name</th>
																<th style="width: 220px;">Task Description</th>
																<th style="width: 40px;">Task Hours</th>
																<th style="width: 75px;">Task Date</th>
																<th style="width: 60px;">Review Status</th>
																<th style="width: 150px;">Review By</th>
																<th style="width: 220px;">Review Comments</th>
																<th style="width: 220px;">Comments</th>
																<th colspan="2" style="width: 150px;">Actions</th>
															</tr>
															 <c:choose>
				        										<c:when test="${empty component.taskFormList}">
																	<tr>
																		<td colspan="10" style="font-weight: bold;text-align: center;">
																			<div id="noComponetMsg" class="boxmsg border-boxmsg" style="margin-left:50px; width: 780px;color: red;">
						    													<p>The assigned work is not yet started.</p>
						    													<b class="border-notch notch"></b>
						    													<b class="notch"></b>
																			</div>
																		</td>
																	</tr>
																</c:when>
																<c:otherwise>
																	<c:forEach var="tasks" items="${component.taskFormList}">
																		<tr id="taskDatta_${tasks.taskId}" style="width: 100%;">
																			<td>${tasks.taskName}<input type="hidden" id="taskIdValue" value="${tasks.taskId}"/></td>
																			<td>${tasks.taskDesc}</td>
																			<td>${tasks.taskHrs}</td>
																			<td>${tasks.taskCreateDate}</td>
																			<td>Submitted</td>
																			<td>${tasks.taskReviewUserName}</td>
																			<td>${tasks.rejComment}</td>
																			<td>${tasks.taskComments}</td>
																			<td><a href="#" taskType="teamTasks"  id="editTask" onclick="edit('${tasks.taskId}');"><img alt="edit project" src="./resources/edit.gif"
																				width="20px;"></a>
																			</td>
																			<td><a href="#"  id="deleteTask" onclick="deleteTask('${tasks.taskId}');"><img alt="delete project" src="./resources/delete.gif"
																				width="20px;"></a>
																			</td>
																		</tr>
																	</c:forEach>
																</c:otherwise>
															</c:choose>
														</table>
													</td>
												</tr>
										</c:forEach>
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
