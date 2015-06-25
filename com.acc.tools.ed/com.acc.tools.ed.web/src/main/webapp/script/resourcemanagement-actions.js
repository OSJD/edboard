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
						
						var addCapabilityDialog = $("#addCapability-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
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
												alert("Capability Name/Specialty Already Exists");
											}
											else if(response=="-1"){
												alert("Error while trying to add Capability");
											}
											else{
												alert("Capability Added Successfully!");
											}
										},
										error : function(data) {

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

								},
								complete: function(data){
								}
							});
							addCapabilityDialog.dialog("open");
						});
						
						
						var addLevelDialog = $("#addLevel-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
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
											},
											error : function(data) {

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

								},
								complete: function(data){
								}
							});
							addLevelDialog.dialog("open");
						});
						
						
						var addSkillDialog = $("#addSkill-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
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
											},
											error : function(data) {

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
							$.ajax({
								type : "POST",
								url : "./capabilitylevelskillmanagement.do",
								data : 
									$("#addSkillForm").serialize(),
									beforeSend : function() {
								},
								success : function(response) {
									$("#addSkill-popup").html($(response).find("#addSkill-popup").html());
								},
								error : function(data) {

								},
								complete: function(data){
								}
							});
							addSkillDialog.dialog("open");
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
	
	function validateCapabilityFields(){
		if($("#capabilityName").val() == ''){
			alert("Please Enter Capability Name");
			return false;
		}else if($("#capabilitySpecialty").val() == ''){
			alert("Please Enter Capability Speciality");
			return false;
		}else{
			return true;
		}
		
	}
	
	function validateLevelFields(){
		if($("#LevelName").val() == ''){
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

	
	
