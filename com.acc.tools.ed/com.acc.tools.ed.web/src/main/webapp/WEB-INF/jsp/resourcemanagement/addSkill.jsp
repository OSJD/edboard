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
		<div id="addSkill-popup" title="Add Skill">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="addSkillForm" action="addSkillForm.do">
				<fieldset>
					<legend>Add Skill</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<td><form:input type="text" path="capabilityName" id="capName"/></td>	
								<th style="text-align: right;">Skill Name</th>
								<td><form:input path="skillName" type="text" id="skillName"
										class="textbox" /></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
