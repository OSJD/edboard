<div id="editTaskPanel" title="Add Tasks">
		<form name="editTaskForm" id="editTaskForm">
			<fieldset>
				<legend>Edit Tasks</legend>
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
									<td><div id="editTaskProjectName"></div></td>
									<td><div id="editTaskReleaseName"></div></td>
									<td><div id="editTaskComponentName"></div></td>
									<td style="height: 50px;">
										<div style="overflow: auto;" id="editTaskAssignedWork"></div>
									</td>
									<td><div id="editTaskCompStartDate"></div></td>
									<td><div id="editTaskCompEndDate"></div></td>
								</tr>
							</table>
						</td>
					</tr>
				<tr>
					<th style="width: 130px;">Task Name</th>
					<th style="width: 40px">Task Type</th>
					<th style="width: 100px;">Task Start Date</th>
					<th style="width: 100px;">Task End Date</th>
					<th style="width: 100px;">Task Description</th>

				</tr>
				<tr>
					<td id="taskNamePosition">
						<div id="editTaskName"></div>
						<input type="hidden" name="taskId" id="editTaskId" />
						<input type="hidden" name="taskComponentId" id="editTaskComponentId" />
					</td>
					<td style="width: 40px;">
						<div id="editTaskType"></div>
					</td>
					<td><div id="editTaskStartDateId"></div></td>

					<td><div id="editTaskEndDateId"></div></td>
					<td colspan="3">
					    <div id="editTaskDesc"></div>
					</td>
				</tr>

					<tr>
						<td colspan="8"
							style="text-align: center; background-image: none; background-color: white;">
							<table style="width: 100%;">

								<tr>
									<th style="text-align: left;width: 200px;">
										<div style="width:285px;float: left;">
											Activity Date :
											<select name="editTaskActivity" id = "editTaskActivitySelect" class = "textbox" style="margin-left:10px;width:140px;">
												  <option value="-1">Enter new comment</option>
					   						</select>
				   						</div>
									</th>
									<th>Task Hours</th>
									<th>Task Status</th>
									<th>Peer Review by</th>
								</tr>
								<tr>
									<td>
										<textarea rows="5" cols="100" name="taskDvlprComments" id="editTaskDvlprComments"></textarea>
									</td>
									<td><input type="text" name="taskHrs" id="editTaskHrs" class="textbox" style="width:35px;" /></td>
									<td>
										<select name="taskStatus" id="editTaskStatus" style="width:135px;">
											<option value="0">---Select---</option>
											<option value="1">Build Completed</option>
											<option value="2">Build In Progress</option>
											<option value="3">Build On Hold</option>
											<option value="4">Review Completed</option>
											<option value="5">Review In Progress</option>
											<option value="6">Review On Hold</option>
											<option value="7">Rework Completed</option>
											<option value="8">Rework In Progress</option>
											<option value="9">Rework On Hold</option>
											<option value="10">Task Closed</option>												
										</select>
									</td>
									<td>
										<select name="taskReviewUser" id="editTaskReviewUser" style="width:200px;">
										</select>
									</td>
								</tr>	
								<tr>
									<td colspan="4" style="text-align: center; background-image: none; background-color: white;">
										<table style="width: 100%;">
											<tr>
												<th style="text-align: left;width: 250px;">
													Development Artifacts
												</th>
												<th style="width:365px;">
													<div style="width: 70%;float: left;">Review Comment</div>
													<div style="width: 30%;float: left;">
														<a href="#" id="editReviewRow">Add New Comments</a>
													</div>
												</th>
												<th style="width:365px;">Developer Update</th>
												<th style="25px;">Valid(Y/N)</th>
											</tr>
											<tr>	
	 											<td style="width: 50px;text-align: left;" >
		 											File Name 									
		 											<!-- <a href="#">Edit</a> 
		 											<a href="#">Delete</a> -->
	 											</td>
	 											
												<td colspan="3">
													<div id="rcMainDiv" style="height: 175px;overflow: auto;">
														<table id="rcTable"><tbody></tbody></table>
													</div>
												</td>
											</tr>	 										
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="4" style="text-align: center; background-image: none; background-color: white;">
										<table style="width: 100%;">
											<tr>
												<th colspan="8"  style=" text-align:left;font-size: 15px;">Code Quality Matrix</th>
											</tr>
											<tr>
												<th style="width: 10px;">Blocker</th>
												<th style="width: 10px;">Critical</th>
												<th style="width: 10px;">Major</th>
												<th style="width: 10px;">Minor</th>
												<th style="width: 10px;">Info</th>
												<th style="width: 10px;">CCN</th>
												<th style="width: 10px;">CCN</th>
												<th style="width: 10px;">Coverage %</th>
											</tr>										
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
</div>
