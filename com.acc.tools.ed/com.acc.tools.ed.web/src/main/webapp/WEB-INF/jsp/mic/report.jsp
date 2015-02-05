<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@include file="Links.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<!-- DataTables -->
<!-- <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.1/js/jquery.dataTables.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.1/css/jquery.dataTables.css">   -->
<!-- jQuery -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!-- <script type="text/javascript" src="//cdn.datatables.net/1.10.1/js/jquery.dataTables.min.js"></script> -->
<script type="text/javascript">
// $(document).ready(function() {
//     $('#example').dataTable( {
//         "pagingType": "full_numbers"
//     } );
// } );
</script>


<script>


// 		$('#myTable')$('#example').DataTable();

	function onSelectChange() {
		
		
		$.post("<%=request.getContextPath()%>/report.do",
				{val:$('#weekSelect').val()},
				function(data,status){$('#mytable').html(data);
																										   }
			  );
/* 		  $('#example').dataTable( {
		        "pagingType": "full_numbers"
		    } );*/
	} 

</script>

</head>
<body>
<br>
<select id="weekSelect" onchange="javascript:onSelectChange()">
<option value="" selected="selected" > Select Week </option>
<%
String options=(String)request.getAttribute("select");
String opt[]=options.split(";");
for(int i=0;i<opt.length;i++)
{%>

<option value="<%=opt[i]%>"> Week <%=i+1 %> </option>
<%} %>
</select>
<br>
<br>

		<div id="mytable"></div>
		
		

		
</body>
</html>