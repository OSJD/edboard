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
									if(validateField()){
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
							$("#resourceFileUploadForm").attr("action","./upload.do");
							document.getElementById("resourceFileUploadForm").enctype = "multipart/form-data";
							$("#resourceFileUploadForm").submit();
						});
						
	 });
	
	
	
	function validateField(){
		var level = $( "#resourceLevel option:selected" ).text();
		var capability = $( "#technicalCapability option:selected" ).text();
		var skill = $( "#technicalSkill option:selected" ).text();
		if($("#empNumber").val() == ''){
			alert("Please Enter Employee Number");
			return false;
		}else if($("#empName").val() == ''){
			alert("Please Enter Employee Name");
			return false;
		}else if($("#conNumber").val() == ''){
			alert("Please Enter Contact Number");
			return false;
		}else if($("#emailID").val() == ''){
			alert("Please Enter Email ID");
			return false;
		}else if($("#enterpriseId").val() == ''){
			alert("Please Enter Enterprise ID");
			return false;
		}else if($("#role").val() == ''){
			alert("Please Enter Role");
			return false;
		}else if($("#empStartDate").val() == ''){
			alert("Please Enter Project Roll On Date");
			return false;
		}else if($("#empEndDate").val() == ''){
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
		}else if($("#preLocation").val() == ''){
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
			/*minDate:$("#relStartDate").html(),
			maxDate:$("#relEndDate").html()*/ 
		}); 
		 $( "#empEndDate" ).datepicker({
					showOn: 'button',
					buttonText: 'Show Date',
					buttonImageOnly: true,
					buttonImage: 'resources/cal.gif',
					dateFormat: 'mm/dd/yy',
					constrainInput: true,
					/* minDate:$("#relStartDate").html(),
					maxDate:$("#relEndDate").html()*/ 
		});
	}

	
	
