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
		<div id="editCapability-popup" title="Edit Capability">
			<p class="validateTips">All form fields are required.</p>
			<form:form commandName="editCapabilityForm"
				action="editCapabilityForm.do">
				<fieldset>
					<legend>Edit Capability</legend>
					<div>
						<table class="ebdtable" id="release">
							<tr>
								<td><form:hidden path="existingCapability" id="existingCap"/></td>	
								<th style="text-align: right;">New Capability Name</th>
								<td><form:input type="text" path="capabilityName"
										id="newCapabilityName" class="textbox"/></td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
