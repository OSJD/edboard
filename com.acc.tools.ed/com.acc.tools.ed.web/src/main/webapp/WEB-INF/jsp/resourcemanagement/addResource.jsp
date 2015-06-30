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
				<table class="ebdtable" id="release">
					 <tr>
						<th style="text-align: right;">Employee Number</th>
						<td>
							<form:input path="employeeNumber" type="text" id="empNumber" class="textbox" />
							
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
						<th><form:hidden path="capability"/>
						<select id="technicalCapability" class="textbox">
								<c:forEach items="${capabilityList}" var="capabilityvar">
									<option value="${capabilityvar}">${capabilityvar}</option>
								</c:forEach>
						</select></th>
						<th>Skill</th>
						<th><form:hidden path="skill" />
							<select id="technicalSkill" class="textbox">
								<c:forEach items="${skillList}" var="skill">
									<option value="${skill}">${skill}</option>
								</c:forEach>
						</select>
						</th>
					</tr>
					<tr>
						<th>Level</th>
						<th> <form:hidden path="level" /> 
						<select id="resourceLevel" class="textbox">
								<c:forEach items="${levelList}" var="level">
									<option value="${level}">${level}</option>
								</c:forEach>
						</select></th>
						<th style="text-align: right;">Previous Location</th>
						<td><form:input path="previousLocation" id="preLocation" type="text" name="preLocation" class="textbox" /></td>
					</tr>
				</table>
			
				<div id = "sucess_msg_div" style="border:1px purple solid;display:none">
		
					<h3>Resource Already Exists!!</h3><br><br>
					<input align="center" type="button" value ="Ok" onClick ="confirmSucess()"/>
				</div>
			</div>
	 </fieldset>
		</form:form> 
	</div>
	 
	
	
</div>


 </body>
</html> 
 
