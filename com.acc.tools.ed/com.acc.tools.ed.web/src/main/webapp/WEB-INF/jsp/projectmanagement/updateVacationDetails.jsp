<div id="vacationUpdatePopup" title="Vacation Update Form">
				<form id="vacationUpdateForm">
					<table class="ebdtable" style="width: 100%; margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Vacation Request</th>
						</tr>
						<tr>
							<th style="width: 75px;">Vacation Type</th>
							<td>
									<select class="text" style="width: 130px;" name="vacationType" id="updateVacationType">
											<option value="0">--select--</option>
											<option value="-1">Vacation</option>
											<option value="-2">Sick Leave</option>
											<option value="-3">Optional Holiday</option>
											<c:if test="${edbUser.role =='Admin'}">
												<option value="-4">Public Holiday</option>
											</c:if>
									</select>
									<input type="hidden" id="updateVacationId" name="vacationId">
							</td>
						</tr>
						<c:if test="${edbUser.role =='Admin'}">
							<tr id="locationDiv" style="display: none">
								<th>Location</th>
								<td><input type="radio" name="location"
									id="offShoreLocation" value="1" checked="checked">
									Off Shore <input type="radio" name="location"
									id="onShoreLocation" value="2"> On
									Shore</td>
							</tr>
						</c:if>
						<tr>
							<th>Start Date</th>
							<td>
								<input type="text" name="startDate" id="vacationUpdateStartDate" class="textbox" />
							</td>
						</tr>
						<tr>
							<th>End Date</th>
							<td>
								<input type="text" name="endDate" id="vacationUpdateEndDate" class="textbox" />
							</td>
						</tr>
						<tr id="backUpRow">
							<th>Backup</th>
							<td>
								<select class="textbox" style="width: 230px;"
										name="backUpResource" id="updateBackupResource">
								</select>
							</td>
						</tr>						
						<tr>
							<th colspan="2">Comments</th>
						</tr>
						<tr>
							<td colspan="2">
								<textarea name="comments" id="updateComments" rows="5" cols="60" class="text"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
