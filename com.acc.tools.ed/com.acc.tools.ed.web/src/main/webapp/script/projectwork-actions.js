$(document).ready(
		function() {
			
		$("#addTaskPanel").dialog({
			autoOpen : false,
			height : 700,
			width : 1100,
			modal : true,
			buttons : {
				"Add Task" :  {
					text:"Add Task",
					id:"addTaskButton",
					click:function(){
					var cId = $('#addTaskPanel').data('param');
					$("#componentId").val(cId);
					$.ajax({
						type : "POST",
						url : "./addTask.do",
						data : 
							$("#addTaskForm").serialize(),
						beforeSend : function() {
						},
						success : function(response) {
							$("#NoTask"+cId).remove();
							$("#taskTable"+cId).append(response);
						},
						error : function(data) {
							$("#addTaskPanel").dialog("close");
							$("#projectWorkMenu").click();

						},
						complete:
							function(data)
							{
							reset();
							$("#addTaskPanel").dialog("close");
							}
					});
					}
				},
				"Edit Task" :  {
					text:"Edit Task",
					id:"editTaskButton",
					click:function(){
					var cId = $('#addTaskPanel').data('param');
					var uId = $('#addTaskPanel').attr("edbUser");
					var taskId= "taskDatta_"+$("#taskId").val();
					$.ajax({
						type : "POST",
						url : "./saveTask.do",
						data : 
							$("#addTaskForm").serialize(),
						beforeSend : function() {
						},
						success : function(response) {
							$("#pwMainContainer #taskTable"+cId).find('tr[id="'+taskId+'"]').replaceWith(response);
						},
						error : function(data) {
							$("#addTaskPanel").dialog("close");
							$("#projectWorkMenu").click();

						},
						complete:
							function(data)
							{
							$("#addTaskPanel").dialog("close");
							reset();
							}
					});
					}
				},
				Cancel : function() {
					$("#addTaskPanel").dialog("close");
					reset();
				},
			},
			 open:function () {
				 if($("#popupDisplay").val()=="edit")
				 {
					 $("#addTaskButton").hide();
					 $("#editTaskButton").show();
					 $("#taskNameSelect").attr('disabled',true);
					
				 }
				 else
				 {
					 $("#addTaskButton").show();
					 $("#editTaskButton").hide();
					 $("#taskNameSelect").removeAttr('disabled');
					 
				 }
			 },
			 close:function () {
				 reset();
			 }

		});

	
		$(".addTaskPopup").unbind("click").on("click", function() {
			var componentId=$(this).attr("id");
			var taskTypeDisplay=$(this).attr("taskType");
			if(taskTypeDisplay=="teamTasks")
			{
				$("#taskActionRow").show();
				$("#taskCommentRow").hide();
				$("#taskReviewUser").val($('#addTaskPanel').attr("edbUser"));
				
			}
			else
			{
				$("#taskActionRow").hide();
				$("#taskCommentRow").show();
			}
			$("#popupDisplay").val('add');
			$("#addTaskPanel").data('param', componentId).dialog('open');
			
		});
		
		
		$(".componentData").hide();	
		$(".compData").hide();	
		$(".taskData").hide();
		$(".releaseRow").on("click",function(){
			var releaseId=$(this).attr("id");
			$("#release"+releaseId).toggle(function(){
				if($("#release"+releaseId).is(":visible")){
					$("#comptree"+releaseId).html("[-]");
					$("a[class='releaseRow']").each(function(index, obj){
						if(obj.id!=releaseId){
							$("#release"+obj.id).hide();
						} 
					});
				} else{
					$("#comptree"+releaseId).html("[+]");
				}
			});
		});
		$(".devRow").on("click",function(){
			var devId=($(this).attr("id")).replace(/\s+/g, '');
			$("#devdev"+devId).toggle(function(){
				if($("#devdev"+devId).is(":visible")){
					$("#devtree"+devId).html("[-]");
				} else
					$("#devtree"+devId).html("[+]");
			});
		});
		$(".componentRow").on("click",function(){
			var componentId=$(this).attr("id");
			$("#component"+componentId).toggle(function(){
				if($("#component"+componentId).is(":visible")){
					$("#tasktree"+componentId).html("[-]");
				} else
					$("#tasktree"+componentId).html("[+]");
			});
		});
		
});


$( "#taskCreateDate" ).datepicker({
	 onSelect: function (date) {
       var date2 = $('#startDate').datepicker('getDate');
       date2.setDate(date2.getDate() + 1);
       $('#endDate').datepicker('setDate', date2);
       //sets minDate to dt1 date + 1
       $('#endDate').datepicker('option', 'minDate', date2);
   }
});

$( "#taskStartDate" ).datepicker({
	 onSelect: function (date) {
        var date2 = $('#startDate').datepicker('getDate');
        date2.setDate(date2.getDate() + 1);
        $('#endDate').datepicker('setDate', date2);
        //sets minDate to dt1 date + 1
        $('#endDate').datepicker('option', 'minDate', date2);
    }
});
$( "#taskEndDate" ).datepicker({
	 onClose: function () {
        var dt1 = $('#startDate').datepicker('getDate');
        var dt2 = $('#endDate').datepicker('getDate');
        if (dt2 <= dt1) {
            var minDate = $('#endDate').datepicker('option', 'minDate');
            $('#endDate').datepicker('setDate', minDate);
        }
    }
});

$("#taskNameSelect").unbind("change").on("change",function(){
	if($("#taskNameSelect").val()=='-1'){
		$("#newTask").show();
	} else {
		$("#newTask").hide();
	}
}); 


$("#taskAction").unbind("change").on("change",function(){
	if ($("#taskAction").val() == "rejected")
		$("#div1").show(); 
	else
		$("#div1").hide();
	
}); 

 function edit(taskId) {
	 var taskTypeDisplay=$("#editTask").attr("taskType");
		if(taskTypeDisplay=="teamTasks")
		{
			$("#taskActionRow").show();
			$("#taskReviewUser").val($('#addTaskPanel').attr("edbUser"));
		}
		else
		{
			$("#taskActionRow").hide();
		}
	$.ajax({
		type : "POST",
		url : "./editTask.do",
		data :{taskId:taskId},
		dataType : 'json',
		beforeSend : function() {
		},
		success : function(response) {
			for(var obj in response)
			{
				$("#taskId").val(response[obj].taskId);
				$("#taskType").val(response[obj].taskType);
				$("#newTask").show();
				$("#taskName").val(response[obj].taskName);
				$("#componentId").val(response[obj].componentId);
				$("#taskDesc").val(response[obj].taskDesc);
				$("#taskHrs").val(response[obj].taskHrs);
				$("#taskCreateDate").val(response[obj].taskCreateDate);
				$("#taskStartDate").val(response[obj].taskStartDate);
				$("#taskEndDate").val(response[obj].taskEndDate);
				$("#taskStatus").val(response[obj].taskStatus);
				$("#taskAction").val(response[obj].taskAction);
				$("#taskComments").val(response[obj].taskComments);
				if($("#taskAction").val()=="rejected")
				{
					$("#rejComment").val(response[obj].rejComment);
					$("#rejComment").show();
				}
			}
			$("#popupDisplay").val('edit');
			$("#addTaskPanel").dialog("open");
		},
		error : function(data) {
			$("#addTaskPanel").dialog("close");
			$("#projectWorkMenu").click();

		}
		
		
	});
}

 function deleteTask(taskId) {
	 var taskIdRow="taskDatta_"+taskId;
	 var cId = $('#addTaskPanel').data('param');
		$.ajax({
			type : "POST",
			url : "./deleteTask.do",
			data :{taskId:taskId},
			beforeSend : function() {
			},
			success : function(response) {
				$("#pwMainContainer #taskTable"+cId).find('tr[id="'+taskIdRow+'"]').remove();
			},
			error : function(data) {
				$("#projectWorkMenu").click();

			}
			
		});

	}
function reset()
{
	$("#taskAction").val('');
	$("#taskNameSelect").val('');
	$("#rejComment").val('');
	$("#taskStatus").val('');
	$("#taskEndDate").val('');
	$("#taskStartDate").val('');
	$("#taskCreateDate").val('');
	$("#taskName").val('');
	$("#taskType").val('');
	$("#taskDesc").val('');
	$("#taskHrs").val('');
	$("#div1").hide();
	$("#newTask").hide();
	$("#taskReviewUser").val('');
	$("#taskComments").val('');
}