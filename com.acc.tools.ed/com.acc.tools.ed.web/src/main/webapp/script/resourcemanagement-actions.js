		$(document)
			.ready(
					function() {
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

	
	
