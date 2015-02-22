<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>

<table class="ebdtable" style="width: 100%;height: 733px;">
	<tr>
		<td style="width: 25%;background-image: none;background-color: white;">
			<div style="float: left;">
				<form id="vacationForm">
					<table style="width: 100%;margin: 0px;">
						<tr>
							<th colspan="2" style="text-align: center;">Vacation Request</th>
						</tr>
						<tr>
							<th style="width:75px;">Vacation Type</th>
							<td>
								<select class="text" style="width: 130px;" name="vacationType" id="vacationType">
									<option value="0">--select--</option>
									<option value="-1">Vacation</option>
									<option value="-2">Sick Leave</option>
									<option value="-3">Optional Holiday</option>
									<option value="-4">Public Holiday</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>Location</th>
							<td>
								<input type="radio" name="location" id="offShoreLocation" value="1" disabled="disabled"> Off Shore
								<input type="radio" name="location" id="onShoreLocation" value="2" disabled="disabled"> On Shore
							</td>
						</tr>
						<tr>
							<th>Start Date</th>
							<td>
								<input type="text" name="startDate" id="vacationStartDate" class="textbox" />
							</td>
						</tr>
						<tr>
							<th>End Date</th>
							<td>
								<input type="text" name="endDate" id="vacationEndDate" class="textbox" />						
							</td>
						</tr>
						<tr>					
							<th colspan="2">Comments</th>
						</tr>
						<tr>
							<td colspan="2"><textarea name="comments" rows="5" cols="60" class="text"></textarea></td>
						</tr>
						<tr>					
							<th colspan="2"><a id="vacationRequestSubmit" href="#">Submit</a></th>
						</tr>
						
					</table>
				</form>
			</div>	
			<div style="height: 720px;">Display applied Vaction Detail status</div>		
		</td>
		<th style="background-image: none;background-color: white;"></th>
	</tr>
</table>