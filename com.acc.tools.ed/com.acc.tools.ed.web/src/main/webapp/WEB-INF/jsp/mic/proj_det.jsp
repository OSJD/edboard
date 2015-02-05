<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<form  method="post" >


<table>
<tr bgcolor="#66CC99">
					<th scope="col">MileStone Release Name</th>
					<th scope="col">MileStone Start Date</th>
					<th scope="col">MileStone End Date</th>
					<th scope="col">Project Manager Name</th>
					<th scope="col">Project Supervisor Name</th>
					<th scope="col">Client Name</th>
					<th scope="col">Client Start Date</th>
					<th scope="col">Client Type</th>
					</tr>
<c:forEach items="${allEvents}" var="event" >

					<tr >
					<td scope="col">
					<c:out value="${event[0]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[1]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[2]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[3]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[4]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[5]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[6]}" ></c:out>
					</td>
					<td scope="col">
					<c:out value="${event[7]}" ></c:out>
					</td>
					</tr>
			</c:forEach> 
			</table>


</form>
</body>
</html>