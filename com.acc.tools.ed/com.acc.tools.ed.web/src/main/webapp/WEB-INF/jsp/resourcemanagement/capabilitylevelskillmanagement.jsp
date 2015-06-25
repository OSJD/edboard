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
	<table class="ebdtableheader" style="width: 850px;">
	<tr><th><a href="#" class="button" id="addCapability" style="width: 100px;">Add Capability</a></th>
	<th><a href="#" class="button" id="addLevel" style="width: 100px;">Add Level</a></th>
	<th><a href="#" class="button" id="addSkill" style="width: 100px;">Add Skill</a></th>
	</tr>
	</table>
</body>
</html>


