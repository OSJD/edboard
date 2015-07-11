<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<%=request.getContextPath()%>/script/reports.js"></script>
<form:form action="downloadDMSReport.do" commandName="statusForm" id="statusForm">
<table class="ebdtableheader">
		<tr>
			<th>Program Name</th>
			<td>
				<form:select id="rptprogram" class="textbox" path="programName">
					<option value="0">Select Program</option>
				    <c:forEach items="${programList}" var="program">
				        <option value="${program.id}" <c:if test="${program.selected==true}">selected</c:if>>${program.label}</option>
				    </c:forEach>
				</form:select>
			</td>
			<th>Project Name</th>
			<td>
				<form:select id="rptproject" class="textbox" path="projectName">
					<option value="0">All Projects</option>
				    <c:forEach items="${projectList}" var="project">
				        <option value="${project.id}" <c:if test="${project.selected==true}">selected</c:if>>${project.label}</option>
				    </c:forEach>
				</form:select>
			</td>
			<td style="background-image: none;background-color: white;border-width: 1px;border-style: solid;border-color: #999999;"><div style="width: 15px;"></div></td>
			<th>Release Name</th>
			<td>
				<form:select id="rptreleases" class="textbox" path="releaseName">
					<option  value="0">All Releases</option>
	 					<c:forEach items="${releaseList}" var="release">
					        <option value="${release.id}" <c:if test="${release.selected==true}">selected</c:if> >${release.label}</option>
					    </c:forEach>					
				</form:select>
			</td>
			<th style="text-align: right;">Week End Date</th>
			<td><form:input type="text" id="weekEndDate" class="textbox" path="weekEndDate" /></td>
		</tr>
	</table>
	
	<br/><br/>
	<input type="button" value="Download Report" onclick = "downloadDMSReport()"  /> 
	
 </form:form>


