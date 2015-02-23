<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script>
 $(document).ready(function () {	
	var isQuizAttempted="${surveySystemForm.quizAttempted}";
	var stopWatch="${surveySystemForm.stopWatch}";
	var timeOut;
	
	var takeQizeDialog = $("#takeQize-popup").dialog({
		autoOpen : false,
		height : 550,
		width : 650,
		modal : true,
	});
	
	$("#surveySystemFormBtn").on("click",function(){
		$.ajax({
			type : "POST",
			url : "./surveyResponse.do",
			data :$("#surveySystemForm").serialize() ,		
			dataType : 'json',	
			beforeSend:function(){
			  },
			success : function(response) {
				if(response.status=="success"){
					alert("Quiz successfully submitted!");
				} else {
					alert("System Error.Please reach out to system admin.");
				}
		 		takeQizeDialog.dialog("close");
		 		isQuizAttempted="true";
		 		clearTimeout(timeOut); 
			},
			error : function(data) {	
				alert("Error :"+data.status);
			}
		});		
	});
	 
	 $("#takeQize-popup-close").unbind("click").on("click", function() {
		 takeQizeDialog.dialog("close");	 
	 });

	$("#takeQize").button().unbind("click").on("click", function() {
		var quizdatetime="${surveySystemForm.quizStartDateTime}".split(" ");
		var quizdate=quizdatetime[0].split("/");
		var quiztime=quizdatetime[1].split(":");
 		var quizStartDate=new Date(quizdate[2],(quizdate[0]-1),quizdate[1],quiztime[0],quiztime[1],00,00);
		var currentDate=new Date();
		if(isQuizAttempted!="false"){
			alert("Quiz already attempted!");
		} else {
			if(quizStartDate<currentDate){
				takeQizeDialog.dialog("open");	
				timeOut=setTimeout(function(){
					alert("Time Out");
					clearTimeout(timeOut); 
					$("#surveySystemFormBtn").click();
				}, stopWatch*60*1000);
			} else{
				alert("Quiz start only after - "+quizdatetime);
			} 
		}
		 	 
	});
	 
});
</script>
<div style="border-color: #999999;border-width: 1px;border-style: solid;height: 250px;overflow: auto;">${surveySystemForm.announcementHTMLData}</div></div>

<div style="background: #b5cfd2 url('../resources/cell-blue.jpg');background-repeat: repeat-x;border-style: solid;border-color: #999999;border-width: 1px;">
	<a href="#" class="button" id="takeQize" style="width: 100px;margin-left: 450px;">Take Quiz</a>
</div>

<div id="takeQize-popup" title="Take Quize">
	<form:form commandName="surveySystemForm" action="surveyResponse.do">
		<form:hidden path="quizStartDateTime" />
		<table class="ebdtable" style="width: 100%">
			<tr>
			    <th colspan="4">Questions</th>
			</tr>
			<c:forEach items="${surveySystemForm.questionnaire}" var="question" varStatus="status">
				<tr>
				    <td colspan="4">
				    	<form:hidden path="questionnaire[${status.index}].questionDescription" />
				    	<form:hidden path="questionnaire[${status.index}].correctAnswer" />
				    	<form:hidden path="questionnaire[${status.index}].questionId" />
				    	<form:hidden path="questionnaire[${status.index}].announcementId" />
				    	${question.questionDescription}
				    </td>
				</tr>
				<c:choose>
					<c:when test="${question.questionType =='1'}">
						<c:forEach items="${question.questionOptions}" var="questionOptions">
								<tr>
						    		<td colspan="4" style="background-image: none;background: #ebe2d7;">
						    			<form:radiobutton path="questionnaire[${status.index}].response" value="${questionOptions.optionValue}"/>${questionOptions.optionDescription}
						    		</td>
								</tr>
						</c:forEach>
					</c:when>
					<c:when test="${question.questionType =='2'}">
						<c:forEach items="${question.questionOptions}" var="questionOptions">
								<tr>
						    		<td colspan="4" style="background-image: none;background: #ebe2d7;">
						    			<form:checkbox path="questionnaire[${status.index}].response" value="${questionOptions.optionValue}" />${questionOptions.optionDescription}
						    		</td>
								</tr>
						</c:forEach>
					</c:when>	
					<c:when test="${question.questionType =='3'}">
						<tr>
							<c:forEach items="${question.matchOptions}" var="questionOptions">
							 	<c:choose>
										<c:when test="${questionOptions.key =='LeftSide'}">
								    		<td style="background-image: none;background: #ebe2d7;border-collapse: collapse;border-spacing: 0px;">
								    			<table style="width: 100%;">
								    				<c:forEach items="${questionOptions.value}" var="matchOptions">
								    			    	<tr><td>${matchOptions.optionDescription}</td></tr>
								    			    </c:forEach>
								    			</table>
								    		</td>
										</c:when>
										<c:otherwise>
								    		<td style="background-image: none;background: #ebe2d7;">
								    			<table style="width: 100%;">
								    				<c:forEach items="${questionOptions.value}" var="matchOptions">
								    			    	<tr><td>${matchOptions.optionDescription}</td></tr>
								    			    </c:forEach>
								    			</table>
								    		</td>
										</c:otherwise>
								</c:choose>
							</c:forEach>
							<td>
								<table>
									<c:forEach items="${question.matchOptions}" var="questionOptions">
										<tr><td style="background-image: none;background: #ebe2d7;"><form:input type="text" path="questionnaire[${status.index}].response" class="textbox" /></td></tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
								<tr>
						    		<td colspan="4" style="background-image: none;background: #ebe2d7;"><form:input type="text" path="questionnaire[${status.index}].response" class="textbox" /></td>
								</tr>
					</c:otherwise>	
				</c:choose>		
			</c:forEach>
			<tr><td colspan="4"><div style="margin-left: 475px;"><input value="Submit" type="button" id="surveySystemFormBtn"><input value="close" type="button" id="takeQize-popup-close"></div></td></tr>
		</table>
	</form:form>
</div>

