<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<%=request.getContextPath()%>/script/projectmanagement-actions.js"></script>
	<form:form commandName="statusForm" action="downloadWsr.do" id="statusForm">
	
	<table class="ebdtableheader">
		<tr>
			<th>Project Name</th>
			<td>
				<form:select id="projects" name="projectName" path ="projectName">
					<option value="0">Select Project</option>
				    <c:forEach items="${projectList}" var="project">
				        <option value="${project.id}" >${project.label}</option>
				    </c:forEach>
				</form:select>
			</td>
			<td style="background-image: none;background-color: white;border-width: 1px;border-style: solid;border-color: #999999;"><div style="width: 5px;"></div></td>
			<th>Release Name</th>
			<td>
				<form:select id="releases" name="releaseName" path ="releaseName">
					<option  value="0">Select Release</option>
	 					<c:forEach items="${releaseList}" var="release">
					        <option value="${release.id}" >${release.label}</option>
					    </c:forEach>					
				</form:select>
			</td>
		<th>Report Type</th>
			<td><form:select id="reportType" class="textbox" path="status">
					<option value="0">Report Type</option>
					<option value="Weekly">Weekly</option>
					<option value="Monthly">Monthly</option>
				</form:select></td>
			<th style="text-align: right;">Start Date</th>
			<td><form:input type="text" id="startDateFilter" class="textbox" path="startDate"/></td>
			<th style="text-align: right;">End Date</th>
			<td><form:input type="text" id="endDateFilter" class="textbox" path="endDate"/></td>
			<th>Report Format</th>
			<td><form:select id="reportFormat" class="textbox" path="reportFormat">
					<option value="0">Report Format</option>
					<option value="Word">WORD</option>
					<option value="Pdf">PDF</option>
				</form:select></td>
		</tr>
	</table>
	<div class="boxmsg border-boxmsg" style="width: 580px; color: red;">
		Please select <u>Project</u> and <u>Release</u> from the drop down
		above to download the report. <br> <b class="border-notch notch"></b>
		<b class="notch"></b>
	</div>

<!-- <a href="./downloadWsr.do" class="button" id="downloadWsr" style="width: 100px;">Download Report</a>  -->
<input type="button" value="Download Report" onclick = "downloadReport()"  /> 
</form:form>
