<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="ebdtable">
	<tr>
		<th>Emp Name</th>
		<th>Score</td>
		<th>Time Taken</th>
		<th>Rank</th>
	</tr>
	<c:forEach items="${viewEvaluation}" var="result" varStatus="status">
		<tr>
			<td>${result.empName}</td>
			<td>${result.score}</td>
			<td>${result.timeTaken}</td>
			<td>${status.index+1}</td>
		</tr>
	</c:forEach>
</table>