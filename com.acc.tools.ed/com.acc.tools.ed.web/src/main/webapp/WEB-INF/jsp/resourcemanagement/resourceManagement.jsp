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
			<th><a href="#" class="button" id="addResource" style="width: 100px;">Add Resource</a></th>
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
					
</div> 
	<jsp:include page="/WEB-INF/jsp/resourcemanagement/addResource.jsp" flush="true"></jsp:include>
</div>
	

 </body>
</html> 
 
 
