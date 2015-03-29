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
						<select name="taskId" id="taskIdSelect" class="textbox" style="width : 150px;">
							<option value="0">--- Select ---</option>
							<option value="-1">Create New Task</option>
						</select>
						<div id="newTask" style="display: none;">
							<input type="text" name="taskName" id="taskName" class="textbox" style="width : 143px;" />
							<input type="hidden" name="componentId" id="componentId" />
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
					<td><div id="editTaskStartDateId"><input type="text" name="taskStartDate" id="taskStartDateId" class="textbox" style="width:75px;" /></div></td>

					<td><div id="editTaskEndDateId"><input type="text" name="taskEndDate" id="taskEndDateId" class="textbox" style="width:75px;" /></div></td>
					<td colspan="3"><textarea name="taskDesc" id="taskDesc" rows="5" cols="90"></textarea>
					
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
											<select name="taskActivity" id = "taskActivitySelect" class = "textbox" style="margin-left:10px; width:140px;">
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
											<option value="0">---Select---</option>
											<option value="1">Completed</option>
											<option value="2">In Progress</option>
											<option value="3">On Hold</option>
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
												<th style="text-align: left;width: 250px;">
													<div style="width: 60%;float: left;">Development Artifacts</div>
													<div style="width: 40%;float: left;">
														<a href="#" id="addArtifacts">Add File</a>
													</div>
												</th>
												<th style="width:365px;">
													Review Comment
												</th>
												<th style="width:365px;">Developer Update</th>
												<th style="25px;">Valid(Y/N)</th>
											</tr>
											<tr>	
	 											<td style="width: 50px;text-align: left;">
		 											File Name 									
		 											<a href="#">Edit</a> 
		 											<a href="#">Delete</a>
	 											</td>
	 											
												<td colspan="3">
													<div style="height: 175px;overflow: auto;">
														<table>
															<tr>
																<td>
																	<div id="viewReviewCommentTxtId" style="height: 75px;overflow: auto;">
																		<textarea cols="60" rows="5" disabled="disabled"></textarea>
																	</div>
																</td>
																<td>
																	<div id="developerCommentsId" style="width:365px;height: 75px;overflow: auto;">
																		<textarea cols="60" rows="5"></textarea>
																	</div>
																</td>
																<td style="width: 20px;"></td>
															</tr>
														</table>
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
<div id="developmentArtifacts-popup" title="Add New Development Artifact">
	<p class="validateTips">All form fields are required.</p>
	<form id="developmentArtifacts">
		<fieldset>
			<legend>Add Development Artifacts</legend>
			<table class="ebdtable" id="developmentArtifacts"
				style="width: 100%;">
				<tr>
					<th style="text-align: right;">File Name</th>
					<td colspan="2"><input type="text" name="fileName" class="textbox" /></td>
				</tr>
				<tr>
					<th colspan="4" style="text-align: left; font-size: 15px;">Code
						Quality Matrix</th>
				</tr>
				<tr>
					<th style="text-align: right; height: 25px;">Blocker</th>
					<td><input type="text" name="blocker" class="textbox" /></td>
					<th style="text-align: right; height: 25px;">CCN</th>
					<td><input type="text" name="ccn" class="textbox" /></td>
				</tr>
				<tr>
					<th style="text-align: right; height: 25px;">Critical</th>
					<td><input type="text" name="critical" class="textbox" /></td>
					<th style="text-align: right; height: 25px;">Total Test Cases</th>
					<td><input type="text" name="testCases" class="textbox" /></td>
				</tr>
				<tr>
					<th style="text-align: right; height: 25px;">Major</th>
					<td><input type="text" name="major" class="textbox" /></td>
					<th style="text-align: right; height: 25px;">Test Coverage</th>
					<td><input type="text" name="testCoverage" class="textbox" /></td>
				</tr>
				<tr>
					<th style="text-align: right; height: 25px;">Minor</th>
					<td><input type="text" name="minor" class="textbox" /></td>
					<td></td>

				</tr>
				<tr>
					<th style="text-align: right; height: 25px;">Info</th>
					<td><input type="text" name="info" class="textbox" /></td>
					<td></td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
