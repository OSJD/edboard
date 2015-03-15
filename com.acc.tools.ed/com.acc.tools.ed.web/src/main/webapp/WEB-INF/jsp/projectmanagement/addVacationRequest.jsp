<div id="vacationRequestPopup" title="Vacation Request Form">
				<form id="vacationRequestForm">
					<table class="ebdtable" style="width: 100%; margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Vacation Request</th>
						</tr>
						<tr>
							<th style="width: 75px;">Vacation Type</th>
							<td><select class="text" style="width: 130px;"
								name="vacationType" id="vacationType">
									<option value="0">--select--</option>
									<option value="-1">Vacation</option>
									<option value="-2">Sick Leave</option>
									<option value="-3">Optional Holiday</option>
									<c:if test="${edbUser.role =='Admin'}">
										<option value="-4">Public Holiday</option>
									</c:if>
							</select></td>
						</tr>
						<c:if test="${edbUser.role =='Admin'}">
							<tr>
								<th>Location</th>
								<td><input type="radio" name="location"
									id="offShoreLocation" value="1" disabled="disabled">
									Off Shore <input type="radio" name="location"
									id="onShoreLocation" value="2" disabled="disabled"> On
									Shore</td>
							</tr>
						</c:if>
						<tr>
							<th>Start Date</th>
							<td><input type="text" name="vacationStartDate"
								id="vacationStartDate" class="textbox" /></td>
						</tr>
						<tr>
							<th>End Date</th>
							<td><input type="text" name="vacationEndDate" id="vacationEndDate"
								class="textbox" /><!-- <a href="#" id="buttonn1">Button2</a> --></td>
						</tr>
						<tr>
							<th>Backup</th>
							<td>
								<select class="text" style="width: 130px;"
									name="backupResource" id="backupResource">
										<option value="0">--select--</option>
										<option value="-1">Dimple</option>
										<option value="-2">Vidhya</option>
										<option value="-3">Vinoth</option>
								</select>
							</td>
						</tr>						
						<tr>
							<th colspan="2">Comments</th>
						</tr>
						<tr>
							<td colspan="2"><textarea name="comments" rows="5" cols="60"
									class="text"></textarea></td>
						</tr>

					</table>
				</form>
			</div>