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
		<div id="editSkill-popup" title="Edit Skill">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="editSkillForm"
				action="editSkillForm.do">
				<fieldset>
					<legend>Edit Skill</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<td><form:hidden path="capabilityName" id="exisCapability"/></td>	
								<td><form:hidden path="existingSkill" id="exisSkill"/></td>	
								<th style="text-align: right;">New Skill Name</th>
								<td><form:input type="text" path="skillName"
										id="newSkillName" class="textbox"/></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
