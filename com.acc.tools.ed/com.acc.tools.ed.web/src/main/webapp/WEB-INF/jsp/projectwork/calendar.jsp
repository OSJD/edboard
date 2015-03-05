<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/jsp/includes/document-header.jsp"%>
<script src="<%=request.getContextPath()%>/script/projectwork-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/calendar-actions.js"></script>
			
<div>
	<table class="ebdtable" style="width: 100%; margin-top: 10px;">
		<tr>
			<th style="width:20px;"><a href="#" id="vacationRequestBtn"><img class="imgLink" 	alt="Vacation Request" src="./resources/addnews.gif" width="20px;"></a></th>
			<th>Resource Name</th>
			<th>Request Date</th>
			<th>Vacation Type</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Comments</th>
			<th>Status</th>
			<th>Supervisor Comments</th>
			<th>Action</th>
		</tr>
	</table>
</div>
<div id="viewVacationDetails"><!-- Project and Release View -->
	<div class="boxmsg border-boxmsg" style="width: 700px;color: red;">
	    Click on add icon to submit vacation request.Work with your team and identify your backup on your absence.
	    <b class="border-notch notch"></b>
	    <b class="notch"></b>
	</div>
</div>
			
			
