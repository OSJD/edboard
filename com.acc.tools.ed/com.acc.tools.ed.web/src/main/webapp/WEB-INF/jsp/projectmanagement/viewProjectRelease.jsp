<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<script>
	 $(document).ready(function () {
		  
		 $( "#compEndDate" ).datepicker(); 
		 $( "#compStartDate" ).datepicker(); 
			
	 
		 $( "#editRelStartDate" ).datepicker({
			 
			 beforeShow: function (input, inst) {
				 var dt2 = $('#editRelStartDate').datepicker('getDate');
	             $('#editRelStartDate').datepicker('option', 'minDate', dt2);
	         }
		 });
		 
		 $( "#editRelEndDate" ).datepicker({
			 
			 beforeShow: function (input, inst) {
				 var dt2 = $('#editRelStartDate').datepicker('getDate');				 
	             $('#editRelEndDate').datepicker('option', 'minDate', dt2);
	         }
		 });
		 
		 $( "#editPrjStartDate" ).datepicker({
			 
			 beforeShow: function (input, inst) {
				 var dt2 = $('#editPrjStartDate').datepicker('getDate');
				 $('#editPrjStartDate').datepicker('option', 'minDate', dt2);
	         }
		 });
		 
		 $( "#editPrjEndDate" ).datepicker({
			 
			 beforeShow: function (input, inst) {
				 var dt2 = $('#editPrjStartDate').datepicker('getDate');				 
	             $('#editPrjEndDate').datepicker('option', 'minDate', dt2);
	         }
		 });
				 
		 $("#editproject-popup").dialog({ 
			 autoOpen: false,
			 height : 450,
			 width : 650,
			 modal : true,
			 buttons : {
					"Edit Project" : function() { 
						
						var selectedProject=$("#projects").val();
						var startDate=$("#editPrjStartDate").val();
						var desc=$("#editPrjDesc").val();
						var endDate=$("#editPrjEndDate").val();						
												
						$.ajax({
							type : "POST",
							url : "./editProject.do",
							data : {projectId:selectedProject,editPrjStartDate:startDate,editPrjEndDate:endDate,editPrjDesc:desc},												
							dataType : 'json',		
							beforeSend:function(){
							  },
							success : function(response) {
								
								for(var obj in response){
									$('#prjDesc').html(response[obj].editPrjDescResp);
									$('#prjStartDate').html(response[obj].editPrjStartDateResp);
									$('#prjEndDate').html(response[obj].editPrjEndDateResp);
									$('#editproject-popup').dialog("close");	
								}
							},
							error : function(data) {	
								$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
							}
						});	
					},
					Cancel : function() {
						$("#editproject-popup").dialog("close");
					},
				},

		 });
		 
		 
		 $("#editrelease-popup").dialog({ 
			 autoOpen: false,
			 height : 300,
			 width : 650,
			 modal : true,
			 buttons : {
					"Edit Release" : function() { 
						
						var selectedRelease=$("#releases").val();
						var startDate=$("#editRelStartDate").val();
						var desc=$("#editRelArti").val();
						var endDate=$("#editRelEndDate").val();						
						
						$.ajax({
							type : "POST",
							url : "./editRelease.do",
							data : {releaseId:selectedRelease, releaseEdDate:endDate, editRelStartDate:startDate, editRelArti:desc},												
							dataType : 'json',						
							success : function(response) {
								
								for(var obj in response){
									$('#relArti').html(response[obj].editRelArtiResp);
									$('#relStartDate').html(response[obj].editRelStartDateResp);
									$('#relEndDate').html(response[obj].editRelEndDateResp);
								}
								$('#editrelease-popup').dialog("close");
							},
							error : function(data) {	
								$('#editrelease-popup').dialog("close");
								$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
							}
						});	
					},
					Cancel : function() {
						$("#editrelease-popup").dialog("close");
					},
				},

		 });
		 
		 $("#delPrj-popup").dialog({ 
			 autoOpen: false,
			 height : 150,
			 width : 350,
			 modal : true,
			 buttons : {
					"Delete" : function() { 
						var selectedProject=$("#projects").val();
						
						$.ajax({
							type : "POST",
							url : "./deleteProject.do",
							data : {projectId:selectedProject},												
							dataType : 'json',							
							error : function(data) {
								if(data.status == 200) {
									$("#delPrj-popup").dialog("close");
									window.location.reload();
								}
							}
						});	
					},
					Cancel : function() {
						$("#delPrj-popup").dialog("close");
					},
				},

		 });
		 
		 $("#delRel-popup").dialog({ 
			 autoOpen: false,
			 height : 150,
			 width : 350,
			 modal : true,
			 buttons : {
					"Delete" : function() { 
						var selectedRelease=$("#releases").val();
						
						$.ajax({
							type : "POST",
							url : "./deleteRelease.do",
							data : {releaseId:selectedRelease},												
							dataType : 'json',							
							error : function(data) {
								if(data.status == 200) {
									$("#delRel-popup").dialog("close");
									window.location.reload();
								}
								//$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
							}
						});	
					},
					Cancel : function() {
						$("#delRel-popup").dialog("close");
					},
				},

		 });
		 
		 $("#editPrj").click(function() {
			 $("#editproject-popup").dialog('open');   
		});
		 
		$("#editRel").click(function() {
			 $("#editrelease-popup").dialog('open');   
		});
		
		$("#delPrj").click(function() {
			 $("#delPrj-popup").dialog('open');   
		});
		
		$("#delRel").click(function() {
			 $("#delRel-popup").dialog('open');   
		});
		
		
		
		$("#addNewCompnt").unbind("click").on("click", function() {
			
  			var lComponentName = $("#componentName").val();
 			if($("#newComponent").is(":visible") && lComponentName == "0") {
				lComponentName = $("#newComponent").val();
			} 
 			var lFunctionalDesc= $("#functionalDesc").val();
			var lCompStartDate = $("#compStartDate").val();
			var lCompEndDate = $("#compEndDate").val();
			var lCompResource = $("#compResourceList option:selected").val();
			var lProjectId = $("#projects").val();
			var lselectedRelease=$("#releases").val();
			var lphaseId=$("#componentPhase").val();
			var lworkDesc=$("#workDesc").val();
			$.ajax({
				type : "POST",
				url : "./addComponent.do",
				data : {componentName:lComponentName,
						functionalDesc:lFunctionalDesc,
						compStartDate:lCompStartDate,
						compEndDate:lCompEndDate,
						compResource:lCompResource,
						projectId:lProjectId,
						releaseId:lselectedRelease,
						phaseId:lphaseId,
						workDesc:lworkDesc},
				dataType : 'json',
				success : function(response) {
					var tableResp = '';
					/* for(var obj in response) {
						tableResp = '<tr><td width="165px;" id="compName">'+response[obj].componentName+'</td>'+
						'<td width="160px"></td>'+
						'<td width="385px;" id="compFuncDesc"><div style="height:20px;display:table-cell;vertical-align:middle;">'+response[obj].functionalDesc+'</div></td>'+
						'<td width="100px;" id="comStDate">'+response[obj].startDate+'</td>'+
						'<td width="100px;" id="compEtDate">'+response[obj].endDate+'</td>'+
						'<td width="80px"></td>'+
						'<td width="80px">% </td>'+
						'<td width="180px;" id="compResName">'+response[obj].resourceName+'</td>'+
						'<td width="295px">'+response[obj].workDesc+'</td>'+
						'<td><img alt="edit project" src="./resources/edit.gif"	width="20px;"></td>'+
						'<td><img alt="delete project" src="./resources/delete.gif"	width="20px;"></td></tr>';
					}
					
					 */
					if($('#children').length){
						$('#componentTable > tbody:first').append(tableResp);	
					} else {
						$("#noComponetMsg").hide();
						$('#componentTable').append('<tbody>'+tableResp+'</tbody>');
					}
					$("#releases").trigger("change");
					
				},
				error : function(data) {	
					$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
				}
			});	  
		 });
		
		$("#componentName").unbind("change").on("change",function(){
			if($("#componentName").val()=='0'){
				$("#newComp").css("display", "inline");
				$("#compStartDate").removeAttr('disabled');
				$("#compEndDate").removeAttr('disabled');
				$('#compStartDate').val('');
				$('#compEndDate').val('');
			} else {
				$("#newComp").css("display", "none");
			}
			$('#componentPhase').val(0);
		});
		
		$("#componentPhase").unbind("change").on("change",function(){
			var compName = $("#componentName").val();
			var compPhase = $("#componentPhase").val();
			var compRelease=$("#releases").val();
			$.ajax({
				type : "POST",
				url : "./getCompDetails.do",
				data : {cmpName:compName,
						cmpPhase:compPhase,
						cmpRelease:compRelease,
						},												
				dataType : 'json',
				success : function(response) {
						if (response[0] != 0) {
						$('#compStartDate').val(response[1]);
						$("#compStartDate").attr('disabled','disabled');
						$('#compEndDate').val(response[2]);
						$("#compEndDate").attr('disabled','disabled');
						$('#functionalDesc').val(response[3]);
						$("#functionalDesc").attr('disabled','disabled');
						
					} else {
						$("#compStartDate").removeAttr('disabled');
						$("#compEndDate").removeAttr('disabled');
						$("#functionalDesc").removeAttr('disabled');
						$('#compStartDate').val('');
						$('#compEndDate').val('');
						$('#functionalDesc').val('');
					}
				}
		});
	});
		
		$("#compResourceList").unbind("change").on("change",function(){
			var selCompName=$("#componentName").val();
			var selCompPhase=$("#componentPhase  option:selected").text();
			var selResource=$("#compResourceList  option:selected ").text();
			
			$("#componentTable tr").each(function () {
		        var this_row = $(this);
		        var tabCompName = $.trim(this_row.find('td:eq(0)').html());
		        var tabCompPhase = $.trim(this_row.find('td:eq(1)').html());
		        var tabResource = $.trim(this_row.find('td:eq(7)').html());
		        
		        if(selCompName==tabCompName && selCompPhase==tabCompPhase && selResource==tabResource){
		        	alert(tabResource+" is already working on "+selCompPhase +" of component "+ selCompName);
					$('#compResourceList').val(0);
		        }
		    });
			
		});
		
		 var usedNames = {};
		 $("#edbCompTable tr:nth-child(2) td:nth-child(1) select > option").each(function () {
		   
		 	if (usedNames[this.value]) {
		       $(this).remove();
		 	} else {
		       usedNames[this.value] = this.text;
		 	}
		});
});
	 
	 function setCharAt(str,index,chr) {
			if(index > str.length-1) return str;
			return str.substr(0,index) + chr + str.substr(index+1);
		}

	</script>
</head>
<form id="projectForm">

<table>
	<tr>
		<td style="width: 555px;">
			<table class="ebdtableheader" style="width: 99%;">
				<tr>
					<td style="font-weight: bold;">Lead</td>
					<td style="background-color: #eaead9;">${viewProjRelDetails.projectLead}</td>
				</tr>
				<tr>
					<td style="font-weight: bold;width: 100px;">Description</td>
					<td style="background-color: #eaead9;width: 220px;height: 35px; overflow: auto;"><div id="prjDesc">${viewProjRelDetails.projectDescription}</div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">Phase</td>
					<td style="background-color: #eaead9;width: 75px; overflow: auto;">
						<c:forEach var = "phase" items="${viewProjRelDetails.phases}">
									<c:choose>
										<c:when test="${phase.trim() =='1'}">
											Analysis,
										</c:when>
										<c:when test="${phase.trim() =='2'}">
											Design,
										</c:when>
										<c:when test="${phase.trim() =='3'}">
											Build,
										</c:when>
										<c:when test="${phase.trim() =='4'}">
											Test
										</c:when>
										<c:otherwise>
											Support
										</c:otherwise>
									</c:choose>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold;">Start Date</td>
					<td style="background-color: #eaead9;"><div id="prjStartDate"><joda:format
							value="${viewProjRelDetails.startDate}" pattern="MM/dd/yyyy" /></div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">End Date</td>
					<td style="background-color: #eaead9;"><div id="prjEndDate"><joda:format
							value="${viewProjRelDetails.endDate}" pattern="MM/dd/yyyy" /></div></td>
				</tr>
			</table>
		</td>
		<td style="width: 650px;">
			<table class="ebdtableheader" style="width: 99%">
				<tr>
					<td style="font-weight: bold;;width: 150px;">Release Artifacts</td>
					<td style="background-color: #eaead9; width: 350px;height: 35px; overflow: auto;">
						<div id="relArti">${viewProjRelDetails.releases[0].releaseArtifacts}</div>
					</td>
				</tr>
				<tr>		
					<td style="font-weight: bold;">Start Date</td>
					<td style="background-color: #eaead9;"><div id="relStartDate">
							${viewProjRelDetails.releases[0].releaseStartDate}</div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">End Date</td>
					<td style="background-color: #eaead9;">
							<div id="relEndDate">${viewProjRelDetails.releases[0].releaseEndDate}</div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">Planned Hours</td>
					<td style="background-color: #eaead9;"><div id="plannedHrs"></div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">Actual Hours</td>
					<td style="background-color: #eaead9;">145 Hr by 01/01/2015(Week 2)</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div style="width: 1205px;overflow: auto;">
	<table class="ebdtable" id = "edbCompTable" style="width:100%;margin-top: 25px;">
		<tr style="width:100%">
			<th style="width: 160px;">Component Name</th>
			<th style="width: 160px;">Component Phase</th>
			<th style="width: 295px;">Functional Desc</th>
			<th style="width: 80px;">Start Date</th>
			<th style="width: 80px;">End Date</th>
			<th style="width: 80px;">Status</th>
			<th style="width: 80px;">% Completed</th>
			<th style="width: 150px;">Resource</th>
			<th style="width: 295px;">Work Description</th>
			<th colspan="2" style="width: 25px;"></th>
		</tr>
		<tr style="width:100%">
			<td>
				<select name="componentName" id = "componentName">
					<option value="-1">---Select Component---</option>
					<c:forEach var="component" items="${viewProjRelDetails.releases[0].components}">
					    <c:if test="${not empty component.componentName}">
							<option value="${component.componentName}" id="compName">${component.componentName}</option>
						</c:if>
					</c:forEach>
					<option value="0">Create New Component</option>
				</select>
				<div id="newComp" class="textbox" style="display:none"><input name="newComponent" id="newComponent" type="text"></div>
			</td>
			<td>
				<select name="componentPhase" id="componentPhase">
					<option value="0">Select Phase</option>
					<c:forEach var = "phase" items="${viewProjRelDetails.phases}">
						<c:choose>
							<c:when test="${phase.trim() =='1'}">
								<option value="1">Analysis</option>
							</c:when>
							<c:when test="${phase.trim() =='2'}">
								<option value="2">Design</option>
							</c:when>
							<c:when test="${phase.trim() =='3'}">
								<option value="3">Build</option>
							</c:when>
							<c:when test="${phase.trim() =='4'}">
								<option value="4">Test</option>
							</c:when>
							<c:otherwise>
								<option value="5">Support</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</td>
			<td><textarea id="functionalDesc" style="overflow: auto; resize: none" rows="6"
					cols="32" class="textarea"></textarea></td>
			<td><input type="text" id ="compStartDate" name="compStartDate" class="textbox" /></td>
			<td><input type="text" id="compEndDate" name="compEndDate" class="textbox" /></td>
			<td><input type="text" id="componentStatus" name="componentStatus" class="textbox" disabled = "disabled" value = "Not Started"/></td>
			<td><input type="text" id="percent" name="percent" class="textbox" disabled = "disabled" value = "0%"/></td>
			<td><select id = "compResourceList" name="compResourceList" class="textbox" >
				<option value="0">Select Resource</option>		
				<c:forEach items="${viewProjRelDetails.resources}" var="resource">
				        <option value="${resource.id}" <c:if test="${resource.selected==true}">selected</c:if> >${resource.label}</option>
				 </c:forEach>
			</select></td>
			<td><textarea id="workDesc" style="overflow: auto; resize: none" rows="6"
					cols="32" class="textarea"></textarea></td>
			<td colspan="2"><a href="#" id="addNewCompnt"><img class="imgLink"
					alt="add comnponent" src="./resources/addnews.gif" width="20px;"></a></td>		
		</tr>
	</table>
	<table class="innertable2" id="componentTable"
		style="border-width: 1px; border-style: solid; border-color: #999999;">
		<c:choose>	
	        <c:when test="${empty viewProjRelDetails.releases[0].components}">
				<div id="noComponetMsg" class="boxmsg border-boxmsg" style="width: 780px;color: red;">
				    <p>No <u>Components/Tasks</u> , <u>Change Requests(CR)</u> or <u>Defects</u> are configured for Project :<u>${viewProjRelDetails.projectName}</u> and Release :<u>${viewProjRelDetails.releases[0].releaseName}</u> .<br>
				    Please add <u>Component/Task/CR/Defect</u> and assign a resource accordingly using the above form.
				    </p>
				    <b class="border-notch notch"></b>
				    <b class="notch"></b>
				</div>
	        </c:when>
	        <c:otherwise>
	        	<c:forEach var="component" items="${viewProjRelDetails.releases[0].components}">
					<tr>
						<td width="160px;" id="compName">${component.componentName}</td>
						<td width="160px">
						<c:forEach var = "cphase" items="${component.phaseId}">
									<c:choose>
										<c:when test="${cphase.trim() =='1'}">
											Analysis
										</c:when>
										<c:when test="${cphase.trim() =='2'}">
											Design
										</c:when>
										<c:when test="${cphase.trim() =='3'}">
											Build
										</c:when>
										<c:when test="${cphase.trim() =='4'}">
											Test
										</c:when>
										<c:otherwise>
											Support
										</c:otherwise>
									</c:choose>
						</c:forEach></td>
						<td width="295px;" id="compFuncDesc"><div style="height:20px;display:table-cell;vertical-align:middle;">${component.functionalDesc}</div></td>
						<td awidth="80px;" id="comStDate">${component.startDate}</td>
						<td width="80px;" id="compEtDate">${component.endDate}</td>
						<td width="80px">Not Started</td>
						<td width="80px" align="center">0 % </td>
						<td width="150px;" id="compResName">${component.resourceName}</td>
						<td width="295px">${component.workDesc}</td>
						<td><img alt="edit project" src="./resources/edit.gif"
							width="20px;"></td>
						<td><img alt="delete project" src="./resources/delete.gif"
							width="20px;"></td>
					</tr>
				</c:forEach>
	        </c:otherwise>
	    </c:choose>
	</table>
</div>
	<div id="editproject-popup" title="Edit Project Details">
		<p class="validateTips">All form fields are required.</p>
			<fieldset>
				<legend>Edit Project</legend>
				<div>
					<table class="ebdtable">
						<tr>
							<th style="text-align: right; height: 25px;">Task Name</th>
							<td><input type="text" id="taskName" name="taskName" value="" /></td>
							<th style="text-align: right;">Task Description</th>
							<td><input type="text" id="taskDesc" name="taskDesc" value="" /></td>
						</tr>
						<tr>
							<th style="text-align: right;">Phase</th>
							<td  colspan="3">
								<c:forEach items="${phaseList}" var="i">
									 <input type="checkbox" id="editPrjPhase">
									 	<option value="${i}">${i}</option>
									 </input>
								</c:forEach>
								</input>	
							</td>
						</tr>
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><input type="text" id="editPrjStartDate" name="editPrjStartDate" value="${viewProjRelDetails.releases[0].releaseStartDate}" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td><input type="text" id="editPrjEndDate" name="editPrjEndDate" value="${viewProjRelDetails.releases[0].releaseEndDate}" class="textbox" /></td>
						</tr>
					</table>
				</div>				
			</fieldset>
	</div>
	<div id="editrelease-popup" title="Edit Release Details">
		<p class="validateTips">All form fields are required.</p>
			<fieldset>
				<legend>Edit Release</legend>
				<div>
					<table class="ebdtable">
						<tr>
							<th style="text-align: right; height: 25px;">Release Name</th>
							<td>${viewProjRelDetails.releases[0].releaseName}</td>
							
							<th style="text-align: right;">Release Artifacts</th>
							<td  colspan="3">
								<textarea style="overflow: auto; resize: none" rows="4" 
									cols="40" class="textarea" id="editRelArti" name="editRelArti" value="${viewProjRelDetails.releases[0].releaseArtifacts}"></textarea>  
							</td>
						</tr>						
						<tr>
							<th style="text-align: right;">Start Date</th>
							<td style="width: 200px;"><input type="text" id="editRelStartDate" name="editRelStartDate" value="${viewProjRelDetails.releases[0].releaseStartDate}" class="textbox" /></td>
							<th style="text-align: right;">End Date</th>
							<td><input type="text" id="editRelEndDate" name="editRelEndDate" value="${viewProjRelDetails.releases[0].releaseEndDate}" class="textbox" /></td>
						</tr>
					</table>
				</div>				
			</fieldset>
	</div>
	
	<div id="delPrj-popup" title="Delete Project">
		<p style="margin-top:25px;margin-left:20px;">Do you want to delete the project?</p>
	</div>
	
	<div id="delRel-popup" title="Delete Release">
		<p style="margin-top:25px;margin-left:20px;">Do you want to delete the release?</p>
	</div>
	
	</form>
	
