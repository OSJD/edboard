<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<script src="<%=request.getContextPath()%>/script/libs/jquery.sceditor.bbcode.min.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery-ui-timepicker.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/edb-common.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui-timepicker.css"/>
<script>
	$(document).ready(function() {
		var initEditor = function() {
			$("#announcementHTMLData").sceditor({
				plugins: 'xhtml',
				style: "<%=request.getContextPath()%>/css/jquery.sceditor.default.min.css",
				toolbar: "bold,italic,underline|strike,subscript,superscript,left,center,right,justify|font,size,color,removeformat|cut,copy,paste,pastetext|bulletlist,orderedlist|table,code,quote,horizontalrule|image,link,unlink,emoticon"
			});
		};
		initEditor();
		
		$("#quizStartDateTimeId").datetimepicker();
		$('#setTimeCheckId').click(function() {
		    $("#startDateTimeId").toggle(this.checked);
		});
		
		var viewEvaluationDialog=$("#viewEvaluation-popup").dialog({
			autoOpen : false,
			height : 350,
			width : 650,
			modal : true,
		});
		
		
		
		$("#addQuestion").click(function(){
			var subjectId=$("#announcementSubjectId").val();
			if(subjectId=="0" || subjectId=="NONE"){
				alert("Please add Announcement before creating questionnaire!");
				return false;
			}
			edb.openWindow("./addquestion.do?announcementId="+subjectId,650,260,"Add Question");
		});
		
		
		
		$("#viewEvaluation").click(function(){
			var subjectId=$("#announcementSubjectId").val();
			if(subjectId=="0" || subjectId=="NONE"){
				alert("Please select Announcement!");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "./viewEvaluation.do",
				data : {announcementSubjectId:subjectId},
				beforeSend : function() {
				},
				success : function(response) {
					$("#viewEvaluation-popup").html(response);
				},
				error : function(data) {

				},
				complete: function(data){
				}
			});
			viewEvaluationDialog.dialog("open");
		});
		

		$('#surveySystemForm').ajaxForm( {
			success: function() {
		 		if($("#announcementSubjectId").val()=="0"){
		 			alert("New Announcement added Successfully!");
	 				$("#updateAnnouncement").show();
	 				$("#createAnnouncement").hide();
		 		} else {
		 			alert("Updated Announcement Successfully!");
		 		}
			 },
		 	complete: function(xhr) {
				$.ajax({
					type : "POST",
						url : "./getAllAnnounceSubjects.do",
						dataType : 'json',	
						success : function(announcementSubject) {
							var selectedSubjectId=$("#announcementSubjectId").val();
							$('#announcementSubjectId').empty();
							var newlyAddedSubject=$("#announcementNewSubject").val();
							for(var field in announcementSubject){
								$('#announcementSubjectId').append('<option value="'+announcementSubject[field].id+'">'+announcementSubject[field].label+'</option>');
								if(announcementSubject[field].label==newlyAddedSubject){
									selectedSubjectId=announcementSubject[field].id;
								}
							}
							$('#announcementSubjectId').append('<option value="0">Create new Announcement</option>');
							$("#announcementSubjectId").val(selectedSubjectId);
							$("#announcementNewSubject").hide();
							
						},
						error : function(data) {	
							alert("Error fetching all annouadding/editing Announcement!Please check with admin.");
						}
				});
		 	}
		});
		

		
 		$('#questionnaireForm').ajaxForm( {
			success: function() {

			 },
		 	complete: function(xhr) {
		 		var questionDescription=$("#questionDescription").val();
		 		var questionType=$("#questionType").val();
		 		var optionsRow='';
		 		var correctAnswer=null;
 				if(questionType==4){
 					correctAnswer=$("#correctTxtAnswer").val();
					optionsRow=optionsRow+'<tr><td colspan="3" style="background-image: none;background: #ebe2d7;">'+correctAnswer+'</td></tr>';
				} else {
 					var questionnaireForm=$('#questionnaireForm').serializeArray();
					var i=1,j=0,k=0;
					for(var index in questionnaireForm){
						var answer='';
						if(questionnaireForm[index].name.indexOf("optionOrder")>1 || questionnaireForm[index].name.indexOf("optionDescription")>1){
							correctAnswer=$("#correctAnswer"+i);
							var optionValue=questionnaireForm[index].value;
							if(correctAnswer.is(':checked')){
								answer='<img alt="Correct Answer" src="./resources/tick.png" height="20px;"	width="30px;">';
							}
							if(j==0){
								optionsRow=optionsRow+'<tr>';	
							}
							optionsRow=optionsRow+'<td style="background-image: none;background: #ebe2d7;">'+optionValue+'</td>';
							j++;
				 			if(k%2>0){
								j=0;
								i++;
								optionsRow=optionsRow+'<td style="background-image: none;background: #ebe2d7;">'+answer+'</td></tr>';	
							} 
							k++;
						}
					}

				} 
		 		questionRow(questionDescription,questionType,optionsRow);
		    },
			error : function(data) {	
				alert(data.responseText);
			}

		}); 

		
		$("#mainBody").unbind("change").on("change","#questionType",function(){
 			var questionType=$(this).val();
			if(questionType==4){
				$("#addQuestionTbl #questionOptionsRowHdr").hide();
				$("#addQuestionTbl #questionOptionsRowDtl1").hide();
				$("#addQuestionTbl #questionOptionsRowDtl2").hide();
				$("#addQuestionTbl #questionOptionsRowDtl3").hide();
				$("#addQuestionTbl #questionOptionsRowDtl4").hide();				
				$("#addQuestionTbl #textOptionRowHdr").show();
				$("#addQuestionTbl #textOptionRowDtl").show();
			}else {
				$("#addQuestionTbl #questionOptionsRowHdr").show();
				$("#addQuestionTbl #questionOptionsRowDtl1").show();
				$("#addQuestionTbl #questionOptionsRowDtl2").show();
				$("#addQuestionTbl #questionOptionsRowDtl3").show();
				$("#addQuestionTbl #questionOptionsRowDtl4").show();				
				$("#addQuestionTbl #textOptionRowHdr").hide();
				$("#addQuestionTbl #textOptionRowDtl").hide();
			}
			return true;
		});
		
		var questionRow=function(questionDescription,questionType,optionsRow){
			
			var optionHeader;
			if(questionType==4){
				optionHeader='<tr><td colspan="3" >Answer</td></tr>';
			}else{
				optionHeader='<tr><td>Option Value</td><td>Option Label</td><td>Answer</td></tr>';
			}
		     $('#questionnaireDataTable > tbody:last').append('<tr>'+
						'<th style="width: 75px;">Question</th>'+
						'<td style="width: 550px;">'+questionDescription+'</td>'+
						'<th style="width: 75px;">Question Type</th>'+
						'<td style="width: 30px;">'+questionType+'</td>'+
						'<td style="width: 75px;">Edit | Delete</td>'+
						'</tr><tr><td colspan="5" style="background-image: none;background: #ebe2d7;">'+
						'<table>'+optionHeader
								 +optionsRow+
						'</table></td></tr>');
		};
		var questionOptionsRow=function(questionOptions,questionType,correctAnswer){
			var questionOptionsRowElement='';
			if(questionType!=4){
				for(var option in questionOptions){
					//alert(correctAnswer+" | "+questionOptions[option].optionValue);
					var optionValue=questionOptions[option].optionValue;
					var answer='';
					if(correctAnswer.indexOf(optionValue)>-1){
						answer='<img alt="Correct Answer" src="./resources/tick.png" height="20px;"	width="30px;">';
					}
					questionOptionsRowElement=questionOptionsRowElement+
						'<tr>'+
							'<td style="background-image: none;background: #ebe2d7;">'+optionValue+'</td>'+
							'<td style="background-image: none;background: #ebe2d7;">'+questionOptions[option].optionDescription+'</td>'+
							'<td style="background-image: none;background: #ebe2d7;">'+answer+'</td>'+
						'</tr>';
				}
			}else {
				questionOptionsRowElement=questionOptionsRowElement+
				'<tr>'+
					'<td colspan="2" style="background-image: none;background: #ebe2d7;">'+correctAnswer+'</td>'+
				'</tr>';
			}
			return questionOptionsRowElement;								   
		};
		
		
		$("#announcementSubjectId").unbind("change").on("change",function(){
			var selectedValue=$("#announcementSubjectId").val();
			resetAnnouncementForm();
			 $('#questionnaireDataTable > tbody').empty();
			if(selectedValue==='0'){
				$("#announcementNewSubject").show();
 				$("#updateAnnouncement").hide();
 				$("#createAnnouncement").show();
			} else {
				$("#announcementNewSubject").hide();
 				$("#updateAnnouncement").show();
 				$("#createAnnouncement").hide();
 				$.ajax({
 					type : "POST",
 						url : "./getAnnouncementDetailsBySubject.do",
 						data : {announcementId:selectedValue},
 						dataType : 'json',	
 						success : function(surveySystemObject) {
 							viewSurvey(surveySystemObject);
 						},
 						error : function(data) {	
 							alert(data.responseText);
 						}
 					});
			}
		});
		
		var viewSurvey=function(surveySystemObject){
				for(var field in surveySystemObject){
						if(field=="announcementHTMLData"){
							$("#announcementHTMLData").data("sceditor").val(surveySystemObject[field]);
						} else if(field=="announcementCreateDate"){
							$("#announcementCreateDateId").html(surveySystemObject[field]);
						} else if(field=="setTime"){
							var setTimeCheckId=surveySystemObject[field];
							$("#setTimeCheckId").prop('checked',setTimeCheckId);
							if(setTimeCheckId){
								$("#startDateTimeId").show();
							}
						} else if(field=="announcementPublished"){
							$("#announcementPublishedId").prop('checked',surveySystemObject[field]);
						} else if(field=="quizStartDateTime"){
							$("#quizStartDateTimeId").val(surveySystemObject[field]);
						} else if(field=="stopWatch"){
							$("#stopWatchId").val(surveySystemObject[field]);
						}else if(field=="questionnaire"){
							viewQuestionnaire(surveySystemObject[field]);
						}
					}
		};	
		
		var viewQuestionnaire=function(questionnaire){
			for(var question in questionnaire){
				var questionDescription=questionnaire[question].questionDescription;
				var questionType=questionnaire[question].questionType;
				var questionOptions=questionnaire[question].questionOptions;
				var correctAnswer=questionnaire[question].correctAnswer; 
				questionRow(questionDescription,questionType,questionOptionsRow(questionOptions,questionType,correctAnswer));
			}
		};
		
	
		var resetAnnouncementForm=function(){
			$("#announcementNewSubject").val("");
			$("#announcementHTMLData").data("sceditor").val("");
			$("#announcementCreateDateId").html("");
			$("#setTimeCheckId").prop('checked',false);
			$("#announcementPublishedId").prop('checked',false);
			$("#quizStartDateTimeId").val("");
		};
	});
</script>
<form:form commandName="surveySystemForm" action="addEditAnnouncement.do">
	<table id="announcementTable" class="ebdtable" style="width: 50%;">
		<tbody>
			<tr>
				<th colspan="2" style="width:280px;">Announcement Subject</th>
				<th  style="width:300px;">
					<form:select path="announcementSubjectId" id="announcementSubjectId" cssClass="textbox" cssStyle="width: 300px;">
						<form:option value="NONE" label="--- Select Announcement---" />
						<form:options items="${announcementSubjectList}" itemValue="id" itemLabel="label"/>
					    <form:option value="0" label="Create new Announcement"/>
					</form:select>	
					<form:input path="announcementSubject" id="announcementNewSubject" class="textbox" style="margin-top: 1px;width: 299px;display: none;"/>				
				</th>
				<th><div style="width:150px;" id="announcementCreateDateId">${announcementCreateDate}</div></th>
				<th><div style="width:250px;"><input type="submit" value="Create Announcement" id="createAnnouncement" ><input type="submit" id="updateAnnouncement" value="Update Announcement" style="display: none;"></div></th>
			</tr>
			</tr>
			<tr>
				<td colspan="5"><form:textarea path="announcementHTMLData" cssStyle="height:300px;width:900px;" /></td>
			</tr>
			<tr id="questionnaireSetupRow">
				<td colspan="5">
					<div style="width: 75px;clear: both;float: left;">Set Time 
            				<form:checkbox path="setTime" id="setTimeCheckId" />
            		</div>
					<div id="startDateTimeId" style="width: 550px;float: left;margin-left: 5px;display:none">
						<div style="width: 150px;float: left;">Start Date Time <form:input path="quizStartDateTime" id="quizStartDateTimeId" class="textbox"/></div>
						<div style="width: 150px;float: left;">Stop Watch(Minutes) <form:input path="stopWatch" id="stopWatchId" cssStyle="width:25px;" class="textbox"/></div>						
					</div>
					<div style="width: 70px;float: right;">Publish 
           				<form:checkbox path="announcementPublished" id="announcementPublishedId" />
		            </div>
					<div style="width: 210px;margin-left: 400px;clear: both;">
						<a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="addQuestion" style="width: 100px;">
							<span class="ui-button-text">Create Question</span>
				 		</a>
				 		<a href="#" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" id="viewEvaluation" style="width: 100px;">
							<span class="ui-button-text">View Evaluation</span>
				 		</a>
			 		</div>
			 	</td>
			</tr>	
			<tr>
			  <td colspan="5">
			      <table id="questionnaireDataTable" class="innertable1" style="width: 100%">
			      	<tbody>
			      	</tbody>
			      </table>
			  </td>
			</tr>
		</tbody>
	</table>
</form:form>

<!--  -->
<div id="viewEvaluation-popup" title="View Evaluation">
	View Evaluation 
</div>
