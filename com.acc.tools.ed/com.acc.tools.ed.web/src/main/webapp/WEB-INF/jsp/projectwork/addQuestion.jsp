<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<title>Add Question</title>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script>
$(document).ready(function() {
	$("#questionType").unbind("change").on("change",function(){
		var questionType=$(this).val();
		if(questionType==4){
			$("#questionOptionsRowHdr").hide();
			$("#questionOptionsRowDtl1").hide();
			$("#questionOptionsRowDtl2").hide();
			$("#questionOptionsRowDtl3").hide();
			$("#questionOptionsRowDtl4").hide();				
			$("#textOptionRowHdr").show();
			$("#textOptionRowDtl").show();
		}else {
			$("#questionOptionsRowHdr").show();
			$("#questionOptionsRowDtl1").show();
			$("#questionOptionsRowDtl2").show();
			$("#questionOptionsRowDtl3").show();
			$("#questionOptionsRowDtl4").show();				
			$("#textOptionRowHdr").hide();
			$("#textOptionRowDtl").hide();
		}
		return true;
	});
	
	$("#questionnaireSubmtBtn").on("click",function(){
		$.ajax({
			type : "POST",
			url : "./savequestion.do",
			data :$("#questionnaireForm").serialize() ,		
			dataType : 'json',	
			beforeSend:function(){
			  },
			success : function(response) {
				viewQuestionnaire(response);
            	window.close();
			},
			error : function(data) {	
				alert(data.status);
			}
		});		
	});
	
	var viewQuestionnaire=function(questionnaire){
			var questionDescription=questionnaire.questionDescription;
			var questionType=questionnaire.questionType;
			var questionOptions=questionnaire.questionOptions;
			var correctAnswer=questionnaire.correctAnswer; 
			questionRow(questionDescription,questionType,questionOptionsRow(questionOptions,questionType,correctAnswer));
	};
	
	var questionRow=function(questionDescription,questionType,optionsRow){
		
		var optionHeader;
		if(questionType==4){
			optionHeader='<tr><td colspan="3" >Answer</td></tr>';
		}else{
			optionHeader='<tr><td>Option Value</td><td>Option Label</td><td>Answer</td></tr>';
		}
	     $('#questionnaireDataTable > tbody:last',window.opener.document).append('<tr>'+
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

	
});
</script>
</head>
	<body>
		<div id="addquestion-popup" title="Add New Question">
			<form:form commandName="questionnaireForm" action="addquestion.do">
				<table class="ebdtable" id="addQuestionTbl" style="width: 100%;">
					<tr>
						<th>Question</th>
						<td colspan="2"  style="width: 550px;">
							<form:hidden path="announcementId"/>
							<form:input type="text" path="questionDescription" class="textbox" size="85"/>					
						</td>
					</tr>
					<tr>
						<th>Question Type</th>
						<td colspan="2">
							<form:select path="questionType"  style="width:135px;" class="textbox" >
								<form:option value="0" label="--- Select Type---" />
								<form:option value="1" label="Radio" />
								<form:option value="2" label="Check" />
								<form:option value="3" label="Match" />
								<form:option value="4" label="Text" />						
							</form:select>
						</td>
					</tr>
					<tr id="questionOptionsRowHdr" style="display: none;">
						<th style="width: 50px;">Value</th>
						<th style="text-align: left;width: 150px;">Label</th>
						<th style="text-align: left;">Answer</th>
					</tr>
					<c:forEach items="${questionnaireForm.questionOptions}" var="questionOption" varStatus="status">
						<tr id="questionOptionsRowDtl${questionOption.optionOrder}" style="display: none;">
							<td>${questionOption.optionOrder}
								<form:hidden path="questionOptions[${status.index}].optionOrder"/>
							</td>
							<td><form:input type="text" path="questionOptions[${status.index}].optionDescription" class="textbox" /></td>
							<td><form:checkbox path="correctAnswer" value="${questionOption.optionOrder}"/></td>
						</tr>
					</c:forEach>
					<tr id="textOptionRowHdr" style="display: none;">
						<th colspan="3" style="text-align: left;">Answer</th>
					</tr>
					<tr id="textOptionRowDtl" style="display: none;">
						<td colspan="3"><form:input type="text" path="correctTxtAnswer" class="textbox" /></td>
					</tr>
					<tr>
						<td colspan="3">
								<div style="margin-left: 545px;">
									<input id="questionnaireSubmtBtn" type="button" value="Submit" class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onsubmit="return false;">
								</div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</body>
</html>