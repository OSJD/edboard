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
	
<div id="addemp-popup" title="Add Employee Details">
	<form:form commandName="addEmpDetailsForm" action="addEmpDetailsForm.do" >
	 <fieldset> 
			<legend>Add Employee Details</legend>
				
			<div>
				<form:hidden id="resourceFlag" path="resourceFlag" name="resourceFlag"/>
				<table class="ebdtable" id="release">
					 <tr>
						<th style="text-align: right;">Employee Number</th>
						<td>
							<form:input path="employeeNumber" type="text" id="empNumber" class="textbox" onBlur="resourceCheck()"/>
							
						</td>

						<th style="text-align: right;">Employee Name</th>
						<td> 
						 <form:input path="employeeName" type="text" id="empName" class="textbox" /> 
						</td>
					</tr> 
					<tr>
						<th style="text-align: right;">Contact Number</th>
						<td><form:input path="contactNumber" id="conNumber" type="text" name="conNumber" class="textbox" /></td>
						<th style="text-align: right;">Email ID</th>
						<td><form:input path="emailId" id="emailID" type="text" name="emailID" class="textbox" /></td>
					</tr>
					<tr>
						<th style="text-align: right;">Enterprise ID</th>
						<td><form:input path="enterpriseId" id="enterpriseId" type="text" name="enterpriseId" class="textbox" /></td>
						<th style="text-align: right;">Employee Role</th>
						<td><form:input path="role" id="role" type="text" name="role" class="textbox" /></td>
					</tr>
					<tr>
						<th>Project Roll On Date (DD/MM/YYYY)</th>
						<td><form:input path="projectStartDate" id ="empStartDate" name="empStartDate" class="textbox" /></td>
						<th style="width: 70px;">Project Roll Off Date (DD/MM/YYY)</th>
							<td><form:input path="projectEndDate" id="empEndDate" name="empEndDate" class="textbox" /></td>
					</tr>
					<tr>
						<th>Capability</th>
						<th>
						<form:select path="capability" id="technicalCapability" onChange="updatePrimarySkills()">
						<option value="" label="Select Capability" />
								<c:forEach items="${capabilityList}" var="capabilityvar">
									<option value="${capabilityvar}">${capabilityvar}</option>
								</c:forEach>
						</form:select></th>
					</tr>
					<tr>
						<th>Primary Skill</th>
						<th>
							<form:select path="skill" id="technicalSkill" onChange="updateSecSkills()">
						</form:select>
						</th>
						<th>Secondary Skill</th>
						<th>
							<form:select path="secSkills" id="secondarySkills" multiple="true">
						</form:select>
						</th>
					</tr>
					<tr>
						<th>Level</th>
						<th>
						<form:select path="level" id="resourceLevel">
						<option value="" label="Select Level" />
								<c:forEach items="${levelList}" var="level">
									<option value="${level}">${level}</option>
								</c:forEach>
						</form:select></th>
						<th style="text-align: right;">Previous Location</th>
						<td><form:input path="previousLocation" id="preLocation" type="text" name="preLocation" class="textbox" /></td>
					</tr>
				</table>
			
				<div id = "sucess_msg_div" style="border:1px purple solid;display:none">
		
					<h3>Employee Details Already Exists!!</h3><br><br>
					<input align="center" type="button" value ="Ok" onClick ="confirmSucess()"/>
				</div>
			</div>
	 </fieldset>
		</form:form> 
	</div>
	 
	
	
</div>


 </body>
</html> 
 
