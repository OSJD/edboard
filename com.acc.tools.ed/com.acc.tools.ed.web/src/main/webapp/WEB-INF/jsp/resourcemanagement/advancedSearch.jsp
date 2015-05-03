<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div style="margin-top: 20px;">
		<form id="resourceDataForm" method="post">
		  	<table id = "ebdRrdTable" class = "ebdtable">
				<thead>      
					<th>RRD Number</th>
					<th>Employee Number</th>
					<th>Employee Lock Date</th>
					<th>Employee Release Date</th>
					<th>Is Hard Locked</th>
					<th>Skill</th>
					<th>Level</th>
					<th>Project</th>
					
				</thead>    
				<tbody> 
			     	<c:forEach items="${rrdList}" var="rrd">
			     		<tr><td>${rrd.rrdNumber}</td>
			     			<td>${rrd.employeeNumber}</td>
			     			<td>${rrd.employeeLockDate}</td>
			     			<td>${rrd.employeeReleaseDate}</td>
			     			<td>${rrd.isHardLocked}</td>
			     			
			     			<c:forEach var="rowEntity" items='${rrd.resourceDetails}' varStatus="innerrrd">            
				     			<td>${rowEntity.skill}</td>
				     			<td>${rowEntity.level}</td> 
   							 </c:forEach>
							
							<c:forEach var="rowEntity" items='${rrd.rrdDetails}' varStatus="innerrrd">            
				     			<td>${rowEntity.project}</td>
   							 </c:forEach>
			     			
											
						</tr>
			     	</c:forEach>  
				</tbody>
			</table>
			<script>  $(function(){
							$("#ebdRrdTable").dataTable( {
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