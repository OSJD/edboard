	<div id="addproject-popup" title="Add New Project">
		<p class="validateTips">All form fields are required.</p>
		<form:form commandName="addProjectForm" action="addProject.do">
			<fieldset>
				<legend>Add Project</legend>
				<div>
					<table class="ebdtable" style="width: 100%;">
						<tr id="programRow">
							<th style="text-align: right; height: 25px;">Program</th>
							<td id="programTD" colspan="3"><form:select path="existingProgram"   multiple="false">
									<form:option value="0" label="--- Select ---" />
    								<form:option value="-1" label="Create New Program" />
								</form:select>
								<div id="newProgram" style="display: none;">
									<form:input type="text" path="newProgramName" class="textbox" />
									<form:hidden path="newProgramId"/>								
								</div>								
								<a href="#" id="deleteProgram">delete</a>
							</td>
						</tr>
						<tr>
							<th style="text-align: right; height: 25px;">Project Name</th>
							<td>
								<form:input type="text" path="projectName" class="textbox" />
								<form:hidden path="projectId" /><br />
								<span id="display" style="color: red;"/>
							</td>
							<th style="text-align: right;">Project Lead</th>
							<td>
								<form:select path="projectLead" class="textbox" multiple="false" cssStyle="width : 195px;">
									<form:option value="0" label="--- Select ---" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><form:input type="date" path="startDate"
								id="projectStartDate" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td>
								<form:input type="date" path="endDate" id="projectEndDate" class="textbox" />
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Phase</th>
							<td  colspan="3">
								<form:checkboxes items="${phaseList}" path="phases" itemLabel="label" itemValue="id" id="listphase" />
							</td>
						</tr>
						<tr>
							<th style="text-align: right; width: 50px;">Description</th>
							<td colspan="3"><form:textarea style="overflow: auto; resize: none" rows="6" path="projectDescription"
									cols="82" class="textarea" /></td>
						</tr>
						<tr style="height: 93px;">
							<th style="text-align: right;">Resources</th>
							
							<td style="width: 40%;">
								 <span>Available Resources</span> 
								<form:select path="stringResources" multiple="true" cssStyle="width:240px;">
								</form:select>
							</td>
							<td style="width: 10%;">
								
								 <a href="JavaScript:void(0);" id="btn-add" class="button">Add >></a>  <br />
								 <a href="JavaScript:void(0);" id="btn-remove" class="button"> << Remove</a>
							</td>
							<td>
								<span>Selected Resources</span> 
								<form:select path="selectedResources" multiple="true" cssStyle="width:240px;" id="selectedResources1">
								</form:select>
							</td>
						</tr>
					</table>
				</div>				
			</fieldset>
		</form:form>
	</div>
	