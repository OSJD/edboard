	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>	
	<div id="addproject-popup" title="Add New Project">
		<p class="validateTips">All form fields are required.</p>
		<form:form commandName="addProjectForm" action="addProject.do">
			<fieldset>
				<legend>Add Project</legend>
				<div>
					<table class="ebdtable">
						<tr id="programRow">
							<th style="text-align: right; height: 25px;">Program</th>
							<td id="programTD" colspan="3"><form:select path="existingProgram"  style="width:135px;" multiple="false">
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
								<form:hidden path="projectId" />
							</td>
							<th style="text-align: right;">Project Lead</th>
							<td>
								<form:select path="projectLead" class="textbox" multiple="false">
									<form:option value="0" label="--- Select ---" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><form:input type="date" path="startDate"
								id="startDate" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td><form:input type="date" path="endDate" id="endDate"
								class="textbox" /></td>
						</tr>
						<tr>
							<th style="text-align: right;">Phase</th>
							<td  colspan="3">
								<form:checkboxes items="${phaseList}" path="phases" itemLabel="label" itemValue="id" />
							</td>
						</tr>
						<tr>
							<th style="text-align: right; width: 50px;">Description</th>
							<td colspan="3"><form:textarea style="overflow: auto; resize: none" rows="6" path="projectDescription"
									cols="82" class="textarea" /></td>
						</tr>
						<tr style="height: 93px;">
							<th style="text-align: right;">Resources</th>
							<td colspan="3">
								<form:select path="stringResources" multiple="true">
								</form:select>
							</td>
						</tr>
					</table>
				</div>				
			</fieldset>
		</form:form>
	</div>