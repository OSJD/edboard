<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<%=request.getContextPath()%>/script/reports.js"></script>

<form:form action="downloadStatusReport.do" commandName="statusForm" id="statusForm">
	
	<table class="ebdtableheader">
		<tr>
			<th>Report Name</th>
			<td>
				<form:select id="reportsName" name="reportName" path ="reportName">
					<option value="0">---Please Select----</option>
					<option value="EmpMstr">Employee Master</option>
					<option value="RelMstr">Release Master</option>
				</form:select>
			</td>
			 <td style="background-image: none;background-color: white;border-width: 1px;border-style: solid;border-color: #999999;"><div style="width: 5px;"></div></td>

			<th>Report Format</th>
			<td><form:select id="reportFormat" class="textbox" path="reportFormat">
					<option value="0">---Please Select----</option>
					<option value="Pdf">PDF</option>
				</form:select></td>
			 
			<th style="text-align: right;">Start Date</th>
			<td><form:input type="text" id="reportStartDate" class="textbox" path="startDate" /></td>
			<th style="text-align: right;">End Date</th>
			<td><form:input type="text" id="reportEndDate" class="textbox" path="endDate" /></td>
			
		</tr>
	</table>
<!-- <a href="./downloadWsr.do" class="button" id="downloadWsr" style="width: 100px;">Download Report</a>  -->
<br/><br/>
<input type="button" value="Download Report" onclick = "downloadReportMaster()"  /> 
</form:form>

