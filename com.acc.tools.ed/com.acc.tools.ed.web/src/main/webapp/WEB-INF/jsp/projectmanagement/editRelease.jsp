	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
		<p class="validateTips">All form fields are required.</p>
		<form:form commandName="editReleaseForm" action="editRelease.do">
			<fieldset>
				<legend>Edit Release</legend>
				<table class="ebdtable" id="release">
					<tr>
						<th style="text-align: right; height: 25px;">Project Name</th>
						<td>${projectForm.projectName}</div></td>
						<th style="text-align: right;">Proj. Start Date</th>
						<td>${projectForm.startDate}</div></td>
						<th style="text-align: right;">Proj. End Date</th>
						<td>${projectForm.endDate}</div></td>
					</tr>
					<tr>
						<th style="text-align: right;">Release Name</th>
						<td><form:input path="releaseName" cssClass="textbox" /></td>
						<th style="text-align: right;">Rel. Start Date</th>
						<td style="width: 200px;"><form:input path="releaseStartDate" id="releaseStartDate" cssClass="textbox" /></td>
						<th style="text-align: right;">Rel. End Date</th>
						<td><form:input path="releaseEndDate" id="releaseEndDate" cssClass="textbox" /></td>
					</tr>						
					<tr>
						<th style="text-align: right;">Release Artifacts</th>
						<td  colspan="5">
							<form:textarea path="releaseArtifacts" cssStyle="overflow: auto; resize: none;width: 800px;" cssClass="textarea"/>  
						</td>
					</tr>	
					<tr>
					  	<td colspan="6">
						  	<a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="getReleasePlan" role="button" style="width: 120px;margin-left: 400px;">
								<span class="ui-button-text">Get Release Plan</span>
				 			</a>
			 			</td>
					</tr>	
				</table>
			</fieldset>
		</form:form>
