
<%
String table=(String)request.getAttribute("table");
String arr[]=table.split(";");
%>
<table id="example" class="display" cellspacing="0" width="100%" align="center" style="border:1px solid black;">
		<tr>
			<th align="center" colspan="5">Tasks which have been completed or are in progress as of this week </th>
		</tr>
		<tr>
			<th align="center">Employee </th><th align="center">Task Done </th><th align="center">Status </th><th align="center">Comments </th><th>Completed On </th>
		</tr>
<%
for(int i=0;i<arr.length;i++)
{%>

<tr>
<%
	String arr2[]=arr[i].split(",");

	for(int j=0;j<arr2.length;j++)
	{ 
		%>
		
		<td align="center"><%=arr2[j] %>
		</td>
		<%
	}
	
}

%>
</tr>
</table>
<br>
<br>
<br>
<br>

<%
String table2=(String)request.getAttribute("table2");
String arrNS[]=table2.split(";");
%>
<table id="example" class="display" cellspacing="0" width="100%" align="center" style="border:1px solid black;">
		<tr>
			<th align="center" colspan="5">Tasks Not Yet Started which should have been started before or in this week </th>
		</tr>
		<tr>
			<th align="center">Employee </th><th>Description </th><th align="center">Task Name </th><th align="center">Status </th><th align="center">Start Date </th>
		</tr>
<%
for(int i=0;i<arrNS.length;i++)
{%>

<tr>
<%
	String arrNS2[]=arrNS[i].split(",");

	for(int j=0;j<arrNS2.length;j++)
	{ 
		%>
		
		<td align="center"><%=arrNS2[j] %>
		</td>
		<%
	}
	
}

%>
</tr>
</table>
