<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form id="resourceFileUploadForm" method="post" action="./upload.do"
	enctype="multipart/form-data">
	<!-- File input -->
	<table class="ebdtableheader" style="width: 850px;">
		<tr>
			<th><a href="#" class="button" id="addResource" style="width: 100px;">Add Resource</a></th>
			<th style="width: 150px;">Bulk Resource Upload</th>
			<th><input name="resourceFileUpload" id="resourceFileUpload" type="file" class="button" style="width: 500px;" /></th>
			<th><input class="button" type="submit" alt="Upload" value="Upload" class="button" /></th>
		</tr>
	</table>
</form>
<div style="margin-top: 20px;">
	<form id="resourceDataForm" method="post">
		<table id="ebdEmpTable" class="ebdtable">
			<thead>
				<th>Name</th>
				<th>Enterprise Id</th>
				<th>SAP ID</th>
				<th>Level</th>
				<th>Role</th>
				<th>Project</th>
				<th>Contact Number</th>
				<th>DOJ</th>
			</thead>
			<tbody>
				<c:forEach items="${empList}" var="emp">
					<tr>
						<td>${emp.employeeName}</td>
						<td>${emp.employeeEnterpId}</td>
						<td>${emp.employeeSAPId}</td>
						<td>${emp.employeeLevel}</td>
						<td>${emp.employeeRole}</td>
						<td>${emp.employeeProjectName}</td>
						<td>${emp.employeePrimaryContactNo}</td>
						<td>${emp.accentureDOJ}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<script>  $(function(){
							$("#ebdEmpTable").dataTable( {
						        columnDefs: [ {
						            targets: [ 0 ],
						            orderData: [ 0, 1 ]
						        }, {
						            targets: [ 1 ],
						            orderData: [ 1, 0 ]
						        }, {
						            targets: [ 4 ],
						            orderData: [ 4, 0 ]
						        } ]
						    } ); 
						});  
			</script>
	</form>
</div>
<div id="addemp-popup" title="Add Employee Details">
	<p class="validateTips">All form fields are required.</p>
	<form:form commandName="addEmpDetailsForm" action="addEmpDetailsForm.do">	
		<fieldset>
			<legend>Add Employee Details</legend>
			<div>
				<table class="ebdtable" id="release">
					<tr>
						<th style="text-align: right;">Employee Number</th>
						<td><input type="text" name="empNumber" class="textbox" /></td>

						<th style="text-align: right;">Employee Name</th>
						<td><input type="text" name="empName" class="textbox" /></td>
					</tr>
					<tr>
						<th style="text-align: right;">Contact Number</th>
						<td><input type="text" name="conNumber" class="textbox" /></td>

						<th style="text-align: right;">Email ID</th>
						<td><input type="text" name="emailID" class="textbox" /></td>
					</tr>
					<tr>
						<th>Capability</th>
						<th><select id="capability" class="textbox">
								<option value="0">Select Capability</option>
								<c:forEach items="${capabilityList}" var="capabilityvar">
									<option value="${capabilityvar}">${capabilityvar}</option>
								</c:forEach>
						</select></th>
						<th>Skill</th>
						<th><select id="skill" class="textbox">
								<option value="0">Select Skill</option>
								<c:forEach items="${skillList}" var="skillvar">
									<option value="${skillvar}">${skillvar}</option>
								</c:forEach>
						</select></th>
					</tr>
					<tr>
						<th>Level</th>
						<th><select id="level" class="textbox">
								<option value="0">Select Level</option>
								<c:forEach items="${levelList}" var="levelvar">
									<option value="${levelvar}">${levelvar}</option>
								</c:forEach>
						</select></th>
						<th style="text-align: right;">Previous Location</th>
						<td><input type="text" name="preLocation" class="textbox" /></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form:form>
</div>
 