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
		var updateVacationRequestPopup=$("#updateVacationRequestPopup").dialog({
			autoOpen : false,
			height : 470,
			width : 430,
			modal : true,
			buttons:{
				"Update":function(){
					$.ajax({
						type : "POST",
						url : "./approveVacation.do",
						data :$("#updateVacationRequestForm").serialize(),
						success : function(response) {
							$("#status"+$("#updateVacationRequestPopup").data("vacationId")).html(response);
							$("#approverComments"+$("#updateVacationRequestPopup").data("vacationId")).html($("#newSupervisorComments").val());
							updateVacationRequestPopup.dialog("close");
						},
						error : function(data) {},
						complete:function(data){
							
						}
					});
				}
			}
			
		});
		var vacationRequestPopup=$("#vacationRequestPopup").dialog({
			autoOpen : false,
			height : 400,
			width : 430,
			modal : true,
			buttons:{
				"Submit":function(){
					$.ajax({
						type : "POST",
						url : "./addVacation.do",
						data :$("#vacationRequestForm").serialize(),
						success : function(status) {
							if(status=="success"){
								alert('Request submitted successfully!');
								$("#mainBody .subtabs").attr("id","pwsubtab3");
								$("#mainBody .subtabs").attr("action","./calendar.do");
								$("#mainBody .subtabs").get(0).click();
							} else {
								alert('Request not submitted successfully!');
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
			$.ajax({
				type : "POST",
				url : "./getBackUpList.do",
				success : function(backupresource) {
					$("#newBackupResource option").remove();
					$("#newBackupResource").append("<option value='-1'>--select--</option>");
					for(var index in backupresource){
						$("#newBackupResource").append("<option value='"+backupresource[index].id+"'>"+backupresource[index].label+"</option>");
					}
				},
				error : function(data) {},
				complete:function(data){
					
				}
			});		
			$("#newVacationType").val(0);
			$("#newVacationType option[value='-4']").remove();
			$("#newVacationType").append("<option value='-4'>Pudlic Holiday</option>");
			vacationRequestPopup.dialog("open");
		});
		
		$(".updateVacationDetail").button().on("click",function(){
			var vacationId=$(this).attr("id");
			var vacationType=$("#vacationType"+vacationId).html().trim()
			var backUpResource=$("#backUpResource"+vacationId).html().replace(/\s+/g, '');
			var supervisorId=$("#supervisorId"+vacationId).val();
			var loginUserId=$("input[name='loginUserId']").val();
			if(loginUserId==supervisorId){
				$("#updatevacationId").val(vacationId);
				$("#newVacationTypeDiv").html(vacationType);
				$("#vacationStartDateDiv").html($("#startDate"+vacationId).html());
				$("#vacationEndDateDiv").html($("#endDate"+vacationId).html());
				$("#newBackupResourceDiv").html(backUpResource);
				$("#newCommentsDiv").html($("#comments"+vacationId).html());
				updateVacationRequestPopup.data("vacationId",vacationId);
				updateVacationRequestPopup.dialog("open");
			}else{
				$.ajax({
					type : "POST",
					url : "./getBackUpList.do",
					success : function(backupresource) {
						$("#newBackupResource option").remove();
						$("#newBackupResource").append("<option value='-1'>--select--</option>");
						for(var index in backupresource){
							var key=backupresource[index].id;
							if(key==backUpResource){
								$("#newBackupResource").append("<option value='"+key+"' selected=\"selected\">"+backupresource[index].label+"</option>");
							} else {
								$("#newBackupResource").append("<option value='"+key+"'>"+backupresource[index].label+"</option>");
							}
						}
					},
					error : function(data) {},
					complete:function(data){
						
					}
				});
				$("#newVacationType option[value='-4']").remove();
				$("#editVacationId").val(vacationId);
				$('#newVacationType option').filter(function() { 
				    return ($(this).text() == vacationType);
				}).prop('selected', true);
				$("#vacationStartDate").val($("#startDate"+vacationId).html());
				$("#vacationEndDate").val($("#endDate"+vacationId).html());
				$("#newComments").val($("#comments"+vacationId).html());
				vacationRequestPopup.dialog("open");	
			}
		});
		
		var addTaskDialog=$("#addTaskPanel").dialog({
			autoOpen : false,
			height : 810,
			width : 1150,
			modal : true,
			buttons : {
				"Add Task" :  {
					text:"Add Task",
					id:"addTaskButton",
					click:function(){
					var cId = $('#addTaskPanel').data('param');
					$("#componentId").val(cId);
					
					var jsonString=edb.jsonString({
						"taskId":{dataType:"int"},
						"taskName":{dataType:"string"},
						"taskType":{dataType:"string"},
						"taskStartDate":{dataType:"string"},
						"taskEndDate":{dataType:"string"},
						"taskDesc":{dataType:"string"},
						"taskReviewUser":{dataType:"int"},
						"componentId":{dataType:"int"},
						"taskLedger":{
							dataType:"object",
							itemElement:{
								"taskDvlprComments":{dataType:"string"},
								"taskHrs":{dataType:"int"},
								"taskStatus":{dataType:"string"}
							}								
						  }
						},$("#addTaskForm"));
					//alert(jsonString);
					
					$.ajax({
						type : "POST",
						url : "./addTask.do",
						contentType: "application/json; charset=utf-8",
					    data : jsonString,						
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
				Cancel : function() {
					addTaskDialog.dialog("close");
					reset();
				},
			},
			 close:function () {
				 reset();
			 }

		});
		
		var editTaskDialog=$("#editTaskPanel").dialog({
			autoOpen : false,
			height : 780,
			width : 1150,
			modal : true,
			buttons : {

				"Edit Task" :  {
					text:"Edit Task",
					id:"editTaskButton",
					click:function(){
						var jsonString=edb.jsonString({
							"taskId":{dataType:"int"},
							"taskLedger":{
											dataType:"object",
											itemElement:{
												"taskDvlprComments":{dataType:"string"},
												"taskHrs":{dataType:"int"},
												"taskStatus":{dataType:"string"}
											}								
										  },
							"taskReviewHistory":{
													dataType:"object",
													itemElement:{
														"reviewHistoryId":{dataType:"int"},
														"reviewComment":{dataType:"string"},
														"devResponse":{dataType:"string"},
														"isReviewValid":{dataType:"string"}
													}
												 }
							},$("#editTaskForm"));
						//alert(jsonString);
						$.ajax({
							type : "POST",
							url : "./editTask.do",
							contentType: "application/json; charset=utf-8",
							data :jsonString,
							beforeSend : function() {
							},
							success : function(response) {
								editTaskDialog.dialog("close");
							},
							error : function(jqXHR, exception) {
								/*$("#editTaskPanel").dialog("close");
								$("#projectWorkMenu").click();*/
								alert(jqXHR.status+" | "+exception);
							},
							complete:function(data){
								addTaskDialog.dialog("close");
								reset();
							}
						});
					}
				},
				Cancel : function() {
					editTaskDialog.dialog("close");
					reset();
				},
			},
			 close:function () {
				 reset();
			 }

		});
		
		
		$( "#taskStartDateId" ).unbind("click").datepicker({
			dateFormat: 'mm/dd/yy',
			showOn: 'button',
			buttonText: 'Show Date',
			buttonImageOnly: true,
			buttonImage: 'resources/cal.gif',
			onSelect : function(selecteddate){
				var context=edb.getEDBContextInstance();
				var fDate,lDate,cDate;
				var startDate=context.getAttribute("taskCompStartDate");
				var endDate=context.getAttribute("taskCompEndDate");
			    fDate = Date.parse(startDate);
			    lDate = Date.parse(endDate);
			    cDate = Date.parse(selecteddate);
			    if(!(cDate <= lDate && cDate >= fDate)) {
			    	alert("Please select the date between "+startDate+" and "+endDate);
			    	$( "#taskStartDateId" ).val("");
			        return true;
			    }
			    return false;
			}
			
		});
		$( "#taskEndDateId" ).unbind("click").datepicker({
			dateFormat: 'mm/dd/yy',
			showOn: 'button',
			buttonText: 'Show Date',
			buttonImageOnly: true,
			buttonImage: 'resources/cal.gif',
			onSelect : function(selecteddate){
				var context=edb.getEDBContextInstance();
				var fDate,lDate,cDate;
				var startDate=context.getAttribute("taskCompStartDate");
				var endDate=context.getAttribute("taskCompEndDate");
			    fDate = Date.parse(startDate);
			    lDate = Date.parse(endDate);
			    cDate = Date.parse(selecteddate);
			    if(!(cDate <= lDate && cDate >= fDate)) {
			    	alert("Please select the date between "+startDate+" and "+endDate);
			    	$( "#taskEndDateId" ).val("");
			        return true;
			    }
			    return false;
			}

		});

		$(".editTaskPopup").on("click",function(){
			
			var componentId=$(this).attr("id");
			var taskId=$(this).attr("taskId");
			var workType=$(this).attr("workType");
			var projectId=$(this).attr("projectId");			
			$("#editTaskProjectName").html($("#projName"+componentId).val());
			$("#editTaskReleaseName").html($("#releaseName"+componentId).val());
			$("#editTaskComponentName").html($("#componentName"+componentId).val());
			$("#editTaskAssignedWork").html($("#assignedWork"+componentId).val());
			$("#editTaskCompStartDate").html($("#startDate"+componentId).val());
			$("#editTaskCompEndDate").html($("#endDate"+componentId).val());
			$("#editTaskId").val(taskId);
			$.ajax({
				type : "POST",
				url : "./getTaskByTaskId.do",
				dataType:'json',
				data : {taskId:taskId,
					projectId:projectId},
				success : function(response) {
					
					// Reviewer List
					var reviewers=response["reviewerList"];
					$("#editTaskReviewUser option").remove();
					$("#editTaskReviewUser").append("<option value='-1'>--- Select ---</option>")
					for(var index in reviewers){
						$("#editTaskReviewUser").append("<option value='"+reviewers[index].id+"'>"+reviewers[index].label+"</option>")
					}
					
					//Task object
					var task=response["task"];
					$("#editTaskName").html(task.taskName);
					$("#editTaskType").html(task.taskType); 
					$("#editTaskDesc").html(task.taskDesc); 
					$("#editTaskStartDateId").html(task.taskStartDate); 
					$("#editTaskEndDateId").html(task.taskEndDate); 
					$("#editTaskActivitySelect option").remove();
					$("#editTaskActivitySelect").append("<option value='-1'>Enter new comment</option>");
					var activity=task.taskLedger;
					var context=edb.getEDBContextInstance();
					context.clean();
					for(var index in activity){
						$("#editTaskActivitySelect").append("<option value='"+activity[index].taskLedgerId+"'>"+activity[index].taskActivityDate+"</option>");
						context.addAttribute(activity[index].taskLedgerId,activity[index]);
					}
					var reviewHistory=task.taskReviewHistory;
					$("#rcTable > tbody > tr").remove();
					for(var index in reviewHistory){
						//alert(reviewHistory[index].reviewComment+" | "+reviewHistory[index].devResponse+" | "+reviewHistory[index].isReviewValid);
						var reviewCommentRowNumber=$('#rcTable tr:last').index()+2;
						if(workType=="Review"){
							var rowForReviewer="<tr><td><input type=\"hidden\" name=\"reviewHistoryId"+reviewCommentRowNumber+"\" id=\"reviewHistoryId"+reviewCommentRowNumber+"\" value=\""+reviewHistory[index].reviewHistoryId+"\"><textarea cols=\"60\" rows=\"5\" name=\"reviewComment"+reviewCommentRowNumber+"\" id=\"reviewComment"+reviewCommentRowNumber+"\">"+reviewHistory[index].reviewComment+"</textarea></td>"+
								"<td><div style=\"width:365px;height: 75px;overflow: auto;\">"+reviewHistory[index].devResponse+"</div></td>"+
								"<td><input type=\"checkbox\" name=\"isReviewValid"+reviewCommentRowNumber+"\" id=\"isReviewValid"+reviewCommentRowNumber+"\" checked=\"checked\"></td></tr>";
							$("#rcTable tbody").append(rowForReviewer);
						}else {
							var rowForDeveloper="<tr><td><input type=\"hidden\" name=\"reviewHistoryId"+reviewCommentRowNumber+"\" id=\"reviewHistoryId"+reviewCommentRowNumber+"\" value=\""+reviewHistory[index].reviewHistoryId+"\"><div style=\"width:365px;height: 75px;overflow: auto;\">"+reviewHistory[index].reviewComment+"</div></td>"+
								"<td><textarea cols=\"60\" rows=\"5\" name=\"devResponse"+reviewCommentRowNumber+"\" id=\"devResponse"+reviewCommentRowNumber+"\">"+reviewHistory[index].devResponse+"</textarea></td>"+
								"<td><input type=\"checkbox\" name=\"isReviewValid"+reviewCommentRowNumber+"\" id=\"isReviewValid"+reviewCommentRowNumber+"\" checked=\"checked\"></td></tr>";
							$("#rcTable tbody").append(rowForDeveloper);
						}
					}
					$("#rcMainDiv").animate({ scrollTop: $("#rcMainDiv")[0].scrollHeight}, 1000);
				},
				error : function(data) {
					alert(data.error);
				}
			});
			
			$("#editTaskPanel").data('param', componentId);
			editTaskDialog.dialog('open');
		});
		
		$("#editReviewRow").button().on("click",function(){
			$(this).unbind("click");
			var reviewCommentRowNumber=$('#rcTable tr:last').index()+2;
			var row="<tr><td><input type=\"hidden\" name=\"reviewHistoryId"+reviewCommentRowNumber+"\"=\"reviewHistoryId"+reviewCommentRowNumber+"\" id=\"reviewHistoryId"+reviewCommentRowNumber+"\" value=\"0\"><textarea cols=\"60\" rows=\"5\" name=\"reviewComment"+reviewCommentRowNumber+"\" id=\"reviewComment"+reviewCommentRowNumber+"\"></textarea></td>"+
				"<td><div style=\"width:365px;height: 75px;overflow: auto;\"></div></td>"+
				"<td><input type=\"checkbox\" name=\"isReviewValid"+reviewCommentRowNumber+"\" id=\"isReviewValid"+reviewCommentRowNumber+"\" value=\"Y\"></td></tr>";
			$("#rcTable").append(row);
			$("#rcMainDiv").animate({ scrollTop: $("#rcMainDiv")[0].scrollHeight}, 1000);
		});
	
		$(".addTaskPopup").on("click", function() {
			
			var componentId=$(this).attr("id");
			var projectId=$(this).attr("projectId");
			$("#taskProjectName").html($("#projName"+componentId).val());
			$("#taskReleaseName").html($("#releaseName"+componentId).val());
			$("#taskComponentName").html($("#componentName"+componentId).val());
			$("#taskAssignedWork").html($("#assignedWork"+componentId).val());
			var startDate=$("#startDate"+componentId).val();
			var endDate=$("#endDate"+componentId).val();
			$("#taskCompStartDate").html(startDate);
			$("#taskCompEndDate").html(endDate);
			
			//storing componentStartDate and componentEndDate in edb context space.
			var context=edb.getEDBContextInstance();
			context.addAttribute("taskCompStartDate",startDate);
			context.addAttribute("taskCompEndDate",endDate);
			
			$.ajax({
				type : "POST",
				url : "./getTaskIdsByComponentId.do",
				dataType:'json',
				data : {componentId:componentId,
						projectId:projectId},
				success : function(response) {
					$.each(response, function(key, value){
						if(key=="myTasks"){
							var tasks=value;
							$("#taskIdSelect option").remove();
							$("#taskIdSelect").append("<option value='0'>--- Select ---</option>");
							$("#taskIdSelect").append("<option value='-1'>Create New Task</option>");
							for(var index in tasks){
								$("#taskIdSelect").append("<option value='"+tasks[index].id+"'>"+tasks[index].label+"</option>")
							}
						} else if(key=="reviewerList"){
							var reviewers=value;
							$("#taskReviewUser option").remove();
							$("#taskReviewUser").append("<option value='-1'>--- Select ---</option>")
							for(var index in reviewers){
								$("#taskReviewUser").append("<option value='"+reviewers[index].id+"'>"+reviewers[index].label+"</option>")
							}
						}
						
					});

				},
				error : function(data) {
				}
			});
			
			$("#addTaskPanel").data('param', componentId);
			addTaskDialog.dialog('open');
		});
		
		/**
		 * Hide and show of tasks
		 */
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
							$("#comptree"+obj.id).html("[+]");
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

		$("#addArtifacts").button().on("click", function() {
			$(this).unbind("click");
			developmentArtifactspopup.dialog("open");
		});
		

		$("#taskIdSelect").unbind("change").on("change",function(){
			var taskId=$("#taskIdSelect").val();
			if(taskId=='-1'){
				$("#newTask").show();
				$("#taskType").val(0).removeAttr('disabled'); 
				$("#taskDesc").val("").removeAttr('disabled'); 
				$("#taskStartDateId").val("").removeAttr('disabled'); 
				$("#taskEndDateId").val("").removeAttr('disabled'); 

			} else {
				$("#newTask").hide();
				$.ajax({
					type : "POST",
					url : "./getTaskByTaskId.do",
					dataType:'json',
					data : {taskId:taskId,
						projectId:0},
					success : function(response) {
						var task=response["task"];
						$("#taskType").val(task.taskType).attr("disabled", "disabled"); 
						$("#taskDesc").val(task.taskDesc).attr("disabled", "disabled"); 
						$("#taskStartDateId").val(task.taskStartDate).attr("disabled", "disabled"); 
						$("#taskEndDateId").val(task.taskEndDate).attr("disabled", "disabled"); 
						$("#taskActivitySelect option").remove();
						$("#taskActivitySelect").append("<option value='-1'>Enter new comment</option>");
						var activity=task.taskLedger;
						var context=edb.getEDBContextInstance();
						for(var index in activity){
							$("#taskActivitySelect").append("<option value='"+activity[index].taskLedgerId+"'>"+activity[index].taskActivityDate+"</option>");
							context.addAttribute(activity[index].taskLedgerId,activity[index]);
						}

					},
					error : function(data) {
						alert(data.error);
					}
				});
			}
		});
		
		$("#editTaskActivitySelect").on("change",function(){
			$(this).unbind("change");
			var activity=$("#editTaskActivitySelect").val();
			if(activity!=-1){
				var context=edb.getEDBContextInstance();
				var taskActivityObject=context.getAttribute(activity);
				$("#editTaskDvlprComments").val(taskActivityObject.taskDvlprComments).attr("disabled", "disabled");
				$("#editTaskHrs").val(taskActivityObject.taskHrs).attr("disabled", "disabled");
				$("#editTaskStatus").val(taskActivityObject.taskStatus).attr("disabled", "disabled");
				$("#editTaskReviewUser").val(taskActivityObject.taskReviewUser).attr("disabled", "disabled");
			} else {
				//$("#editTaskActivityDateId").html("Date :"+$.datepicker.formatDate('mm/dd/yy', new Date()));
				$("#editTaskDvlprComments").val("").removeAttr('disabled');
				$("#editTaskHrs").val(0).removeAttr('disabled');
				$("#editTaskStatus").val(0).removeAttr('disabled');
				$("#editTaskReviewUser").val(-1).removeAttr('disabled');
			}
		});

		$("#taskActivitySelect").unbind("change").on("change",function(){
			var activity=$("#taskActivitySelect").val();
			if(activity!=-1){
				var context=edb.getEDBContextInstance();
				var taskActivityObject=context.getAttribute(activity);
				$("#taskDvlprComments").val(taskActivityObject.taskDvlprComments).attr("disabled", "disabled");
				$("#taskHrs").val(taskActivityObject.taskHrs).attr("disabled", "disabled");
				$("#taskStatus").val(taskActivityObject.taskStatus).attr("disabled", "disabled");
				$("#taskReviewUser").val(taskActivityObject.taskReviewUser).attr("disabled", "disabled");
				//alert(taskActivityObject.taskStatus +"|"+taskActivityObject.taskReviewUser);
			} else {
				$("#taskActivityDateId").html("Date :"+$.datepicker.formatDate('mm/dd/yy', new Date()));
				$("#taskDvlprComments").val("").removeAttr('disabled');
				$("#taskHrs").val(0).removeAttr('disabled');
				$("#taskStatus").val(0).removeAttr('disabled');
				$("#taskReviewUser").val(-1).removeAttr('disabled');
			}
		});
		
		$("#taskStatus").unbind("change").on("change",function(){
			var taskStatus=$(this).val();
			if(taskStatus!=1){
				$("#taskReviewUser").val(-1).attr("disabled", "disabled");
			} else {
				$("#taskReviewUser").val(-1).removeAttr('disabled');
			}
			
			
		}); 
		
		
		$("#newVacationType").on("change",function(){
			$(this).unbind("change");
			var vactionType=$(this).val();
			if(vactionType==-4){
				$("#locationDiv").show();
				$("#backUpRow").hide();
			} else {
				$("#locationDiv").hide();
				$("#backUpRow").show();
			}
			
		});
		
});



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
	$("#taskIdSelect").val('');
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
