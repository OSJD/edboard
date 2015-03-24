$(document).ready(
		function() {
						
		/*Calendar Actions*/
		$("#vacationEndDate").edbdatepicker({
			//trigger : "#buttonn1"
			showOn: 'button',
			buttonText:'Show Date',
			buttonImageOnly: true,
			buttonImage: 'resources/cal.gif',
			dateFormat: 'mm/dd/yy',
			constranInput: true 
		});

		
		$( "#vacationStartDate" ).datepicker({ 
			showOn: 'button',
			buttonText:'Show Date',
			buttonImageOnly: true,
			buttonImage: 'resources/cal.gif',
			dateFormat: 'mm/dd/yy',
			constranInput: true 
		});
		
		var vacationRequestPopup=$("#vacationRequestPopup").dialog({
			autoOpen : false,
			height : 420,
			width : 430,
			modal : true,
			buttons:{
				"Submit":function(){
					$.ajax({
						type : "POST",
						url : "./addVacation.do",
						data :$("#vacationRequestForm").serialize(),
						beforeSend : function() {
						},
						success : function(response) {
							if(response=="success"){
								alert('Request submitted successfully!');
							}
							vacationRequestPopup.dialog("close");
						},
						error : function(data) {},
						complete:function(data){
							
						}
					});
				}
			}
		});
		
		$("#vacationRequestBtn").on("click",function(){
			vacationRequestPopup.dialog("open");
		});
		
		var addTaskDialog=$("#addTaskPanel").dialog({
			autoOpen : false,
			height : 780,
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
						complete:function(data){
							addTaskDialog.dialog("close");
							reset();
						}
					});
					}
				},
				Cancel : function() {
					addTaskDialog.dialog("close");
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
		
		
		$( "#taskStartDateId" ).datepicker({
			dateFormat: 'mm/dd/yy',
			constrainInput: true
		});
		$( "#taskEndDateId" ).datepicker({
			dateFormat: 'mm/dd/yy',
			constrainInput: true
		});

	
		$(".addTaskPopup").on("click", function() {
			
			var componentId=$(this).attr("id");
			var taskTypeDisplay=$(this).attr("taskType");
			$("#taskProjectName").html($("#projName"+componentId).val());
			$("#taskReleaseName").html($("#releaseName"+componentId).val());
			$("#taskComponentName").html($("#componentName"+componentId).val());
			$("#taskAssignedWork").html($("#assignedWork"+componentId).val());
			var startDate=$("#startDate"+componentId).val();
			var endDate=$("#endDate"+componentId).val();
			$("#taskCompStartDate").html(startDate);
			$("#taskCompEndDate").html(endDate);
			
			

			
			$.ajax({
				type : "POST",
				url : "./getTaskIdsByComponentId.do",
				dataType:'json',
				data : {componentId:componentId},
				success : function(tasks) {
					$("#taskNameSelect option").remove();
					$("#taskNameSelect").append("<option value='0'>--- Select ---</option>")
					$("#taskNameSelect").append("<option value='-1'>Create New Task</option>");
					for(var index in tasks){
						$("#taskNameSelect").append("<option value='"+tasks[index].id+"'>"+tasks[index].label+"</option>")
					}
				},
				error : function(data) {
				}
			});
			
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
			$("#addTaskPanel").data('param', componentId);
			addTaskDialog.dialog('open');
		});
		
		
		$(".componentData").hide();	
		$(".compData").hide();	
		$(".taskData").hide();
		$(".releaseRow").unbind("click").on("click",function(){
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
		$(".devRow").unbind("click").on("click",function(){
			var devId=($(this).attr("id")).replace(/\s+/g, '');
			$("#devdev"+devId).toggle(function(){
				if($("#devdev"+devId).is(":visible")){
					$("#devtree"+devId).html("[-]");
				} else
					$("#devtree"+devId).html("[+]");
			});
		});
		$(".componentRow").unbind("click").on("click",function(){
			var componentId=$(this).attr("id");
			$("#component"+componentId).toggle(function(){
				if($("#component"+componentId).is(":visible")){
					$("#tasktree"+componentId).html("[-]");
				} else
					$("#tasktree"+componentId).html("[+]");
			});
		});
		
		$(".updateTaskComnt").button().unbind("click").on("click",function(){


			var componentId=this.id;
			
			var taskComments=$("#taskComments_"+componentId).val();
			//alert(approverComments);
			$.ajax({
				type : "POST",
				url : "./updateTaskComments.do",
				data : {
					componentId:componentId,
					taskComments:taskComments
				},
				success : function(response) {
					if(response=="success"){
						alert('Request status updated successfully!');
					}
					
				},
				error : function(data) {},
				complete:function(data){

				}
			});
		});
		
});

$(".taskCommentSubmit").button().unbind("click").on("click",function(){
	

	var taskId=this.id;
	
	var devloperComments=$("#taskDvlprComments_"+taskId).val();
	//alert(taskId);
	$.ajax({
		type : "POST",
		url : "./addTaskComments.do",
		data : {
			taskId:taskId,
			devloperComments:devloperComments
			
		},
		success : function(response) {
			if(response=="success"){
				alert('Request status updated successfully!');
			}
			vacationRequestPopup.dialog("close");
		},
		error : function(data) {},
		complete:function(data){

		}
	});
});

$("#taskNameSelect").unbind("change").on("change",function(){
	var taskId=$("#taskNameSelect").val();
	if(taskId=='-1'){
		$("#newTask").show();
	} else {
		$("#newTask").hide();
		//alert(taskId);
		$.ajax({
			type : "POST",
			url : "./getTaskByTaskId.do",
			dataType:'json',
			data : {taskId:taskId},
			success : function(task) {
				$("#taskType").val(task.taskType);
				$("#taskType").attr("disabled", "disabled"); 
				$("#taskDesc").val(task.taskDesc);
				$("#taskDesc").attr("disabled", "disabled"); 
				$("#taskStartDateId").val(task.taskStartDate);
				$("#taskStartDateId").attr("disabled", "disabled"); 
				$("#taskEndDateId").val(task.taskEndDate) 
				$("#taskEndDateId").attr("disabled", "disabled"); 
				$("#taskActivitySelect option").remove();
				$("#taskActivitySelect").append("<option value='-1'>Enter new comment</option>");
				var activity=task.taskLedger;
				var context=edb.getEDBContextInstance();
				for(var index in activity){
					$("#taskActivitySelect").append("<option value='"+activity[index].taskLedgerId+"'>"+activity[index].taskActivity+"</option>");
					alert(activity[index].taskLedgerId);
					context.addAttribute(activity[index].taskLedgerId,activity[index]);
				}

			},
			error : function(data) {
				alert(data.error);
			}
		});
	}
}); 

$("#taskActivitySelect").on("change",function(){
	var context=edb.getEDBContextInstance();
	var taskActivityObject=context.getAttribute($("#taskActivitySelect").val());
	$("#taskActivityDateId").html("Date :"+taskActivityObject.taskActivityDate);
	
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
				alert(testing + val(response[obj].taskId));
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

var developmentArtifactspopup=$("#developmentArtifacts-popup").dialog({
	autoOpen : false,
	height : 420,
	width : 430,
	modal : true,
	buttons:{
		"Add":function(){
		alert("Added Successfully")
		}
	}
});

$("#addArtifacts").unbind("click").on("click", function() {
	alert("fd" );
	developmentArtifactspopup.dialog("open");
	alert("fd45" );
});
