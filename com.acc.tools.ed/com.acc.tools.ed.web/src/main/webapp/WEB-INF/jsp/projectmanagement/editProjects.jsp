<div id="editproject-popup" title="Edit Project">

		<p class="validateTips">All form fields are required.</p>
		<form:form commandName="editProjectForm" action="editProject.do">
			<fieldset>
				<legend>Edit Project</legend>
				<div>
					<table class="ebdtable" style="width: 10px;">
						<tr id="programRow">
							<th style="text-align: right; height: 25px;">Program</th>
							<td id="programTD" colspan="3"><form:select path="existingProgram" id="existingProgramEdit"   multiple="false">
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
								<form:input type="text" path="projectName" id="projectNameEdit" class="textbox" />
								<form:hidden path="projectId" id="projectIdEdit" />
							</td>
							<th style="text-align: right;">Project Lead</th>
							<td>
								<form:select path="projectLead" id="projectLeadEdit" class="textbox" multiple="false" cssStyle="width : 195px;">
									<form:option value="0" label="--- Select ---" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><form:input type="date" path="startDate" id="startDateEdit" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td><form:input type="date" path="endDate" id="endDateEdit" class="textbox" /></td>
						</tr>
						<tr>
							<th style="text-align: right;">Phase</th>
							<td  colspan="3">
								<form:checkboxes items="${phaseList}" path="phases" itemLabel="label" itemValue="id" id="phasesEdit"/>
							</td>
						</tr>
						<tr>
							<th style="text-align: right; width: 50px;">Description</th>
							<td colspan="3"><form:textarea style="overflow: auto; resize: none" rows="6" path="projectDescription" id="projectDescriptionEdit" 
									cols="82" class="textarea" /></td>
						</tr>
						<tr style="height: 93px;">
							<th style="text-align: right;">Resources</th>
							
							<td>
								 <p style="color: red;">Available Resources</p>
								<form:select path="stringResources" id="stringResourcesEdit" multiple="true">
								</form:select>
							</td>
							<td>
								 <a href="#" id="btn-addEdit" class="button">Add >></a>  <br />
								 <a href="#" id="btn-removeEdit" class="button"> << Remove</a>
							</td>
							<td>
								<p style="color: red;">Selected Resources</p> 
								<form:select path="selectedResources" id="selectedResourcesEdit" multiple="true" cssStyle="width : 195px;">
								</form:select>
							</td>
						</tr>
					</table>
				</div>				
			</fieldset>
		</form:form>
	</div>