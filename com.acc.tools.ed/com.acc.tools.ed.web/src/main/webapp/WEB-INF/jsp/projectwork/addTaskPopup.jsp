	<!-- Add Task Popup -->
	<script type="text/javascript">


var newwindow;
function poptastic(url)
{
	newwindow=window.open(url,'name','height=400,width=200');
	if (window.focus) {newwindow.focus()}
}

</script>
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
									<th style="text-align: left">
										Activites	
										<input type="submit" value=" < " class="CommandButton "/>
					<%-- 					<form:select path="taskActivity"
											style="width:135px;" multiple="false">
											<form:option  value="0" label="--- Select ---" />
											 <c:forEach items="${activityList}" var="activity">
				      						  <option value="${activity.id}" >${activity.label}</option>
				   							 </c:forEach>
										</form:select> --%>
							
										<input type="submit" value=" > " class="CommandButton "/>
										<a href="#" taskType="teamTasks" class="viewComments" id="viewCommentsId" title="ComponentDetails" style="text-align:right"> History</a>
										<input type="submit" value=" +Comment " class="CommandButton " style="float: right;"/>
										</div>
									
									</th>
									<th>Task Hours</th>
									<th>Task Status</th>
									<th>Peer Review by</th>
								</tr>
								<tr>
									<td>
										<textarea cols="100" rows="5"></textarea><br>
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
