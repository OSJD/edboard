<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<div id="editproject-popup" title="Edit Project">

		<p class="validateTips">All form fields are required.</p>
		<form:form commandName="editProjectForm" action="addProject.do">
			<fieldset>
				<legend>Edit Project</legend>
				<div>
					<table class="ebdtable" style="width: 10px;">
						<tr id="programRow">
							<th style="text-align: right; height: 25px;">Program</th>
							<td id="programTD" colspan="3"><form:select path="existingProgramEdit"   multiple="false">
									<form:option value="0" label="--- Select ---" />
    								<form:option value="-1" label="Create New Program" />
								</form:select>
								<div id="newProgram" style="display: none;">
									<form:input type="text" path="newProgramNameEdit" class="textbox" />
									<form:hidden path="newProgramIdEdit"/>								
								</div>								
								<a href="#" id="deleteProgram">delete</a>
							</td>
						</tr>
						<tr>
							<th style="text-align: right; height: 25px;">Project Name</th>
							<td>
								<form:input type="text" path="projectNameEdit" class="textbox" />
								<form:hidden path="projectIdEdit" />
							</td>
							<th style="text-align: right;">Project Lead</th>
							<td>
								<form:select path="projectLeadEdit" class="textbox" multiple="false" cssStyle="width : 195px;">
									<form:option value="0" label="--- Select ---" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><form:input type="date" path="startDateEdit" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td><form:input type="date" path="endDateEdit" class="textbox" /></td>
						</tr>
						<tr>
							<th style="text-align: right;">Phase</th>
							<td  colspan="3">
								<form:checkboxes items="${phaseList}" path="phasesEdit" itemLabel="label" itemValue="id" cssClass="phases"/>
							</td>
						</tr>
						<tr>
							<th style="text-align: right; width: 50px;">Description</th>
							<td colspan="3"><form:textarea style="overflow: auto; resize: none" rows="6" path="projectDescriptionEdit" 
									cols="82" class="textarea" /></td>
						</tr>
						<tr style="height: 93px;">
							<th style="text-align: right;">Resources</th>
							
							<td colspan="1">
								 <a>Available Resources</a>  <br /> <br /> 
								<form:select path="stringResourcesEdit" multiple="true">
								</form:select>
							</td>
							<td colspan="1">
								
								 <a href="JavaScript:void(0);" id="btn-add" class="button">Add >></a>  <br />
								 <a href="JavaScript:void(0);" id="btn-remove" class="button"> << Remove</a>
							</td>
							<td colspan="1">
								<a>Selected Resources</a>  <br /><br /> 
								<!-- <select id="selectedResources" multiple="true" style="width : 195px;">
								</select> -->
								<form:select path="selectedResourcesEdit" multiple="true" cssStyle="width : 195px;">
								</form:select>
							</td>
						</tr>
					</table>
				</div>				
			</fieldset>
		</form:form>
	</div>