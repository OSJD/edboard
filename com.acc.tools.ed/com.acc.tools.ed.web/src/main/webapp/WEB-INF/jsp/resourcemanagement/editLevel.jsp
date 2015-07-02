<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<html>
<head>
<script
	src="<%=request.getContextPath()%>/script/resourcemanagement-actions.js"></script>
</head>
<body>
	<div id="main_container">
		<div id="editLevel-popup" title="Edit Level">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="editLevelForm"
				action="editLevelForm.do">
				<fieldset>
					<legend>Edit Level</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<td><form:hidden path="existingLevel" id="existingLev"/></td>	
								<th style="text-align: right;">New Level Name</th>
								<td><form:input type="text" path="levelName"
										id="newLevelName" class="textbox"/></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
