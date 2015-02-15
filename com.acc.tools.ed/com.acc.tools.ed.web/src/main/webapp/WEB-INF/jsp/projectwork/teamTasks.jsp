<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<script src="<%=request.getContextPath()%>/script/submenu-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
</head>
<body>

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
					<td style="width: 130px;">In Progress</td>
					<td style="width: 130px;">50</td>
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
													<td>In Progress</td>
													<td>60</td>
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
																			<td>01/01/2015</td>
																			<td>Submitted</td>
																			<td>${tasks.taskReviewUser}</td>
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
	<input type="hidden" id="popupDisplay"/>
	<!-- Add Task Popup -->
	<div id="addTaskPanel" title="Add Tasks" edbUser="${edbUser.employeeId}">
	<form:form commandName="addTaskForm" action="addTask.do">
		<fieldset>
			<legend>Add Tasks</legend>
			<div>
				<table class="addTask">
					<tr>
						<th style="text-align: right;">Task Type</th>
						<td><select name="taskType">
								<option value=""></option>
								<option value="AG">Assigned</option>
								<option value="AD">Adhoc</option>
								<option value="VA">Value Added</option>
						</select></td>
					</tr>
					<tr>
					<th style="text-align: right;">Task Name</th>
					<td id="taskTD" colspan="3">
					<form:select path="existingTask"  style="width:135px;" multiple="false">
									<form:option value="0" label="--- Select ---" />
    								<form:option value="-1" label="Create New Task" />
								</form:select>
					
					<div id="newTask" style="display: none;">
							<form:input type="text" path="newTaskName" class="textbox" />
							<form:hidden path="newTaskId"/>								
					</div>	
					</td>							
					</tr>
					<tr>
						<th style="text-align: right;">Description</th>
						<td><input type="text" id="taskDesc" name="taskDesc" value="" /></td>
					</tr>
					<tr>
						<th style="text-align: right;">Task Hours</th>
						<td colspan="2"><input type="text" id="taskHrs"
							name="taskHrs" value="" />&nbsp;&nbsp; Hrs</td>
					</tr>
					<tr>
						<th style="text-align: right;">Task Status</th>
						<td><select name="taskStatus">
								<option value=""></option>
								<option value="completed">Completed</option>
								<option value="inProgress">In Progress</option>
								<option value="onHold">On Hold</option>
						</select></td>
					</tr>
					<tr>
						<jstl:choose>
							<jstl:when
								test="${(edbUser.role =='SUPERVISOR') || (edbUser.role =='Lead') || (edbUser.role =='MANAGER')}">
								<th style="text-align: right;">Task Action</th>
								<td><form:select path="taskAction" id="taskAction"  style="width:135px;" multiple="false" >
									<form:option value="" label="---Select---" />
									<form:option value="approved" label="Approved" />
    								<form:option value="rejected" label="Rejected" />
								</form:select></td>
								<td>
									<div id="div1" style="display: none;">
										<form:textarea style="overflow: auto; resize: none" rows="3" id="rejComment" path="rejComment"
									cols="20" class="textarea" />
									</div>
								</td>
							</jstl:when>
							<jstl:otherwise>
								<td><form:textarea style="overflow: auto; resize: none" id="rejComment" rows="3" path="rejComment"
									cols="20" class="textarea" /></td>
							</jstl:otherwise>
						</jstl:choose>
					</tr>
				</table>
			</div>	
		</fieldset>
		</form:form>
	</div>					
</body>
</html>

