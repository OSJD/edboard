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
		<div id="addCapability-popup" title="Add Capability">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="addCapabilityForm"
				action="addCapabilityForm.do">
				<fieldset>
					<legend>Add Capability</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<th style="text-align: right;">Capability Name</th>
								<td><form:input path="capabilityName" type="text"
										id="capabilityName" class="textbox" /></td>

								<th style="text-align: right;">Capability Specialty</th>
								<td><form:input path="capabilitySpecialty" type="text"
										id="capabilitySpecialty" class="textbox" /></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
