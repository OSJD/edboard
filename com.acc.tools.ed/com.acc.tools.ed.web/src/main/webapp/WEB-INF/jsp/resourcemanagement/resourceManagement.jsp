<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
 <html> 
 <head>
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
			<script src="<%=request.getContextPath()%>/script/resourcemanagement-actions.js"></script>
 </head>
 <body>
<form id="resourceFileUploadForm" method="post">
	<!-- File input -->
	<table class="ebdtableheader" style="width: 850px;">
		<tr>
			<th>Resource Name</th>
			<th><select id="existingEmpId">
					<option value="" label="Select Resource" />
					<c:forEach items="${empList}" var="emp">
						<option value="${emp.employeeSAPId}">${emp.employeeName}</option>
					</c:forEach>
			</select></th>
			<th><a href="#" class="button" id="addResource" style="width: 100px;">Add Resource</a></th>
			<th><a href="#" class="button" id="updateResource" style="width: 100px;">Edit Resource</a></th>
			<th><a href="#" class="button" id="deleteResource" style="width: 100px;">Delete Resource</a></th>
			<th style="width: 150px;">Upload Bulk Resource</th>
			<th><input name="resourceFileUpload" type="file" class="button" style="width: 500px;" /></th>
			<th><input class="button" type="button" alt="Upload" value="Upload" id="resourceFileUpload" /></th>
		</tr>
	</table>
</form>
<div id="main_emp_container">
	<div id="emp_details" style="display:block">
		<table id="ebdEmpTable" class="ebdtable">
			<thead>
				<tr>
				<th>Name</th>
				<th>Enterprise Id</th>
				<th>SAP ID</th>
				<th>Level</th>
				<th>Role</th>
				<th>Project</th>
				<th>Contact Number</th>
				<th>DOJ</th>
				</tr>
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
					
</div> 
	<jsp:include page="/WEB-INF/jsp/resourcemanagement/addResource.jsp" flush="true"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/resourcemanagement/updateResource.jsp" flush="true"></jsp:include>
</div>
<div id="deleteResource-confirm"
		title="Empty the resource recycle bin?">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<div id="deletedResource"></div>
</div>	

 </body>
</html> 
 
 
