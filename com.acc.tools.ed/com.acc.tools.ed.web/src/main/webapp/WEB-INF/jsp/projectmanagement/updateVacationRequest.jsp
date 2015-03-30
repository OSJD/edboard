<div id="updateVacationRequestPopup" title="Vacation Request Form">
				<form id="updateVacationRequestForm">
					<table class="ebdtable" style="width: 100%; margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Vacation Request</th>
						</tr>
						<tr>
							<th style="width: 75px;">Vacation Type</th>
							<td>
								<input type="hidden" name="vacationId" id="updatevacationId">
								<div id="newVacationTypeDiv"></div>
							</td>
						</tr>
						<tr>
							<th>Start Date</th>
							<td>
								<div id="vacationStartDateDiv"></div>
							</td>
						</tr>
						<tr>
							<th>End Date</th>
							<td>
								<div id="vacationEndDateDiv"></div>
							</td>
						</tr>
						<tr>
							<th>Backup</th>
							<td>
								<div id="newBackupResourceDiv">
								</div>
							</td>
						</tr>						
						<tr>
							<th colspan="2">Comments</th>
						</tr>
						<tr>
							<td colspan="2">
								<div id="newCommentsDiv" style="height: 80px;overflow: auto;">
								</div>
							</td>
						</tr>
						<tr id="statusTr">
							<th>Status</th>
							<td>
								<select class="text" style="width: 130px;" name="status" id="newStatus">
												<option value="0">--select--</option>
												<option value="Approved">Approved</option>
												<option value="Rejected">Rejected</option>
												<option value="OnHold">OnHold</option>
								</select>
							</td>
						</tr>
						<tr id="supervisorCommentsHdTr">
							<th colspan="2">Supervisor Comments</th>
						</tr>
						<tr id="supervisorCommentsValTr">
							<td colspan="2">
								<textarea rows="5" cols="60" name="approverComments"  id="newSupervisorComments"></textarea>
							</td>
						
						</tr>

					</table>
				</form>
			</div>