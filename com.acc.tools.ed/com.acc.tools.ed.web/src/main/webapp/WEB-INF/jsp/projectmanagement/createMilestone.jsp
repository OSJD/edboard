<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.acc.tools.ed.integration.dto.ProjectForm" %>

<h4 align="center">Create New Milestone</h4>
	<table align="center">

		<form action="createdMilestone.do" method="post">
			<tr>
				<td>Milstone Name:</td>
				<td><input name="mlname" /></td>
			</tr>
			<tr>
				<td>Start Date:</td>
				<td><input name="sdate" class="datepicker" /></td>
			<tr>
				<td>End Date:</td>
				<td><input name="edate" class="datepicker" /></td>
			</tr>
			<tr>
				<td>Project :</td>
				<td><select name="proj_id">
						<c:forEach items="${projects}" var="project">
							<option value="${project.id}">${project.name}</option>

						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="submit" /></td>
			</tr>
		</form>
	</table>

	<table id="example" class="display" cellspacing="0" width="100%" >
		<thead>
	
		<tr>
			<th>Milestone Name</th>
			<th>Project Name</th>
			<th>Project manager name</th>
			<th>Start Date</th>
			<th>End Date</th>
		</tr>
		</thead>
		<tbody  style="overflow-y:scroll; height:100px;">
		<%
			String milestones[] = ((String) request.getAttribute("listMilestone")).split(";");
			for (int i = 1; i <= milestones.length; i++) {
		%><tr>
			<%
				String milestoneDetail[] = milestones[i - 1].split(",");
					for (int j = 1; j <= milestoneDetail.length; j++) {
			%><td>
					<%=milestoneDetail[j-1] %>
			</td>
			<%
				}
			%>
		</tr>
		<%
			}
		%>
