<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
	<script type="text/javascript">
		var issueIdVal = '';
		$(document).ready(function (){
			var addIssuePopUp=$("#addIssuePopUp").dialog({
				 autoOpen: false,
				 height : 350,
				 width : 510,
				 title : "Add New Issue",
				 modal : true,
				 buttons : {
						"Add Issue" : function() { 
				  			if($("#issueDesc").val() != '' && $("#mitigationPlan").val() != '' && $("#issueOpenDate").val() !='' ){
								$.ajax({
									type : "POST",
									url : "./addIssue.do",
									data : $("#addIssueForm").serialize()+"&projectId="+$("#keyIssuesTable #projects").val()+"&releaseId="+$("#keyIssuesTable #releases").val(),
									success : function() {
										alert("Issue added sucessfully.");	
									},
									error : function(data) {	
										$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
									}
								});	
								addIssuePopUp.dialog("close");
							}else{
								alert("Please enter the complete details")
							}
						},
						Cancel : function() {
							addIssuePopUp.dialog("close");
						},
					},
			 });
			
			 $("#addIssue").click(function(){
				 if(validate()){
					 $("#issueOpenDate").val('');
					 $("#issueDesc").val('');
					 $("#mitigationPlan").val('');
					 $("#addIssuePopUp").dialog('open');
				 }
			 });
			 
			 $("#issueOpenDate" ).datepicker({
				showOn: 'button',
				buttonText: 'Show Date',
				buttonImageOnly: true,
				buttonImage: 'resources/cal.gif',
				dateFormat: 'mm/dd/yy',
				constrainInput: true
			});
			 
			 var issueListPopup=$("#issueListPopUp").dialog({
				 autoOpen: false,
				 height : 380,
				 width : 630,
				 title : "Issue List",
				 modal : true,
				 buttons : {
						close : function() {
							issueListPopup.dialog("close");
						},
					},
	
			 });
			 
			 $("#viewIssues").click(function() {
				 if(validate()){
					 $.ajax({
							type : "POST",
							url : "./getIssues.do",
							dataType:"html",
							data : {projectId:$("#keyIssuesTable #projects").val(),releaseId:$("#keyIssuesTable #releases").val()},
							success : function(response) {
								$("#issueListPopUp").html(($(response).filter("#issueListPopUp").html()));
								$("#issueListPopUp").dialog('open');
							},
							error : function(data) {	
								$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
							}
					});	
				 }
			 });
			 
			 $("#editIssueCloseDate" ).datepicker({
					showOn: 'button',
					buttonText: 'Show Date',
					buttonImageOnly: true,
					buttonImage: 'resources/cal.gif',
					dateFormat: 'mm/dd/yy',
					constrainInput: true
			});
			 
			 var editIssuePopUp=$("#editIssuePopUp").dialog({
				 autoOpen: false,
				 height : 410,
				 width : 510,
				 title : "Edit Issue",
				 modal : true,
				 open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
				 closeOnEscape: false,
				 buttons : {
						"Save" : function() { 
								$.ajax({
									type : "POST",
									url : "./editIssue.do",
									data : $("#editIssueForm").serialize()+"&projectId="+$("#keyIssuesTable #projects").val()+"&releaseId="+$("#keyIssuesTable #releases").val(),
									success : function(response) {
										$("#issueListPopUp").html(($(response).filter("#issueListPopUp").html()));
										$("#issueListPopUp").dialog('open');
									},
									error : function(data) {	
										$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
									}
								});	
								editIssuePopUp.dialog("close");
						},
						Cancel : function() {
							editIssuePopUp.dialog("close");
							$("#issueListPopUp").dialog('open');
						},
					},
			 });
			 
			 var deleteMessageDialog=$("#deleteMessage").dialog({
				 autoOpen: false,
				 height : 100,
				 width : 300,
				 title : "Issue List",
				 modal : true,
				 buttons : {
					 	Delete: function(){
					 		$.ajax({
								type : "POST",
								url : "./deleteIssue.do",
								dataType : "html",
								data : {projectId:$("#keyIssuesTable #projects").val(),releaseId:$("#keyIssuesTable #releases").val(),issueId:issueIdVal},
								success : function(response) {
									$("#issueListPopUp").html(($(response).filter("#issueListPopUp").html()));
									$("#issueListPopUp").dialog('open');
								},
								error : function(data) {	
									$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
								}
							});	
					 		$("#deleteMessage").dialog('close');
					 	},
						cancel : function() {
							$("#deleteMessage").dialog('close');
							$("#issueListPopUp").dialog('open');
						},
					},
			 });
			 
			$("#keyIssuesTable #projects").unbind("change").on("change", function(){
				var selectedProject=$("#keyIssuesTable #projects").val();
				$.ajax({
					type : "POST",
					url : "./fetchReleases.do",
					data : {projectId:selectedProject},
					dataType : 'json',	
					
					success : function(response) {
						$('#keyIssuesTable #releases')
						  	.find('option')
						  	.remove();
						$('#keyIssuesTable #releases').append('<option value="SR">Select Release</option>');
						for(var obj in response){
							$('#keyIssuesTable #releases').append('<option value='+response[obj].id+'>'+response[obj].label+'</option>');
						};
					},
					error : function(data) {	
						$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
					}
				});	
			});
			 
		});
		
		function validate(){
			 if($("#keyIssuesTable #projects").val() == 0){
				 alert("Please select a project.");
				 return false;
			 }else if($("#keyIssuesTable #releases").val() == 'SR'){
				 alert("Please select a release.");
				 return false;
			 }
			 return true;
		}
		
		function openIssuePopUp(index){
			$("#issueListPopUp").dialog('close');
			$("#issueId").val($("#issueId_"+index).val());
			$("#editIssueDesc").val($("#issueDesc_"+index).val());
			$("#editIssueOpenDate").text($("#openDate_"+index).val());
			$("#editIssueCloseDate" ).datepicker('setDate',$("#endDate_"+index).val());
			$("#editMitigationPlan").val($("#mtgtnPlan_"+index).val());
			$("#editIssuePopUp").dialog('open');
		}
		
		function deleteIssue(index){
			$("#issueListPopUp").dialog('close');
			issueIdVal = $("#issueId_"+index).val();
			$("#deleteMessage").dialog('open');
		}
		
	</script>	
</head>

	<table id="keyIssuesTable" class="ebdtableheader">
		<tr>
			<th>Project Name</th>
			<td>
				<select id="projects" class="textbox">
						<option value="0">Select Project</option>
					    <c:forEach items="${projectList}" var="project">
					        <option value="${project.id}" <c:if test="${project.selected==true}">selected</c:if>>${project.label}</option>
					    </c:forEach>
				</select>
			</td>
			<th>Release Name</th>
			<td>
				<select id="releases" class="textbox">
					<option  value="0">Select Release</option>
	 					<c:forEach items="${releaseList}" var="release">
					        <option value="${release.id}" >${release.label}</option>
					    </c:forEach>					
				</select>
			</td>
			<td><a href="#" class="button" id="addIssue" style="width: 100px;">Add Issue</a></td>
			<td><a href="#" class="button" id="viewIssues" style="width: 100px;">View Issue(s)</a></td>
		</tr>
	</table>
	
	<div id="addIssuePopUp">
		<form id="addIssueForm">
			<table class="ebdtable">
				<tr>
					<td>Open Date</td>
					<td><input type="date" id="issueOpenDate" name="openDate" class="textbox"/></td>
				</tr>
				<tr>
					<td>Issue Description</td>
					<td><textarea rows="6" cols="70" id="issueDesc" name="issueDesc" class="textbox"></textarea></td>
				</tr>
				<tr>
					<td>Mitigation Plan</td>
					<td><textarea rows="6" cols="70" id="mitigationPlan" name="mtgtnPlan" class="textbox"></textarea></td>
				</tr>
			</table>		
		</form>
	</div>
	
	<div id="issueListPopUp" style="height:700px">
		<c:choose>
			<c:when test="${empty issueList}">
				<span style="color: red">No issues found.</span>
			</c:when>
			<c:otherwise>
				<table class="ebdtable" id="issueListTable">
					<thead>
						<tr>
							<th>Action</th>
							<th>Issue Description</th>
							<th>Open Date</th>
							<th>Close Date</th>
							<th>Mitigation Plan</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${issueList}" var="issue" varStatus="status">
							<input type="hidden" id="issueId_${status.index}" value ="${issue.issueId}"/>
							<input type="hidden" id="issueDesc_${status.index}" value ="${issue.issueDesc}"/>
							<input type="hidden" id="openDate_${status.index}" value ="${issue.openDate}"/>
							<input type="hidden" id="endDate_${status.index}" value ="${issue.endDate}"/>
							<input type="hidden" id="mtgtnPlan_${status.index}" value ="${issue.mtgtnPlan}"/>
							<tr>
								<td style="vertical-align: top;">
									<c:choose>
										<c:when test="${not empty issue.endDate}">
											NA
										</c:when>
										<c:otherwise>
											<a href="#" onclick="openIssuePopUp('${status.index}')">Edit</a><br>
											<a href="#" onclick="deleteIssue('${status.index}')">Delete</a>											
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<div style="overflow-y: scroll;height:100px;width:200px;">${issue.issueDesc}</div>
								</td>
								<td style="vertical-align: top;">${issue.openDate}</td>
								<td style="vertical-align: top;">${issue.endDate}</td>
								<td>
									<div style="overflow-y: scroll;height:100px;width:200px;">${issue.mtgtnPlan}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>				
			</c:otherwise>
		</c:choose>
	</div>
	
	<div id="editIssuePopUp">
		<form id="editIssueForm">
			<input type="hidden" id="issueId" name="issueId"/>
			<table class="ebdtable">
				<tr>
					<td>Open Date</td>
					<td id="editIssueOpenDate"/>
				</tr>
				<tr>
					<td>Close Date</td>
					<td><input type="date" id="editIssueCloseDate" name="endDate" class="textbox"/></td>
				</tr>
				<tr>
					<td>Issue Description</td>
					<td><textarea rows="6" cols="70" id="editIssueDesc" name="issueDesc" class="textbox"></textarea></td>
				</tr>
				<tr>
					<td>Mitigation Plan</td>
					<td><textarea rows="6" cols="70" id="editMitigationPlan" name="mtgtnPlan" class="textbox"></textarea></td>
				</tr>
			</table>		
		</form><br>
		Note: If the closed date is entered and saved, the issue cannot be edited or deleted.
	</div>
	
	<span id="deleteMessage">Are you sure you want to delete the issue ?</span>
