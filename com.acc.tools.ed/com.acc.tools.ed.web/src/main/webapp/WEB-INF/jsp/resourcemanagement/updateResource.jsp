<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
 <html> 
 <head>
				<script src="<%=request.getContextPath()%>/script/resourcemanagement-actions.js"></script>
 </head>
 <body>

<div id="main_container">
	
<div id="updateemp-popup" title="Update Employee Details">
	<form:form commandName="updateEmpDetailsForm" action="updateEmpDetailsForm.do" >
	 <fieldset> 
			<legend>Update Employee Details</legend>
				
			<div>
				<%-- <form:hidden id="resourceFlag" path="resourceFlag" name="resourceFlag"/> --%>
				<table class="ebdtable" id="release">
					 <tr>
						<th style="text-align: right;">Employee Number</th>
						<td>
							<form:input path="employeeNumber" type="text" id="editEmpNumber" class="textbox"/>
							
						</td>

						<th style="text-align: right;">Employee Name</th>
						<td> 
						 <form:input path="employeeName" type="text" id="editEmpName" class="textbox"/> 
						</td>
					</tr> 
					<tr>
						<th style="text-align: right;">Contact Number</th>
						<td><form:input path="contactNumber" id="editConNumber" type="text" name="editConNumber" class="textbox" /></td>
						<th style="text-align: right;">Email ID</th>
						<td><form:input path="emailId" id="editEmailID" type="text" name="editEmailID" class="textbox" /></td>
					</tr>
					<tr>
						<th style="text-align: right;">Enterprise ID</th>
						<td><form:input path="enterpriseId" id="editEnterpriseId" type="text" name="editEnterpriseId" class="textbox" /></td>
						<th style="text-align: right;">Employee Role</th>
						<td><form:input path="role" id="editRole" type="text" name="editRole" class="textbox" /></td>
					</tr>
					<tr>
						<th>Project Roll On Date (DD/MM/YYYY)</th>
						<td><form:input path="projectStartDate" id ="editEmpStartDate" name="editEmpStartDate" class="textbox" /></td>
						<th style="width: 70px;">Project Roll Off Date (DD/MM/YYY)</th>
							<td><form:input path="projectEndDate" id="editEmpEndDate" name="editEmpEndDate" class="textbox" /></td>
					</tr>
					<tr>
						<th>Capability</th>
						<th>
						<form:hidden path="exisCapability" id="exisCapability"/>
						<form:select path="capability" id="editTechnicalCapability" onChange="updateEditPrimarySkills()">
						<option value="" label="Select Capability" />
								<c:forEach items="${capabilityList}" var="capabilityvar">
									<option value="${capabilityvar}">${capabilityvar}</option>
								</c:forEach>
						</form:select></th>
					</tr>
					<tr>
						<th>Primary Skill</th>
						<th>
							<form:hidden path="exisSkill" id="exisSkill"/>
							<form:select path="skill" id="editTechnicalSkill" onChange="updateEditSecSkills()">
						</form:select>
						</th>
						<th>Secondary Skill</th>
						<th>
							<form:hidden path="exisSecSkills" id="exisSecSkills"/>
							<form:select path="secSkills" id="editSecondarySkills" multiple="true">
						</form:select>
						</th>
					</tr>
					<tr>
						<th>Level</th>
						<th>
						<form:hidden path="exisLevel" id="exisLevel"/>
						<form:select path="level" id="editResourceLevel">
						<option value="" label="Select Level" />
								<c:forEach items="${levelList}" var="level">
									<option value="${level}">${level}</option>
								</c:forEach>
						</form:select></th>
						<th style="text-align: right;">Previous Location</th>
						<td><form:input path="previousLocation" id="editPreLocation" type="text" name="editPreLocation" class="textbox" /></td>
					</tr>
				</table>
			
				<div id = "edit_sucess_msg_div" style="border:1px purple solid;display:none">
		
					<h3>Employee Details Already Exists!!</h3><br><br>
					<input align="center" type="button" value ="Ok" onClick ="confirmEditSucess()"/>
				</div>
			</div>
	 </fieldset>
		</form:form> 
	</div>
	 
	
	
</div>


 </body>
</html> 
 
