<tr id="taskDatta_${addTaskForm.taskId}" style="width: 100%;">
	<td>${addTaskForm.taskName}</td>
	<td>${addTaskForm.taskDesc}</td>
	<td>${addTaskForm.workType}</td>
	<td>${addTaskForm.taskCreateDate}</td>
	<td>${addTaskForm.taskStartDate}</td>
	<td>${addTaskForm.taskEndDate}</td>
	<td>${addTaskForm.taskStatus}</td>
	<td></td>
	<td>${addTaskForm.taskReviewUserName}</td>
	<td><a href="#" id="editTask" onclick="edit('${addTaskForm.taskId}');"><img
			alt="edit project" src="./resources/edit.gif" width="20px;"></a></td>
	<td><a href="#" id="deleteTask" onclick="deleteTask('${addTaskForm.taskId}');"><img
			alt="delete project" src="./resources/delete.gif" width="20px;"></a></td>
</tr>