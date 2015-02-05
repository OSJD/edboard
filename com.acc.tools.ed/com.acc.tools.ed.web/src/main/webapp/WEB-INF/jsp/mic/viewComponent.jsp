<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
<form>
	<c:forEach var="component" items="${componentList}">
		<tr>
			<td width="180px;" id="compID" style="display:none">${component.projectId}</td>
			<td width="165px;" id="compName">${component.componentName}</td>
			<td width="385px;" id="compFuncDesc"><div style="height:20px;display:table-cell;vertical-align:middle;">${component.functionalDesc}</div></td>
			<td width="100px;" id="comStDate">${component.startDate}</td>
			<td width="100px;" id="compEtDate">${component.endDate}</td>
			<td width="180px;" id="compResName">${component.resourceName}</td>
			<td><img alt="edit project" src="./resources/edit.gif"
				width="20px;"></td>
			<td><img alt="delete project" src="./resources/delete.gif"	width="20px;"></td>
		</tr>
	</c:forEach>
</form>
</body>