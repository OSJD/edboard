	<div id="addrelease-popup" title="Add New Release">
		<p class="validateTips">All form fields are required.</p>
		<form id="addReleaseForm">
			<fieldset>
				<legend>Add Release</legend>
				<table class="ebdtable" id="release" style="width: 100%;">
					<tr>
						<th style="text-align: right; height: 25px;">Project Name</th>
						<td><div id="projName"></div></td>
						<th style="text-align: right;">Proj. Start Date</th>
						<td><div id="projStartDate"></div></td>
						<th style="text-align: right;">Proj. End Date</th>
						<td><div id="projEndDate"></div></td>
					</tr>
					<tr>
						<th style="text-align: right;">Release Name</th>
						<td><input type="text" name="releaseName" class="textbox" /></td>
						<th style="text-align: right;">Rel. Start Date</th>
						<td style="width: 200px;"><input type="text" id="releaseStartDate" name="releaseStartDate" class="textbox" /></td>
						<th style="text-align: right;">Rel. End Date</th>
						<td><input type="text" id="releaseEndDate" name="releaseEndDate" class="textbox" /></td>
					</tr>						
					<tr>
						<th style="text-align: right;">Release Artifacts</th>
						<td  colspan="5">
							<textarea style="overflow: auto; resize: none;width: 800px;" rows="6" name="releaseArtifacts"	 class="textarea" ></textarea>  
						</td>
					</tr>	
					<tr>
					  	<td colspan="6">
						  	<a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="createReleasePlan" role="button" style="width: 120px;margin-left: 400px;">
								<span class="ui-button-text">Create Release Plan</span>
				 			</a>
			 			</td>
					</tr>
					<tr id="addReleasePlan"><td colspan="6"><span style="color: red;">Please Click on "Create Release Plan" button to add release plan.</span></td></tr>	
				</table>
			</fieldset>
		</form>
	</div>