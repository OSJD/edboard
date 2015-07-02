<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<html>
<head>
<script
	src="<%=request.getContextPath()%>/script/resourcemanagement-actions.js"></script>
</head>
<body id="mainBody">
	<%@ include file="/WEB-INF/jsp/resourcemanagement/addCapability.jsp"%>
	<%@ include file="/WEB-INF/jsp/resourcemanagement/addLevel.jsp"%>
	<%@ include file="/WEB-INF/jsp/resourcemanagement/addSkill.jsp"%>
	<%@ include file="/WEB-INF/jsp/resourcemanagement/editCapability.jsp"%>
	<%@ include file="/WEB-INF/jsp/resourcemanagement/editLevel.jsp"%>
	<%@ include file="/WEB-INF/jsp/resourcemanagement/editSkill.jsp"%>
	<table class="ebdtable"
		style="width: 850px; align: center; text-align: center;">
		<tr>
			<th>Capability</th>
			<th><select id="existingCapability">
					<option value="" label="Select Capability" />
					<c:forEach items="${capabilityList}" var="capabilityvar">
						<option value="${capabilityvar}">${capabilityvar}</option>
					</c:forEach>
			</select></th>
			<th><a href="#" class="button" id="addCapability"
				style="width: 100px;">Add Capability</a></th>
			<th><a href="#" class="button" id="editCapability"
				style="width: 100px;">Edit Capability</a></th>
			<th><a href="#" class="button" id="deleteCapability"
				style="width: 100px;">Delete Capability</a></th>
		</tr>
		<tr>
			<th>Level</th>
			<th><select id="existingLevel">
					<option value="" label="Select Level" />
					<c:forEach items="${levelList}" var="level">
						<option value="${level}">${level}</option>
					</c:forEach>
			</select></th>
			<th><a href="#" class="button" id="addLevel"
				style="width: 100px;">Add Level</a></th>
			<th><a href="#" class="button" id="editLevel"
				style="width: 100px;">Edit Level</a></th>
			<th><a href="#" class="button" id="deleteLevel"
				style="width: 100px;">Delete Level</a></th>
		</tr>
		<tr>
			<th>Skill</th>
			<th><select id="existingSkill">
			</select></th>
			<th><a href="#" class="button" id="addSkill"
				style="width: 100px;">Add Skill</a></th>
			<th><a href="#" class="button" id="editSkill"
				style="width: 100px;">Edit Skill</a></th>
			<th><a href="#" class="button" id="deleteSkill"
				style="width: 100px;">Delete Skill</a></th>
		</tr>
	</table>
	<div id="deleteCapability-confirm"
		title="Empty the capability recycle bin?">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<div id="deletedCapability"></div>
	</div>
	<div id="deleteLevel-confirm" title="Empty the level recycle bin?">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<div id="deletedLevel"></div>
	</div>
	<div id="deleteSkill-confirm" title="Empty the skill recycle bin?">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
		<div id="deletedSkill"></div>
	</div>
</body>
</html>


