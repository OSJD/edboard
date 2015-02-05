<tr id="taskDatta_${addTaskForm.taskId}" style="width: 100%;">
	<td>${addTaskForm.taskName}</td>
	<td>${addTaskForm.taskDesc}</td>
	<td>${addTaskForm.taskHrs}</td>
	<td>${addTaskForm.taskCreateDate}</td>
	<td>${addTaskForm.taskAction}</td>
	<td>${addTaskForm.taskReviewUser}</td>
	<td>${addTaskForm.rejComment}</td>
	<td>${addTaskForm.taskComments}</td>
	<td><a href="#" id="editTask" onclick="edit('${addTaskForm.taskId}');"><img
			alt="edit project" src="./resources/edit.gif" width="20px;"></a></td>
	<td><a href="#" id="deleteTask" onclick="deleteTask('${addTaskForm.taskId}');"><img
			alt="delete project" src="./resources/delete.gif" width="20px;"></a></td>
</tr>