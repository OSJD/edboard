<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="<%=request.getContextPath()%>/script/submenu-actions.js"></script>
<script
	src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
	
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
									<td><a href="#" taskType="myTasks" class="addTaskPopup"
										id="${component.componentId}"><img class="imgLink"
											alt="add comnponent" src="./resources/addnews.gif"></a></td>
									<td>De</td>
								</tr>
								<tr id="component${component.componentId}" class="taskData">
									<td colspan="10" style="background-color: white;">
										<table class="innertable1"
											id="taskTable${component.componentId}">
											<tbody>
												<tr>
													<th style="width: 150px;">Task Name</th>
													<th style="width: 220px;">Task Description</th>
													<th style="width: 40px;">Task Hours</th>
													<th style="width: 75px;">Task Date</th>
													<th style="width: 60px;">Review Status</th>
													<th style="width: 150px;">Review By</th>
													<th style="width: 220px;">Review Comments</th>
													<th style="width: 220px;">Today Work</th>
													<th colspan="2" style="width: 150px;">Actions</th>

												</tr>

												<c:choose>
													<c:when test="${empty component.taskFormList}">
														<tr id="NoTask${component.componentId}">
															<td colspan="10"
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
																<td>${tasks.taskHrs}</td>
																<td>${tasks.taskCreateDate}</td>
																<td>${tasks.taskAction}</td>
																<td>${tasks.taskReviewUser}</td>
																<td>${tasks.rejComment}</td>
																<td>${tasks.taskComments}</td>
																<td><a href="#" taskType="myTasks" id="editTask"
																	onclick="edit('${tasks.taskId}');"><img
																		alt="edit project" src="./resources/edit.gif"
																		width="20px;"></a></td>
																<td><a href="#" id="deleteTask"
																	onclick="deleteTask('${tasks.taskId}');"><img
																		alt="delete project" src="./resources/delete.gif"
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
	<input type="hidden" id="popupDisplay" />
	<!-- Add Task Popup -->
	<div id="addTaskPanel" title="Add Tasks"
		edbUser="${edbUser.employeeId}">
		<form:form commandName="addTaskForm" id="addTaskForm">
			<fieldset>
				<legend>Add Tasks</legend>
				<table class="ebdtable" style="width: 100%;">
					<tr>
						<td colspan="8">
							<table class="innertable2" style="width: 100%;">
								<tr>
									<th style="width: 100px; height: 25px;">Project Name</th>
									<th style="width: 100px;">Release Name</th>
									<th style="width: 100px;">Component Name</th>
									<th style="width: 250px;">Assigned Work</th>
									<th style="width: 100px;">Start Date</th>
									<th style="width: 100px;">End Date</th>
								</tr>
								<tr>
									<td><div id="taskProjectName"></div></td>
									<td><div id="taskReleaseName"></div></td>
									<td><div id="taskComponentName"></div></td>
									<td style="height: 50px;">
										<div style="overflow: auto;" id="taskAssignedWork"></div>
									</td>
									<td><div id="taskCompStartDate"></div></td>
									<td><div id="taskCompEndDate"></div></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th style="text-align: right;">Task Type</th>
						<td style="width: 40px;"><form:select path="taskType"
								id="taskType" multiple="false">
								<form:option value="" label="---Select---" />
								<form:option value="as" label="Assigned" />
								<form:option value="ad" label="Adhoc" />
								<form:option value="va" label="Value Added" />
							</form:select></td>
						<th style="text-align: right; width: 55px;">Task Name</th>
						<td id="taskNamePosition" style="width: 260px;">
							<form:select path="taskName" id="taskNameSelect" class="textbox" multiple="false" cssStyle="width : 120px;">
								<form:option  value="0" label="--- Select ---" />
								<form:option  value="-1" label="Create New Task" />
							</form:select>						
							<div id="newTask" style="float: right; display: none;">
								<form:input type="text" path="taskName" id="taskName"
									class="textbox" />
								<form:hidden path="componentId" id="componentId" />
								<form:hidden path="taskId" id="taskId" />
							</div>
						</td>
						<th style="text-align: right; width: 75px;">Task Start Date</th>
						<td><form:input type="date" path="taskStartDate"
								id="taskStartDate" class="textbox" cssStyle="width:75px;" /></td>
						<th style="text-align: right; width: 75px;">Task End Date</th>
						<td><form:input type="date" path="taskEndDate"
								id="taskEndDate" class="textbox" cssStyle="width:75px;" /></td>
					</tr>
					<tr>
						<th style="text-align: right; width: 75px;">Task Description</th>
						<td colspan="3"><form:textarea type="text" path="taskDesc"
								id="taskDesc" cssClass="textbox" cols="70" rows="5" /></td>
						<th style="text-align: right; width: 75px;">Development Artifacts</th>
						<td colspan="3">
							<div style="overflow: auto;height: 100px;">
								<table style="width: 100%;">
									<tr>
										<th style="width: 50px;">File Name</th>
										<td><input></td>
									</tr>
								</table>
							</div>
							<div style="margin-left: 30%;"><button>Add another File</button></div>
						</td>
					</tr>
					<tr>
						<td colspan="8"
							style="text-align: center; background-image: none; background-color: white;">
							<table style="width: 100%;">
								<tr>
									<th colspan="4" style="text-align: left;font-size: 15px;">Current Date() - Task Details</th>
								</tr>
								<tr>
									<th>Today Work</th>
									<th>Task Hours</th>
									<th>Task Status</th>
									<th>Peer Review by</th>
								</tr>
								<tr>
									<td>
										<div id="divComment">
											<form:textarea cssStyle="overflow: auto; resize: none;" rows="5" id="taskComments" path="taskComments" cols="100"	cssClass="textarea"  />
										</div>
									</td>
									<td><form:input type="text" path="taskHrs" id="taskHrs"
											class="textbox" cssStyle="width:35px;" /></td>
									<td><form:select path="taskStatus" id="taskStatus"
											style="width:135px;" multiple="false">
											<form:option value="" label="---Select---" />
											<form:option value="completed" label="Completed" />
											<form:option value="inProgress" label="In Progress" />
											<form:option value="onHold" label="On Hold" />
										</form:select></td>
									<td>Murali Gavarasana</td>
								</tr>								
								<tr>
									<td colspan="4" style="background-image: none;background-color: white;">
										<table style="width: 100%;">
											<tr>
												<td style="width: 25%;">
													<table style="width: 100%;">
														<tr><th colspan="4">Code Quality Matrix</th></tr>
														<tr>	
				 											<th style="width: 50px;">Bloker</th>
				 											<td style="width: 50px;">0</td>
				 											<th style="width: 50px;">CCN</th>
				 											<td></td>
				 										</tr>
														<tr>
															<th >Cirital</th>
															<td>0</td>
															<th>Total Test Cases</th>
															<td></td>
														</tr>
														<tr>
															<th >Major</th>
															<td>0</td>
															<th>Test Coverage</th>
															<td></td>
														</tr>
														<tr>
															<th>Minor</th>
															<td>0</td>
															<td></td>
															<td></td>
														</tr>
														<tr>
															<th>Info</th>
															<td>0</td>
															<td></td>
															<td></td>
												 		</tr>
													</table>
												</td>
												<td>
													<div style="height:175px; overflow: auto;">
														<table style="width: 100%;">
															<tr><th colspan="4">Peer Review Details</th></tr>
															<tr>	
					 											<th>Review Comment</th><th>Developer Update</th>
					 										</tr>
															<tr>
																<td>
																	<div style="width: 325px;height: 60px;overflow: auto;">
																		Resview Comment 1
																	</div>
																</td><td><textarea cols="62" rows="5"></textarea></td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
<%-- 								<tr id="taskActionRow" style="display: none">
								
									<th >Current Date() - Task Details</th>
									<th style="text-align: right;">Task Action</th>
									<td><form:select path="taskAction" id="taskAction"
											style="width:135px;" multiple="false">
											<form:option value="" label="---Select---" />
											<form:option value="approved" label="Approved" />
											<form:option value="rejected" label="Rejected" />
											<form:option value="review" label="Review" />
										</form:select> <form:hidden path="taskReviewUser" id="taskReviewUser" /></td>
									<td>
										<div id="div1" style="display: none;">
											<form:textarea style="overflow: auto; resize: none" rows="3"
												id="rejComment" path="rejComment" cols="20" class="textarea" />
										</div>
									</td>
								</tr> --%>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</form:form>
	</div>
