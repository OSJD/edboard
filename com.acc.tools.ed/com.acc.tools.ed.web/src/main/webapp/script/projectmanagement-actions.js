	$(document)
			.ready(
					function() {
						
						/*
						 * Date Format :yyyy-mm-dd
						 */
						Date.prototype.yyyymmdd = function() {         
					        
					        var yyyy = this.getFullYear().toString();                                    
					        var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
					        var dd  = this.getDate().toString();             
					                            
					        return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0]);
					   }; 
					   
						/**
						 * Activate subtab1 on main tab 'Project Plan' active
						 */
						$('#subtab1').parent().addClass('subtab-active');
						$('#subtab1').css({
							"pointer-events": "none",
							"cursor": "default"
						});
						
						/*
						 * Add New Project Dialog box
						 */
						var addProjectDialog = $("#addproject-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
							modal : true,
							buttons : {
								"Add Project" : function() { 
									
									var progName=$("#programs option:selected").text();
									var progId=$("#programs").val();
									var progName1=$("#program").val();
									if(progId == "NONE" && progName1 == ''){
										alert("Please select Program!");
									} else if (progName == progName1) {
										alert("Program already exist!");
									} else if ((progId == "NONE" && progName != progName1) || progId != "NONE") {
										if($("#newProgramName:empty").length > 0 && $("#existingProgram").val()=='-1'){
											var newProgramId=generateId("existingProgram")+1;
											$("#newProgramId").val(newProgramId);
										}
										var newProjectId=generateId("projects")+1;
										$("#projectId").val(newProjectId);
										$("#addProjectForm").submit();																		
									}
									
									},
								Cancel : function() {
									addProjectDialog.dialog("close");
								},
							},

						});

						$("#addProject,#editProject,#deleteProject").button().unbind("click").on("click", function() {
							$.ajax({
								type : "GET",
								url : "./fetchInitialProjectSetupDetails.do",
								dataType:'json',
								success : function(response) {
									$.each(response, function(outerKey, outerValue){
									   if(outerKey=='resourceList'){
										    $.each(outerValue, function(key, value){
										       $('#stringResources').append('<option value="'+value.id+'">'+value.label+'</option>');
										    });
									   }
									   if(outerKey=='programList'){
										    $.each(outerValue, function(key, value){
										       $('#existingProgram').append('<option value="'+value.id+'">'+value.label+'</option>');
										    });
									   }
									   if(outerKey=='projectLeadList'){
										    $.each(outerValue, function(key, value){
										       $('#projectLead').append('<option value="'+value.id+'">'+value.label+'</option>');
										    });
									   }
									   
									});
									addProjectDialog.dialog("open");
								},
								error : function(data) {	
									$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
								}
							});	
						
						});
						
						
						/*
						 * Add New Release Dialog box
						 */
						var addReleaseDialog = $("#addrelease-popup").dialog({
							autoOpen : false,
							height : 700,
							width : 1100,
							modal : true,
							buttons : {
								"Add Release" : function() { 
									var releaseIdCount=generateId("releases")+1;
									var projectId=$("#projectId").val();
									var releaseForm = $('#addReleaseForm').serializeArray();
									var resources = $("td[id^='resource']").length;
									var days = $("input[id^='resDayHour']").length/resources;
									var jsonString = "{";
									$.each(releaseForm,
									    function(i, v) {
										 if(v.name=="releaseStartDate" || v.name=="releaseEndDate"){																															
											jsonString=jsonString+" \""+v.name+"\":\""+$("input[id^='"+v.name+"']").val()+"\",";
										} else {
											jsonString=jsonString+" \""+v.name+"\":\""+v.value+"\",";
										}
									 });
									jsonString=jsonString+"\"resourcesAndHours\" : {";
									
									for(var i=0; i<resources;i++){										
										jsonString=jsonString+"\""+$("#resId"+i).val()+"\": [";
										for(var j=0;j<days;j++){
											if(i==(resources-1) && j==(days-1)){
												jsonString=jsonString+"\""+$("#resDayHour"+i+j).val()+"\" ] }," ;
											}
										    else if(j==(days-1)){
											 jsonString=jsonString+"\""+$("#resDayHour"+i+j).val()+"\" ]," ;
											}
											else{
												jsonString=jsonString+"\""+$("#resDayHour"+i+j).val()+"\"," ;
											}											
										}
										
									}

									jsonString=jsonString+"\"releaseId\":\""+releaseIdCount+"\",";
									jsonString=jsonString+"\"projectId\":\""+projectId+"\"";
									jsonString=jsonString+"}";
									
									$.ajax({
										type : "POST",
										url : "./addRelease.do",
										data :jsonString ,												
										contentType : 'application/json; charset=utf-8',
										dataType : 'json',		
										beforeSend:function(){
										  },
										success : function(response) {
											$('#releases').append('<option selected value="'+response.id+'">'+response.label+'</option>').change();
											
											addReleaseDialog.dialog("close");		
										},
										error : function(data) {	
											$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
										}
									});	
								},
								Cancel : function() {
									addReleaseDialog.dialog("close");
								}
							},

						});

						$("#addRelease,#editRelease,#deleteRelease").button().unbind("click").on("click", function() {
							var prjName=$("#projects option:selected").text();
							var projectId = $("#projects").val();
							if(projectId=="0"){
								alert("Please select Project!");
							} else {
								$("#projName").html(prjName);
								$("#projectId").val(projectId);
								$.ajax({
									type : "POST",
									url : "./getPrjDate.do",
									data : {projectId:projectId},									
									dataType : 'json',		
									success : function(response) {
										var projStartDate=null;
										var projEndDate=null;
										$.each(response, function(projDateKey, projDateValue){
											
											if(projDateKey=='projectStartDate'){
												projStartDate=new Date(projDateValue);
												$("#projStartDate").html(projDateValue);
											}
											if(projDateKey=='projectEndDate'){
												projEndDate=new Date(projDateValue);
												$("#projEndDate").html(projDateValue);
											}
											
										});
										
										$( "#releaseStartDate" ).datepicker({
											showOn: 'button',
											buttonText: 'Show Date',
											buttonImageOnly: true,
											buttonImage: 'resources/cal.gif',
											dateFormat: 'dd/mm/yy',
											constrainInput: true,
											minDate:projStartDate,
											maxDate:projEndDate
										 }); 
										
										$( "#releaseEndDate" ).datepicker({
											showOn: 'button',
											buttonText: 'Show Date',
											buttonImageOnly: true,
											buttonImage: 'resources/cal.gif',
											dateFormat: 'dd/mm/yy',
											constrainInput: true,
											minDate:projStartDate,
											maxDate:projEndDate
										});
									},
									error : function(data) {	
										$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
									}
								});	
								
								addReleaseDialog.dialog("open");
							}
						});
						
						//Create release plan
						$("#createReleasePlan").unbind("click").on("click",function(){
							var releaseStDt=$("#releaseStartDate").val();
							var releaseEndDt=$("#releaseEndDate").val();
							var projId = $("#projects").val();
							$.ajax({
								url : "./createReleasePlan.do",
								data : "releaseStartDate="+releaseStDt+"&releaseEndDate="+releaseEndDt+"&projId="+projId,									
								success : function(response) {
									$('#release tr:last').after(response);
								},
								error : function(data) {	
									$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
								}
							});	
						});
						
						/*
						 * On selecting a project from drop down, 
						 * this function will fetch the corresponding releases.
						 */
						
						$("#projects").unbind("change").on("change", function() {
							var selectedProject=$("#projects").val();
							$.ajax({
								type : "POST",
								url : "./fetchReleases.do",
								data : {projectId:selectedProject},
								dataType : 'json',		
								beforeSend:function(){
								  },
								success : function(response) {
						
									$('#releases')
									  	.find('option')
									  	.remove();
									$('#releases').append('<option value="SR">Select Release</option>');
									for(var obj in response){
										$('#releases').append('<option value='+response[obj].id+'>'+response[obj].label+'</option>');
									};
								},
								error : function(data) {	
									$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
								}
							});	
						});
						
						/*
						 * On selecting a release from drop down, 
						 * this function will fetch Project and Release details.
						 */
						
						$("#releases").unbind("change").on("change", function() {
							var selectedProject=$("#projects").val();
							var selectedRelease=$("#releases").val();
							var jsonRequest="{ \"projectId\" : \""+selectedProject+"\", \"releaseId\" : \""+selectedRelease+"\"}";
							$.ajax({
								type : "POST",
								url : "./viewProjectReleaseDetails.do",
								data : jsonRequest,
								contentType : 'application/json; charset=utf-8',
								beforeSend:function(){
								  },
								success : function(response) {
									$("#viewProjectAndReleaseDetails").html(response);
								},
								error : function(data) {	
									$("#mainContainer").html("Application error! Please call help desk.  Error:"+data.status);
								}
							});	
						});
						
						$("#existingProgram").unbind("change").on("change",function(){
							if($("#existingProgram").val()=='-1'){
								$("#newProgram").css("display", "inline");
							} else {
								$("#newProgram").css("display", "none");
							}
						});
						
						
					});

	/*
	 * This function will be to update resource excel file into the system.
	 * FormData will work only in IE >=10
	 */
	
	function uploadResourceFile(){
		$('#result').html('');

		//Callback handler for form submit event
		$("#resourceFileUploadForm").submit(function(e){
		    var formObj = $(this);
		    var formURL = formObj.attr("action");
		    var formData = new FormData(this);
		    $.ajax({
		        url: formURL,
		        type: 'POST',
		        data:  formData,
		        mimeType:"multipart/form-data",
		        contentType: false,
		        cache: false,
		        processData:false,
		        success: function(data, textStatus, jqXHR){
		        	$('#result').html(data);
		        },
		        error: function(jqXHR, textStatus, errorThrown){
		        	$('#result').html('Error uploading resource file.'+textStatus);
		        }         
		    });
		    e.preventDefault(); //Prevent Default action.
		    e.unbind();
		});
		$("#resourceFileUploadForm").submit(); //Submit the form
	}
	
	function generateId(id){
		var projectid=0;
			$("#"+id+" > option").each(function() {
				var tempValue=parseInt(this.value);
				if(projectid<tempValue){
					projectid=tempValue;
				}
			});
			return projectid;
	}

