<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<p class="validateTips">All form fields are required.</p>
	<form:form commandName="addRrdDetailsForm" action="">
			<div>
				<table class="ebdtable" id="rrdDetails">
					<tr>
						<th style="text-align: right; height: 25px;">Employee Number</th>
						<td><input type="text" name="empNumber" class="textbox" /></td>
						<th style="text-align: right; height: 25px;">RRD Number</th>
						<td><input type="text" name="rrdNumber" class="textbox" /></td>
					</tr>

					<tr>
						<th>Project</th>
						<td><select id="project" class="textbox">
								<option value="0">Select Project</option>
								<c:forEach items="${projectList}" var="project">
									<option value="${project.id}">${project.label}</option>
								</c:forEach>
						</select></td>
						<th>Level</th>
						<td><select id="level" class="textbox">
								<option value="0">Select Level</option>
								<c:forEach items="${levelList}" var="levelvar">
									<option value="${levelvar}">${levelvar}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>Capability</th>
						<td><select id="capability" class="textbox">
								<option value="0">Select Capability</option>
								<c:forEach items="${capabilityList}" var="capabilityvar">
									<option value="${capabilityvar}">${capabilityvar}</option>
								</c:forEach>
						</select></td>
						<th>Skill</th>
						<td><select id="skill" class="textbox">
								<option value="0">Select Skill</option>
								<c:forEach items="${skillList}" var="skillvar">
									<option value="${skillvar}">${skillvar}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>Is hardlocked</th>
						<td><input type="radio" name="Yes" value="Yes"> <label
							for="Yes">Yes</label> <input type="radio" name="No" value="No">
							<label for="No">No</label></td>
						<th>Is Active</th>
						<td><input type="radio" name="Yes" value="Yes"> <label
							for="Yes">Yes</label> <input type="radio" name="No" value="No">
							<label for="No">No</label></td>
					</tr>
					<tr>
						<th>Start Date:</th>
						<td><input name="startDate" /></td>
						<th>End Date:</th>
						<td><input name="endDate" /></td>
					</tr>
					<tr>
						<th colspan="4" style="background-image: none;">
							<div style="width:100px;float: left;margin-left: 170px;"><a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="submit" >SUBMIT</a></div>
							<div style="width:100px;float: left;"><a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="reset" >RESET</a></div>
						</th>
					</tr>
				</table>
			</div>
	</form:form>

