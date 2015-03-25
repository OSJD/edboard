<div id="addTaskPanel" title="Add Tasks">
		<form name="addTaskForm" id="addTaskForm">
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
					<th style="width: 130px;">Task Name</th>
					<th style="width: 40px">Task Type</th>
					<th style="width: 100px;">Task Start Date</th>
					<th style="width: 100px;">Task End Date</th>
					<th style="width: 100px;">Task Description</th>

				</tr>
				<tr>
					<td id="taskNamePosition">
						<select name="taskName" id="taskNameSelect" class="textbox" style="width : 150px;">
							<option value="0">--- Select ---</option>
							<option value="-1">Create New Task</option>
						</select>
						<div id="newTask" style="display: none;">
							<input type="text" name="taskName" id="taskName" class="textbox" style="width : 143px;" />
							<input type="hidden" name="componentId" id="componentId" />
							<input type="hidden" name="taskId" id="taskId" />
						</div>
					</td>
					<td style="width: 40px;">
						<select name="taskType" id="taskType" class="textbox">
							<option value="0">---Select---</option>
							<option value="1">Assigned</option>
							<option value="2">Adhoc</option>
							<option value="3">Value Added</option>
						</select>
					</td>
					<td><input type="text" name="taskStartDate" id="taskStartDateId" class="textbox" style="width:75px;" /></td>

					<td><input type="text" name="taskEndDate" id="taskEndDateId" class="textbox" style="width:75px;" /></td>
					<td colspan="3"><textarea name="taskDesc" id="taskDesc" rows="5" cols="90"></textarea>
					
					</td>

				</tr>

					<tr>
						<td colspan="8"
							style="text-align: center; background-image: none; background-color: white;">
							<table style="width: 100%;">

								<tr>
									<th style="text-align: left;width: 200px;">
										<div style="width:185px;float: left;">
											Activites	
											<select name="taskActivity" id = "taskActivitySelect" class = "textbox" style="width:140px;">
												  <option value="-1">Enter new comment</option>
					   						</select>
				   						</div>
				   						<div id="taskActivityDateId" style="margin-left:15px; width: 10px;float: left;"></div>
									</th>
									<th>Task Hours</th>
									<th>Task Status</th>
									<th>Peer Review by</th>
								</tr>
								<tr>
									<td>
										<textarea rows="5" cols="100" name="taskComments" id="taskDvlprComments"></textarea>
									</td>
									<td><input type="text" name="taskHrs" id="taskHrs" class="textbox" style="width:35px;" /></td>
									<td>
										<select name="taskStatus" id="taskStatus" style="width:135px;">
											<option value="-1">---Select---</option>
											<option value="completed">Completed</option>
											<option value="inProgress">In Progress</option>
											<option value="onHold">ComplOn Holdeted</option>
										</select>
									</td>
									<td>
										<select name="taskReviewUser" id="taskReviewUser" style="width:200px;">
										</select>
									</td>
								</tr>	
								<tr>
									<td colspan="4" style="text-align: center; background-image: none; background-color: white;">
										<table style="width: 100%;">
											<tr>
												<th style="width: 30%;text-align: left;">Development Artifacts<a href ="#">Add File</a> </th>
												<th>Review Comment</th>
												<th>Developer Update</th>
											</tr>
											<tr>	
	 											<td style="width: 50px;text-align: left;">
		 											File Name 									
		 											<a href="#">Edit</a> 
		 											<a href="#">Delete</a>
	 											</td>
	 											
												<td>
													<div style="width: 325px;height: 60px;overflow: auto;">
														Review Comment 1
													</div>
												</td>
												<td>
													<textarea cols="62" rows="5"></textarea>
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
	<div id="taskHistoryPanel" title="Tasks History">
	
	</div>
