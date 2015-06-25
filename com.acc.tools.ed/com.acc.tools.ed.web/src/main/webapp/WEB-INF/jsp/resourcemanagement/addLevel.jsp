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
		<div id="addLevel-popup" title="Add Level">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="addLevelForm"
				action="addLevelForm.do">
				<fieldset>
					<legend>Add Skill</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<th style="text-align: right;">Level Name</th>
								<td><form:input path="levelName" type="text"
										id="levelName" class="textbox" /></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
