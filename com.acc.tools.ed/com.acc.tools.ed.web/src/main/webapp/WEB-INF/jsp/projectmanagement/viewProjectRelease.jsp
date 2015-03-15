<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
	<script>
	 $(document).ready(function () {
		  
		 
			$("#mainBody").unbind("click").on("click","#addNewCompnt", function() {
				
 				 $( "#compStartDate" ).datepicker({
						showOn: 'button',
						buttonText: 'Show Date',
						buttonImageOnly: true,
						buttonImage: 'resources/cal.gif',
						dateFormat: 'mm/dd/yy',
						constrainInput: true,
						minDate:$("#mainBody #relStartDate").html(),
						maxDate:$("#mainBody #relEndDate").html()
				 }); 
				 $( "#compEndDate" ).datepicker({
						showOn: 'button',
						buttonText: 'Show Date',
						buttonImageOnly: true,
						buttonImage: 'resources/cal.gif',
						dateFormat: 'mm/dd/yy',
						constrainInput: true,
						minDate:$("#mainBody #relStartDate").html(),
						maxDate:$("#mainBody #relEndDate").html()
				 });
				$("#mainBody #componentPopup").dialog("open");
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
				success : function(componentDetail) {
					for(var field in componentDetail){
						if(field=="functionalDesc"){
							$('#functionalDesc').val(componentDetail.functionalDesc);
							$("#functionalDesc").attr('disabled','disabled');
						} else if(field=="startDate"){
							$('#compStartDate').val(componentDetail.startDate);
							$("#compStartDate").attr('disabled','disabled');
						} else if(field=="endDate"){
							$('#compEndDate').val(componentDetail.endDate);
							$("#compEndDate").attr('disabled','disabled');
						}
					}
				}
		});
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
		 
		 /*Component Data table*/
		 $("#componentTable").dataTable( {
		        columnDefs: [ {
		            targets: [ 0 ],
		            orderData: [ 0, 1 ]
		        }, {
		            targets: [ 1 ],
		            orderData: [ 1, 0 ]
		        }, {
		            targets: [ 4 ],
		            orderData: [ 4, 0 ]
		        } ]
		    } ); 
	});  

	 
	 function setCharAt(str,index,chr) {
			if(index > str.length-1) return str;
			return str.substr(0,index) + chr + str.substr(index+1);
		}

	</script>
</head>


<table style="width: 100%;margin-bottom: 25px;">
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
					<td style="background-color: #eaead9;"><div id="prjStartDate">${viewProjRelDetails.startDate}</div></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">End Date</td>
					<td style="background-color: #eaead9;"><div id="prjEndDate">${viewProjRelDetails.endDate}</div></td>
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
					<td style="background-color: #eaead9;">
						<div id="relStartDate">${viewProjRelDetails.releases[0].releaseStartDate}</div></td>
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
<table class="ebdtable" id = "componentTable" style="width:100%;margin-top: 25px;">
		<thead>
			<th colspan="2" width="50px;"><a href="#" id="addNewCompnt"><img class="imgLink" 	alt="add comnponent" src="./resources/addnews.gif" width="20px;"></a></th>
			<th style="width: 160px;">Component Name</th>
			<th style="width: 80px;">Component Phase</th>
			<th style="width: 450px;">Functional Desc</th>
			<th style="width: 80px;">Start Date</th>
			<th style="width: 80px;">End Date</th>
			<th style="width: 80px;">Status</th>
			<th style="width: 80px;">% Completed</th>
			<th style="width: 150px;">Resource</th>
			<th style="width: 450px;">Work Description</th>
		</thead>
		<tbody>
			<c:choose>	
		        <c:when test="${empty viewProjRelDetails.releases[0].components}">
		        	<tr id="noComponentsRow">
		        		<td colspan="11">
							<div id="noComponetMsg" class="boxmsg border-boxmsg" style="margin-left:50px; width: 780px;color: red;">
							    <p>No <u>Components/Tasks</u> , <u>Change Requests(CR)</u> or <u>Defects</u> are configured for Project :<u>${viewProjRelDetails.projectName}</u> and Release :<u>${viewProjRelDetails.releases[0].releaseName}</u> .<br>
							    Please add <u>Component/Task/CR/Defect</u> and assign a resource accordingly using the above form.
							    </p>
							    <b class="border-notch notch"></b>
							    <b class="notch"></b>
							</div>
						</td>
					</tr>
		        </c:when>
		        <c:otherwise>
		        	<c:forEach var="component" items="${viewProjRelDetails.releases[0].components}">
						<tr>
							<td><img alt="edit project" src="./resources/edit.gif" width="20px;"></td>
							<td><img alt="delete project" src="./resources/delete.gif"  width="20px;"></td>
							<td width="215px;" id="compName">${component.componentName}</td>
							<td width="115px">
								<c:choose>
									<c:when test="${component.phaseId==1}">
										Analysis
									</c:when>
									<c:when test="${component.phaseId ==2}">
										Design
									</c:when>
									<c:when test="${component.phaseId==3}">
										Build
									</c:when>
									<c:when test="${component.phaseId ==4}">
										Test
									</c:when>
									<c:otherwise>
										Support
									</c:otherwise>
								</c:choose>
							</td>
							<td width="295px;" id="compFuncDesc"><div style="height:20px;display:table-cell;vertical-align:middle;">${component.functionalDesc}</div></td>
							<td awidth="80px;" id="comStDate">${component.startDate}</td>
							<td width="80px;" id="compEtDate">${component.endDate}</td>
							<td width="80px">Not Started</td>
							<td width="80px" align="center">0 % </td>
							<td width="150px;" id="compResName">${component.resourceName}</td>
							<td width="295px">${component.workDesc}</td>
						</tr>
					</c:forEach>
		        </c:otherwise>
		    </c:choose>
	</tbody>	    
</table>

	
