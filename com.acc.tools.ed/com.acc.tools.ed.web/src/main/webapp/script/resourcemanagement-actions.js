		$(document)
			.ready(
					function() {
							$('#sucess_msg_div').dialog({ autoOpen: false }); // Initialize dialog plugin
							var addResourceDialog = $("#addemp-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
							modal : true,
							buttons : {
								"Add Resource" : function() {
									if(validateFieldAdd()){
										var level = $( "#resourceLevel option:selected" ).text();
										var capability = $( "#technicalCapability option:selected" ).text();
										var skill = $( "#technicalSkill option:selected" ).text();
										var empNumber = $("#empNumber").val();
										var empName = $("#empName").val();
										$("#level").val(level);
										$("#capability").val(capability);
										$("#skill").val(skill);
										alert("Employee Details Updated Sucessfully");
										$("#addEmpDetailsForm").submit();
										
								}
							
								},
								Cancel : function() {
									addResourceDialog.dialog("close");
								},
							},

						});
						
						$("#addResource").button().unbind("click").on("click", function() {
							$.ajax({
								type : "POST",
								url : "./resourceManagement.do",
								data : 
									$("#addEmpDetailsForm").serialize(),
									beforeSend : function() {
								},
								success : function(response) {
									$("#addemp-popup").html($(response).find("#addemp-popup").html());
									loadDatepicker();
								},
								error : function(data) {

								},
								complete: function(data){
								}
							});
							addResourceDialog.dialog("open");
						});
						
						$("#resourceFileUpload").unbind("click").on("click",function(){
							/*$("#resourceFileUploadForm").attr("action","./upload.do");
							document.getElementById("resourceFileUploadForm").enctype = "multipart/form-data";
							$("#resourceFileUploadForm").submit();*/
										var fileName = $.trim($("#fileUploadAddress").val());

										if (fileName == '') {
											alert("Please select a file to upload.");
										} else {
											$("#resourceFileUploadForm").attr("action", "./upload.do");
											document.getElementById("resourceFileUploadForm").enctype = "multipart/form-data";
											$("#resourceFileUploadForm").submit();
										}
						});
						
						
						
						
						//RM - Capability / Skill / Level Master Screen Changes - Start
						
						var addCapabilityDialog = $("#addCapability-popup").dialog({
							autoOpen : false,
							height : 450,
							width : 550,
							modal : true,
							buttons : {
								"Add Capability" : function() {
									if(validateCapabilityFields()){	
									$.ajax({
										type : "POST",
										url : "./addCapabilityForm.do",
										data : 
											$("#addCapabilityForm").serialize(),
											beforeSend : function() {
										},
										success : function(response) {
											if(response=="-2"){
												alert("Capability Already Exists");
											}
											else if(response=="-1"){
												alert("Error while trying to add Capability");
											}
											else{
												alert("Capability Added Successfully!");
											}
											$("#mainBody .subtabs").attr("id","rmSubtab4");
											$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
											$("#mainBody .subtabs").get(0).click();
										},
										error : function(data) {
											alert("Application error! Please call help desk. Error:"+data.error);
										},
										complete: function(data){
										}
									});
									addCapabilityDialog.dialog("close");
								}
									
								},
								Cancel : function() {
									addCapabilityDialog.dialog("close");
								},
							},
							
							
							

						});
						
						$("#addCapability").button().unbind("click").on("click", function() {
							
							if($("#existingCapability option:selected").val() != ""){
								alert('If you choose capability go for Edit Capability !!! ');
								$("#existingCapability").val(0);
								return false;
							}
							
							$.ajax({
								type : "POST",
								url : "./capabilitylevelskillmanagement.do",
								data : 
									$("#addCapabilityForm").serialize(),
									beforeSend : function() {
								},
								success : function(response) {
									$("#addCapability-popup").html($(response).find("#addCapability-popup").html());
								},
								error : function(data) {
									alert("Application error! Please call help desk. Error:"+data.error);
								},
								complete: function(data){
								}
							});
							addCapabilityDialog.dialog("open");
						});
						
						
						var addLevelDialog = $("#addLevel-popup").dialog({
							autoOpen : false,
							height : 450,
							width : 550,
							modal : true,
							buttons : {
								"Add Level" : function() {									
									if(validateLevelFields()){	
										$.ajax({
											type : "POST",
											url : "./addLevelForm.do",
											data : 
												$("#addLevelForm").serialize(),
												beforeSend : function() {
											},
											success : function(response) {
												if(response=="-2"){
													alert("Level Already Exists");
												}
												else if(response=="-1"){
													alert("Error while trying to add Level");
												}
												else{
													alert("Level Added Successfully!");
												}
												
												$("#mainBody .subtabs").attr("id","rmSubtab4");
												$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
												$("#mainBody .subtabs").get(0).click();
											},
											error : function(data) {
												alert("Application error! Please call help desk. Error:"+data.error);
											},
											complete: function(data){
											}
										});
										addLevelDialog.dialog("close");
									}
							
								},
								Cancel : function() {
									addLevelDialog.dialog("close");
								},
							},

						});
						
						$("#addLevel").button().unbind("click").on("click", function() {
							if($("#existingLevel option:selected").val() != ""){
								alert('If you choose level go for Edit Level !!! ');
								$("#existingLevel").val(0);
								return false;
							}
							
							$.ajax({
								type : "POST",
								url : "./capabilitylevelskillmanagement.do",
								data : 
									$("#addLevelForm").serialize(),
									beforeSend : function() {
								},
								success : function(response) {
									$("#addLevel-popup").html($(response).find("#addLevel-popup").html());
								},
								error : function(data) {
									alert("Application error! Please call help desk. Error:"+data.error);
								},
								complete: function(data){
								}
							});
							addLevelDialog.dialog("open");
						});
						
						
						var addSkillDialog = $("#addSkill-popup").dialog({
							autoOpen : false,
							height : 450,
							width : 550,
							modal : true,
							buttons : {
								"Add Skill" : function() {
								if(validateSkillFields()){	
										$.ajax({
											type : "POST",
											url : "./addSkillForm.do",
											data : 
												$("#addSkillForm").serialize(),
												beforeSend : function() {
											},
											success : function(response) {
												if(response=="-2"){
													alert("Skill Category/Name Already Exists");
												}
												else if(response=="-1"){
													alert("Error while trying to add Skill");
												}
												else{
													alert("Skill Added Successfully!");
												}
												
												$("#mainBody .subtabs").attr("id","rmSubtab4");
												$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
												$("#mainBody .subtabs").get(0).click();
												
												
											},
											error : function(data) {
												alert("Application error! Please call help desk. Error:"+data.error);
											},
											complete: function(data){
											}
										});
										addSkillDialog.dialog("close");
								}
							
								},
								Cancel : function() {
									addSkillDialog.dialog("close");
								},
							},

						});
						
						$("#addSkill").button().unbind("click").on("click", function() {	
							var existingCapability = $("#existingCapability option:selected").val();
							if($("#existingCapability option:selected").val() == ""){
								alert('Please select Capability under which Skill needs to be added!');
								$("#existingCapability").focus();
								return false;
							}
							
							var existingSkill = $("#existingSkill option:selected").val();
							if($("#existingSkill option:selected").val() != ""){
								alert('If you choose Skill go for Edit Skill !!! ');
								$("#existingSkill").focus();
								return false;
							}
							
							$.ajax({
								type : "POST",
								url : "./loadSkillDetails.do",
								data : {existingCapability : existingCapability},
									beforeSend : function() {
								},
								success : function(response) {
									 $("#capName").val(response.capabilityName);
									 $("#skillName").val(response.skillName);
									 addSkillDialog.dialog("open");
								},
								error : function(data) {
									alert("Application error! Please call help desk. Error:"+data.error);
								},
							});

						});
						
						var editCapabilityDialog = $("#editCapability-popup").dialog({
							autoOpen : false,
							height : 450,
							width : 550,
							modal : true,
							buttons : {
								"Edit Capability" : function() {
									if(validateNewCapabilityFields()){	
									$.ajax({
										type : "POST",
										url : "./editCapabilityForm.do",
										data : 
											$("#editCapabilityForm").serialize(),
											beforeSend : function() {
										},
										success : function(response) {
											if(response=="-2"){
												alert("Cannot Edit! Capability Already Exists");
											}
											else if(response=="-1"){
												alert("Error while trying to edit Capability");
											}
											else{
												alert("Capability Updated Successfully!");
											}
											$("#mainBody .subtabs").attr("id","rmSubtab4");
											$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
											$("#mainBody .subtabs").get(0).click();
										},
										error : function(data) {
											alert("Application error! Please call help desk. Error:"+data.error);
										},
										complete: function(data){
										}
									});
									editCapabilityDialog.dialog("close");
									}
								},
								Cancel : function() {
									editCapabilityDialog.dialog("close");
								},
							},
							
							
							

						});
							
									
						$("#editCapability").button().unbind("click").on("click", function() {
							
							var existingCapability = $("#existingCapability option:selected").val();
							if($("#existingCapability option:selected").val() == 0){
								alert('Please select Capability to Edit');
								$("#existingCapability").focus();
								return false;
							}

							
							$.ajax({
								type : "POST",
								url : "./viewCapabilityDetails.do",
								dataType:'json',
								data : {existingCapability : existingCapability},
									beforeSend : function() {
								},
								success : function(response) {
									 //$("#editCapability-popup").html($(response).find("#editCapability-popup").html());
									 $("#existingCap").val(response.existingCapability);
									 $("#newCapabilityName").val(response.capabilityName);
									editCapabilityDialog.dialog("open");
								},
								error : function(data) {
									alert("Application error! Please call help desk. Error:"+data.error);
								},
							});
							
						});
						
						
						var editLevelDialog = $("#editLevel-popup").dialog({
							autoOpen : false,
							height : 450,
							width : 550,
							modal : true,
							buttons : {
								"Edit Level" : function() {
									if(validateNewLevelFields()){	
									$.ajax({
										type : "POST",
										url : "./editLevelForm.do",
										data : 
											$("#editLevelForm").serialize(),
											beforeSend : function() {
										},
										success : function(response) {
											if(response=="-2"){
												alert("Cannot Edit! Level Already Exists");
											}
											else if(response=="-1"){
												alert("Error while trying to edit Level");
											}
											else{
												alert("Level Updated Successfully!");
											}
											$("#mainBody .subtabs").attr("id","rmSubtab4");
											$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
											$("#mainBody .subtabs").get(0).click();
										},
										error : function(data) {
											alert("Application error! Please call help desk. Error:"+data.error);
										},
										complete: function(data){
										}
									});
									editLevelDialog.dialog("close");
									}
								},
								Cancel : function() {
									editLevelDialog.dialog("close");
								},
							},
							
							
							

						});
							
									
						$("#editLevel").button().unbind("click").on("click", function() {
							
							var existingLevel = $("#existingLevel option:selected").val();
							if($("#existingLevel option:selected").val() == 0){
								alert('Please select Level to Edit');
								$("#existingLevel").focus();
								return false;
							}

							
							$.ajax({
								type : "POST",
								url : "./viewLevelDetails.do",
								dataType:'json',
								data : {existingLevel : existingLevel},
									beforeSend : function() {
								},
								success : function(response) {
									 $("#existingLev").val(response.existingLevel);
									 $("#newLevelName").val(response.LevelName);
									 editLevelDialog.dialog("open");
								},
								error : function(data) {
									alert("Application error! Please call help desk. Error:"+data.error);
								},
							});
							
						});
						
						var deleteCapabilityConfirm=$( "#deleteCapability-confirm" ).dialog({
					    	autoOpen : false,
					        height:150,
					        width:600,
					        modal: true,
					        buttons: {
					          "Delete Capability": function() {
					        	  var existingCapability = $("#existingCapability option:selected").val();
									$.ajax({
										type : "POST",
										url : "./deleteCapability.do",
										data :{existingCapability:existingCapability} ,												
										//dataType : 'json',		
										beforeSend:function(){
										  },
										success : function(response) {
											deleteCapabilityConfirm.dialog("close");
											if(response=="-2"){
												alert("Cannot Delete! Capability Does not Exist");
											}
											else if(response=="-1"){
												alert("Error while trying to delete Capability");
											}
											else{
												alert("Capability deleted Successfully!");
											}
											$("#mainBody .subtabs").attr("id","rmSubtab4");
											$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
											$("#mainBody .subtabs").get(0).click();	
										},
										error : function(data) {	
											alert("Application error! Please call help desk. Error:"+data.status);
										}
									});
					          },
					          Cancel: function() {
					        	  deleteCapabilityConfirm.dialog("close");
					          }
					        }
					      });
						$("#deleteCapability").button().unbind("click").on("click", function() {
							var existingCapability = $("#existingCapability option:selected").val();
							if($("#existingCapability option:selected").val() == ""){
								alert('Please select Capability to Delete');
								$("#existingCapability").focus();
								return false;
							}
							else{
								$("#deletedCapability").html(existingCapability+" will be permanently deleted and cannot be recovered.<br> Are you sure?");
								deleteCapabilityConfirm.dialog("open");
							}

						});
						
					
						 var deleteLevelConfirm=$( "#deleteLevel-confirm" ).dialog({
						    	autoOpen : false,
						        height:150,
						        width:600,
						        modal: true,
						        buttons: {
						          "Delete Level": function() {
						        	  var existingLevel = $("#existingLevel option:selected").val();
										$.ajax({
											type : "POST",
											url : "./deleteLevel.do",
											data :{existingLevel:existingLevel} ,												
											//dataType : 'json',		
											beforeSend:function(){
											  },
											success : function(response) {
												deleteLevelConfirm.dialog("close");
												if(response=="-2"){
													alert("Cannot Delete! Level Does not Exist");
												}
												else if(response=="-1"){
													alert("Error while trying to delete Level");
												}
												else{
													alert("Level deleted Successfully!");
												}
												$("#mainBody .subtabs").attr("id","rmSubtab4");
												$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
												$("#mainBody .subtabs").get(0).click();	
											},
											error : function(data) {	
												alert("Application error! Please call help desk. Error:"+data.status);
											}
										});
						          },
						          Cancel: function() {
						        	  deleteLevelConfirm.dialog("close");
						          }
						        }
						      });
							$("#deleteLevel").button().unbind("click").on("click", function() {
								var existingLevel = $("#existingLevel option:selected").val();
								if($("#existingLevel option:selected").val() == ""){
									alert('Please select Level to Delete');
									$("#existingLevel").focus();
									return false;
								}
								else{
									$("#deletedLevel").html(existingLevel+" will be permanently deleted and cannot be recovered.<br> Are you sure?");
									deleteLevelConfirm.dialog("open");
								}

							});
							
							
							var editSkillDialog = $("#editSkill-popup").dialog({
								autoOpen : false,
								height : 450,
								width : 550,
								modal : true,
								buttons : {
									"Edit Skill" : function() {
									if(validateNewSkillFields()){	
											$.ajax({
												type : "POST",
												url : "./editSkillForm.do",
												data : 
													$("#editSkillForm").serialize(),
													beforeSend : function() {
												},
												success : function(response) {
													if(response=="-2"){
														alert("Cannot Edit! Skill Already Exists");
													}
													else if(response=="-1"){
														alert("Error while trying to edit Skill");
													}
													else{
														alert("Skill Updated Successfully!");
													}
													
													$("#mainBody .subtabs").attr("id","rmSubtab4");
													$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
													$("#mainBody .subtabs").get(0).click();
													
													
												},
												error : function(data) {
													alert("Application error! Please call help desk. Error:"+data.error);
												},
												complete: function(data){
												}
											});
											editSkillDialog.dialog("close");
									}
								
									},
									Cancel : function() {
										editSkillDialog.dialog("close");
									},
								},

							});
							
							$("#editSkill").button().unbind("click").on("click", function() {	
								var existingCapability = $("#existingCapability option:selected").val();
								if($("#existingCapability option:selected").val() == 0){
									alert('Please select Capability under which Skill needs to be edited!');
									$("#existingCapability").focus();
									return false;
								}
								
								var existingSkill = $("#existingSkill option:selected").val();
								if($("#existingSkill option:selected").val() == 0){
									alert('Please select Skill to be edit!');
									$("#existingSkill").focus();
									return false;
								}
								
								$.ajax({
									type : "POST",
									url : "./viewSkillDetails.do",
									data : {
										existingCapability : existingCapability,
										existingSkill : existingSkill
									},
										beforeSend : function() {
									},
									success : function(response) {
										 $("#exisCapability").val(response.capabilityName);
										 $("#exisSkill").val(response.existingSkill);
										 $("#newSkillName").val(response.skillName);
										 editSkillDialog.dialog("open");
									},
									error : function(data) {
										alert("Application error! Please call help desk. Error:"+data.error);
									}
								});


							});
							
							
							var deleteSkillConfirm=$( "#deleteSkill-confirm" ).dialog({
						    	autoOpen : false,
						        height:150,
						        width:600,
						        modal: true,
						        buttons: {
						          "Delete Skill": function() {
						        	  var existingCapability = $("#existingCapability option:selected").val();
						        	  var existingSkill = $("#existingSkill option:selected").val();
										$.ajax({
											type : "POST",
											url : "./deleteSkill.do",
											data : {
												existingCapability : existingCapability,
												existingSkill : existingSkill
											},		
											beforeSend:function(){
											  },
											success : function(response) {
												deleteSkillConfirm.dialog("close");
												if(response=="-2"){
													alert("Cannot Delete! Skill Does not Exist");
												}
												else if(response=="-1"){
													alert("Error while trying to delete Skill");
												}
												else{
													alert("Skill deleted Successfully!");
												}
												$("#mainBody .subtabs").attr("id","rmSubtab4");
												$("#mainBody .subtabs").attr("action","./capabilitylevelskillmanagement.do");
												$("#mainBody .subtabs").get(0).click();	
											},
											error : function(data) {	
												alert("Application error! Please call help desk. Error:"+data.status);
											}
										});
						          },
						          Cancel: function() {
						        	  deleteSkillConfirm.dialog("close");
						          }
						        }
						      });
							$("#deleteSkill").button().unbind("click").on("click", function() {
								var existingCapability = $("#existingCapability option:selected").val();
								if($("#existingCapability option:selected").val() == 0){
									alert('Please select Capability under which Skill needs to be deleted!');
									$("#existingCapability").focus();
									return false;
								}
								
								var existingSkill = $("#existingSkill option:selected").val();
								if($("#existingSkill option:selected").val() == 0){
									alert('Please select Skill to be delete!');
									$("#existingSkill").focus();
									return false;
								}
								else{
									$("#deletedSkill").html(existingSkill+" will be permanently deleted and cannot be recovered.<br> Are you sure?");
									deleteSkillConfirm.dialog("open");
								}

							});
						
	 });
	
		$(document)
		.ready(
				function() {
						var addResourceDialog = $("#updateemp-popup").dialog({
						autoOpen : false,
						height : 550,
						width : 650,
						modal : true,
						buttons : {
							"Update Resource" : function() {
								
								if(validateFieldUpdate()){
									var level = $( "#resourceLevel option:selected" ).text();
									var capability = $( "#technicalCapability option:selected" ).text();
									var skill = $( "#technicalSkill option:selected" ).text();
									var empNumber = $("#empNumber").val();
									var empName = $("#empName").val();
									
									$("#level").val(level);
									$("#capability").val(capability);
									$("#skill").val(skill);
									alert("Employee Details Updated Sucessfully");
									$("#addEmpDetailsForm").submit();
									
							}
						
							},
							Cancel : function() {
								addResourceDialog.dialog("close");
							},
						},

					});
					
					$(".updateResource").unbind("click").on("click", function() {
						var employeeSAPId=$(this).attr("id");
						
						$.ajax({
							
							type : "POST",
							url : "./resourceManagementUpdate.do",
							data : 
								$("#addEmpDetailsForm").serialize(),
								beforeSend : function() {
							},
							success : function(response) {
								$("#updateemp-popup").html($(response).find("#updateemp-popup").html());
								loadDatepicker();
							},
							error : function(data) {

							},
							complete: function(data){
							}
						});
						addResourceDialog.dialog("open");
					});
					
					$("#resourceFileUpload").unbind("click").on("click",function(){
						/*$("#resourceFileUploadForm").attr("action","./upload.do");
						document.getElementById("resourceFileUploadForm").enctype = "multipart/form-data";
						$("#resourceFileUploadForm").submit();*/
						var fileName = $.trim($("#fileUploadAddress").val());

						if (fileName == '') {
							alert("Please select a file to upload.");
						} else {
							$("#resourceFileUploadForm").attr("action", "./upload.do");
							document.getElementById("resourceFileUploadForm").enctype = "multipart/form-data";
							$("#resourceFileUploadForm").submit();
						}
					});
					
 });
	
	
	function validateFieldAdd(){

			var level = $( "#addemp-popup #resourceLevel option:selected" ).text();
			var capability = $( "#addemp-popup #technicalCapability option:selected" ).text();
			var skill = $( "#addemp-popup #technicalSkill option:selected" ).text();

		if($("#addemp-popup #empNumber").val() == ''){
			alert("Please Enter Employee Number");
			return false;
		}else if($("#addemp-popup #empName").val() == ''){
			alert("Please Enter Employee Name");
			return false;
		}else if($("#addemp-popup #conNumber").val() == ''){
			alert("Please Enter Contact Number");
			return false;
		}else if($("#addemp-popup #emailID").val() == ''){
			alert("Please Enter Email ID");
			return false;
		}else if($("#addemp-popup #enterpriseId").val() == ''){
			alert("Please Enter Enterprise ID");
			return false;
		}else if($("#addemp-popup #role").val() == ''){
			alert("Please Enter Role");
			return false;
		}else if($("#addemp-popup #empStartDate").val() == ''){
			alert("Please Enter Project Roll On Date");
			return false;
		}else if($("#addemp-popup #empEndDate").val() == ''){
			alert("Please Enter Project Roll Off Date");
			return false;
		}else if(capability == 'Select Capability'){
			alert("Please Select Capability");
			return false;
		}else if( skill == 'Select Skill'){
			alert("Please Select Skill");
			return false;
		}else if(level == 'Select Level'){
			alert("Please Select Level");
			return false;
		}else if($("#addemp-popup #preLocation").val() == ''){
			alert("Please Enter Previous Location");
			return false;
		}else{
			return true;
		}
		
	}
	
	$("#existingCapability").unbind("change").on("change",function(){
		var existingCapability = $("#existingCapability option:selected").val();
		$.ajax({
			type : "POST",
			url : "./getSkill.do",
			data :{existingCapability:existingCapability} ,														
			beforeSend:function(){
			  },
			success : function(response) {
		        $('#existingSkill').empty();
		        $('#existingSkill').append('<option value="">Select Skill</option>');
		        for (i in response ) {
		            $('#existingSkill').append('<option value="' + response[i] + '">' + response[i] + '</option>');
		        }


			},
			error : function(data) {	
				alert("Application error! Please call help desk. Error:"+data.status);
			}
		});
	});
	
function validateFieldUpdate(){
		
	var level = $( "#updateemp-popup #resourceLevel option:selected" ).text();
	var capability = $( "#updateemp-popup #technicalCapability option:selected" ).text();
	var skill = $( "#updateemp-popup #technicalSkill option:selected" ).text();

		if($("#updateemp-popup #empNumber").val() == ''){
			alert("Please Select Employee Number");
			return false;
		}else if($("#updateemp-popup #empName").val() == ''){
			alert("Please Enter Employee Name");
			return false;
		}else if($("#updateemp-popup #conNumber").val() == ''){
			alert("Please Enter Contact Number");
			return false;
		}else if($("#updateemp-popup #emailID").val() == ''){
			alert("Please Enter Email ID");
			return false;
		}else if($("#updateemp-popup #enterpriseId").val() == ''){
			alert("Please Enter Enterprise ID");
			return false;
		}else if($("#updateemp-popup #role").val() == ''){
			alert("Please Enter Role");
			return false;
		}else if($("#updateemp-popup #empStartDate").val() == ''){
			alert("Please Enter Project Roll On Date");
			return false;
		}else if($("#updateemp-popup #empEndDate").val() == ''){
			alert("Please Enter Project Roll Off Date");
			return false;
		}else if(capability == 'Select Capability'){
			alert("Please Select Capability");
			return false;
		}else if( skill == 'Select Skill'){
			alert("Please Select Skill");
			return false;
		}else if(level == 'Select Level'){
			alert("Please Select Level");
			return false;
		}else if($("#updateemp-popup #preLocation").val() == ''){
			alert("Please Enter Previous Location");
			return false;
		}else{
			return true;
		}
		
	}
	
	function loadDatepicker(){
		$( "#empStartDate" ).datepicker({
			showOn: 'button',
			buttonText: 'Show Date',
			buttonImageOnly: true,
			buttonImage: 'resources/cal.gif',
			dateFormat: 'mm/dd/yy',
			constrainInput: true,
			
		}); 
		 $( "#empEndDate" ).datepicker({
					showOn: 'button',
					buttonText: 'Show Date',
					buttonImageOnly: true,
					buttonImage: 'resources/cal.gif',
					dateFormat: 'mm/dd/yy',
					constrainInput: true,
					
		});
	}
	
	function confirmSucess(){
		$('#sucess_msg_div').dialog("close");
	}
	
	function resourceCheck(){
		$.ajax({
			type : "POST",
			url : "./checkResource.do",
			data : 
				$("#addEmpDetailsForm").serialize(),
				beforeSend : function() {
			},
			success : function(response) {
				var resFlag= $(response).find("#resourceFlag").val();
				if(resFlag == null || resFlag == ''){
				}else{
									
					$('#sucess_msg_div').dialog("open");
				}
				
			},
			error : function(data) {

			},
			complete: function(data){
				
			}
		});
			
	}
	
	function validateCapabilityFields(){
		if($("#capabilityName").val() == ''){
			alert("Please Enter Capability Name");
			return false;
		}else{
			return true;
		}
		
	}
	
	function validateLevelFields(){
		if($("#levelName").val() == ''){
			alert("Please Enter Level Name");
			return false;
		}
		else{
			return true;
		}
		
	}
	
	function validateSkillFields(){
		if($("#skillCategory").val() == ''){
			alert("Please Enter Skill Category Speciality");
			return false;
		}
		else if($("#skillName").val() == ''){
			alert("Please Enter Skill Name");
			return false;
		}else{
			return true;
		}
		
	}
	
	function validateNewCapabilityFields(){
		if($("#newCapabilityName").val() == ''){
			alert("Please Enter New Capability Name");
			return false;
		}else{
			return true;
		}
		
	}
	
	
	function validateNewLevelFields(){
		if($("#newLevelName").val() == ''){
			alert("Please Enter New Level Name");
			return false;
		}else{
			return true;
		}
		
	}

	function validateNewSkillFields(){
		if($("#newSkillName").val() == ''){
			alert("Please Enter New Skill Name");
			return false;
		}else{
			return true;
		}
		
	}

	
	
