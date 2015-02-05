<%@page import="com.acc.tools.ed.integration.dto.LowLevelTask"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page  import="java.util.Map,java.util.Iterator,java.util.Set,java.util.List" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
    <%@page  import="com.acc.tools.ed.integration.dto.HrsResource"%>
	<jsp:include page="Links.jsp"></jsp:include>
	<%   response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" );   response.setDateHeader( "Expires", 0 );%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

.imgLink{border: none};
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="resources\jquery.dataTables.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="jquery-1.11.1.js"></script>
<script type="text/javascript" src="resources\jquery.dataTables.js"></script>




<script type="text/javascript">


var newwindow;
function poptastic(url)
{
	newwindow=window.open(url,'name','height=400,width=200');
	if (window.focus) {newwindow.focus()}
}

</script>
<!-- <script>


	$(function() {

		var moveLeft = 0;

		var moveDown = 0;

		$('a.popper').hover(function(e) {

			var target = ($(this).attr('data-popbox'));

			$(target).show();

			moveLeft = $(this).outerWidth();

			moveDown = ($(target).outerHeight() / 2);

		}, function() {

			var target = '#' + ($(this).attr('data-popbox'));

			$(target).hide();

		});

		$('a.popper')
				.mousemove(
						function(e) {

							var target = '#' + ($(this).attr('data-popbox'));

							leftD = e.pageX + parseInt(moveLeft);

							maxRight = leftD + $(target).outerWidth();

							windowLeft = $(window).width() - 40;

							windowRight = 0;

							maxLeft = e.pageX
									- (parseInt(moveLeft)
											+ $(target).outerWidth() + 20);

							if (maxRight > windowLeft && maxLeft > windowRight)

							{

								leftD = maxLeft;

							}

							topD = e.pageY - parseInt(moveDown);

							maxBottom = parseInt(e.pageY + parseInt(moveDown)
									+ 20);

							windowBottom = parseInt(parseInt($(document)
									.scrollTop())
									+ parseInt($(window).height()));

							maxTop = topD;

							windowTop = parseInt($(document).scrollTop());

							if (maxBottom > windowBottom)

							{

								topD = windowBottom - $(target).outerHeight()
										- 20;

							} else if (maxTop < windowTop) {

								topD = windowTop + 20;

							}

							$(target).css('top', topD).css('left', leftD);

						});

	});
</script>
 -->
 <script>
$(document).ready(function() {

	//alert($('.showonclick').text());
	$('.showonclick').hide();
	
	$('#clicktoshow').click(function () {
		
		$('.showonclick').show();
	});
	$(document).mouseup(function (e)
			{
			    var container = $(".showonclick");

			    if (!container.is(e.target) // if the target of the click isn't the container...
			        && container.has(e.target).length === 0) // ... nor a descendant of the container
			    {
			        container.hide();
			    }
			});
	
	$(".tablecolor:even").css("background-color", "#eeeeee");
	if($(".taskSelect").val()==null)
		$(".submitButton").hide();
		
	
	
    $('a.edit').click(function () {
    	
        var dad = $(this).parent().parent();
       
        var id=$(dad).attr("id");
       //	alert(dad.html());
     
       // alert(dad.children().eq(-3).html());
        var hours=dad.children().eq(-6).html();
        var date=dad.children().eq(-5).html();
        var status=dad.children().eq(-4).html();
        var comment=dad.children().eq(-3).html();
       // alert(hours+" "+date+" "+status+ " "+comment);
        var one="<td align=\"center\" style=\"width: 11%;\"> <input type=\"hidden\" size=\"5\" name=\"id\" value=\""+id+"\"><input size=\"5\" name=\"hours\" value=\""+hours+"\"></td>";
        var two="  ";
        var three="<td align=\"center\" style=\"width: 11%;\">"
        		+"<input type=\"hidden\"size=\"12\" name=\"date\" value=\""+date+"\">"
        		+"<select name='status' selected=\""+status+"\"> "
        			+"<option value='Not Yet Started'>Not Yet Started</option>"
        			+"<option value='Design Phase'>Design Phase</option>"
        			+"<option value='In Progress' selected='selected'>In Progress</option>"
        			+"<option value='Completed'>Completed</option>"
        			+"<option value='Halted'>Halted</option></select>";
	/* </optgroup>
	<optgroup label="Delayed Tasks">
		
		<jstl:forEach items="${delayedTasks}" var="dT">
			<option value="${dT.llid}">${dT.name}</option>
		</jstl:forEach>
	</optgroup></select> */
	/* <input size=\"15\" name=\"status\" value=\""+status+"\"> </td>"; */
        var four="<td align=\"center\" style=\"width: 11%;\"> <input type='textbox' size=\"15\" name=\"comment\" value=\""+comment+"\"> </td>";
        dad.children().eq(-6).replaceWith(one);
       // dad.children().eq(2).replaceWith(two);
        dad.children().eq(-4).replaceWith(three);
        dad.children().eq(-3).replaceWith(four);
        dad.children().eq(-2).replaceWith("<td align=\"center\" style=\"width: 11%;\"><input type='image' src='save.gif' alt='resources\submit' width='20' height='20' /> </td>")
       // dad.replaceWith(newTR);
        
    });


});
</script>

</head>
<body>
<!-- 

<div id="pop1" class="popbox">

    <h2>Success!</h2>

    <p>This is an example popbox.</p>

</div>

<div id="pop2" class="popbox">

    <h2>Danger!</h2>

    <p>Don't let this popbox go off the screen!</p>

</div>
 -->
 

 

 <!-- 

This is a popbox test.  <a class="popper" href="#" data-popbox="requirementPopup.jsp">Hover here</a> to see how it works.  You can also hover <a class="popper" href="#" data-popbox="pop2">here</a> to see a different example.
 -->
	<%
/* Map<Integer,String> tasks=((Map<Integer,String>) request.getAttribute("subTasks"));

Set<Integer> taskIds=tasks.keySet();
Iterator i=tasks.entrySet().iterator(); */
String role=(String)session.getAttribute("role");
String tl="TL";
String am="AM";
String man="manager";
String emp="";
%>
	<%
List<List<HrsResource>> weekEntries=(List<List<HrsResource>>)request.getAttribute("allEntries"); 


int page_number =Integer.parseInt( session.getAttribute("page_number").toString());

%>

				
				<%-- <jstl:forEach items="${allEntries}" var="weekEntry">
				
					<jstl:forEach items="${weekEntry}" var="hrsResource">
						date: ${hrsResource.date}
					
					</jstl:forEach>
				</jstl:forEach> --%>


	 	
	 	<h4 align="right" id="clicktoshow" >>Completed Tasks</h4>
	 	<div class="showonclick">
		<table align="right" border="1" cellpadding="2" 
			style="display: table;" align="left" >
		
			<tr>
		
			</tr>
			
			<tr class="tablecolor">
				<td align="center" >Task</td>
				<td align="center" >Start Date</td>
				<td align="center" >End Date</td>
			</tr>
				
			
			<jstl:forEach items="${completedTasks}" var="cT" varStatus="index">
			
				<tr>
					<td>${cT.name}</td>
					<td>${cT.stDate}</td>
					<td>${cT.endDate}</td>
				</tr>
			</jstl:forEach>
			
		</table>
</div>  

	<%-- 	<table align="right">
			<tr>
				<td colspan="3">Delayed Tasks</td>
			</tr>
			<tr>
				<td>Task</td>
				<td>Start Date</td>
				<td>End Date</td>
			</tr>
			<jstl:forEach items="${delayedTasks}" var="dT">
				<tr>
					<td>${dT.name}</td>
					<td>${dT.stDate}</td>
					<td>${dT.endDate}</td>
				</tr>
			</jstl:forEach>
		</table>
--%>
	
	<div>
		
		<br > <br><br>
		<h4 >Work Done Today: </h4>
		<f:form commandName="hrsResource" action="addHours.do" method="post">
			<table>
				<tr>
					<th align="left">Task Working on:</th>
					
					
<!-- 					// code above pulls tasks of (logged in emp, all emp with t.l who is logged in, -->
<!-- 					 or all tasks assigned for manager logged in) -->
					<td><f:select class="taskSelect" path="Actl_Tsk_Id" name="taskId">
							
							<optgroup label="Current Tasks">

								<jstl:forEach items="${currentTasks}" var="cT">
									<option value="${cT.llid}">${cT.name}-${cT.desc} </option>
								</jstl:forEach>
							</optgroup>
							<optgroup label="Delayed Tasks">
								
								<jstl:forEach items="${delayedTasks}" var="dT">
									<option value="${dT.llid}">${dT.name}-${dT.desc}</option>
								</jstl:forEach>
							</optgroup>
			
<!-- 			// code to pull only the tasks assigned to the logged in person, if its t.l it would'nt show any task becz he isnt given any work in resr_actl_tsk -->
			
				<%-- <% while(i.hasNext())
	{
	Map.Entry pairs=(Map.Entry)	i.next();

	%>
				<f:option value="<%=pairs.getKey() %>"><%= pairs.getValue() %>
				</f:option>
				<%
	}
	%> --%>

						</f:select></td>
				</tr>
				<tr>
					<th align="left">Hours Worked:</th>
					<td><f:input path="hrs" name="hrs" /></td>
				</tr>
				<tr>
					<th align="left">Status:</th>
					<td><f:select path="status" >
							<option value="Not Yet Started">Not Yet Started</option>
							<option value="Design Phase">Design Phase</option>
							<option value="In Progress" selected="selected">In Progress</option>
							<option value="Completed">Completed</option>
							<option value="Halted">Halted</option>
						</f:select></td>
				</tr>
				<tr>
					<th align="left">Comments:</th>
					<td><f:textarea path="comment" /></td>
				</tr>
				<tr>
					<th></th>

					
					<td><input class="submitButton" type="submit" value="Save" /></td>

					
				</tr>
			</table>
		</f:form>
	</div>

	<table align="right"><tr>
	<%
		for (int i=weekEntries.size(); i >=1 ; i--) {
			int loop=weekEntries.size()-i;
	%>
	<jstl:url var="link" value="nextPage.do">
	<jstl:param name="page_number" value="<%=emp+loop%>"></jstl:param>
	</jstl:url>
	<td>
	<%
	
	System.out.print("i="+i);
	List<HrsResource> fetchedWeek=weekEntries.get(loop);
	HrsResource hrsR=fetchedWeek.get(0);
	String weekLabel=hrsR.getWeek();
	%>
	<a href="${link}" ><%=weekLabel %>
	</a></td><td width="10px" > </td>
	<%} %>
	</tr>
	</table>


	<table border=1 cellpadding="2" align="center"
		style="display: table; width: 100%;" id="myTable">


		<tr style="width: 100%; display: table; text-align: left;">
			<jstl:if
				test="<%=role.equalsIgnoreCase(tl)|| role.equalsIgnoreCase(am)||role.equalsIgnoreCase(man) %>">
				<th align="center" style="width: 11%;">Enterprise Id</th>
			</jstl:if>

			<th scope="col" align="center" style="width: 11%;">Task</th>
			<th scope="col" align="center" style="width: 11%;">Description</th>

			<th scope="col" align="center" style="width: 11%;">No.Of Hours</th>

			<th scope="col" align="center" style="width: 11%;">Filling Date</th>

			<th scope="col" align="center" style="width: 11%;">Status</th>

			<th scope="col" align="center" style="width: 11%;">Comment</th>
			<th scope="col" align="center" style="width: 11%;">Edit</th>
			<th scope="col" align="center" style="width: 11%;">Delete</th>
		</tr>
	</table>
	<form action="edit.do">
		<table border=1 cellpadding="2" align="center"
			style="display: table; width: 100%;">
			<%
				if (weekEntries.size() > 0) {
			%>
			<tbody
				style="overflow: auto; height: 200px; float: left; width: 100%;">
				<jstl:set var="weekSize" value="<%=weekEntries.size()%>">
				</jstl:set>
				<%-- 					<jstl:out value="${weekSize}"></jstl:out> --%>


				<jstl:catch var="catchException"> 
				<jstl:set var="oneWeek" value="<%=weekEntries.get(page_number)%>"></jstl:set>
				</jstl:catch>
				<jstl:choose>
			
				
				<jstl:when test = "${catchException != null}">
  				<tr>
  				----
  				<td width="100%" align="center">There are no Entries for the week selected. </td> </tr>
				</jstl:when>
				

				<%-- 					<jstl:out value="${fn:length(allEntries)}"></jstl:out> --%>
				<jstl:otherwise>

				<jstl:forEach items="${oneWeek}" var="i">
					<tr class="tablecolor" id="${i.actl_frn_id}"
						style="width: 100%; display: table; text-align: left;">
						<jstl:if
							test="<%=role.equalsIgnoreCase(tl)
								|| role.equalsIgnoreCase(am)
								|| role.equalsIgnoreCase(man)%>">

							<td align="center" style="width: 11%;">${i.enterprise_id}</td>
						</jstl:if>
						<td align="center" style="width: 11%;"><jstl:url var="url"
								value="requirement.do">
								<jstl:param name="id" value="${i.actl_frn_id}">
								</jstl:param>
								<jstl:param name="name" value="${i.taskName}">
								</jstl:param>
							</jstl:url> <a href="javascript:poptastic('${url}');">${i.taskName}</a></td>
						<td align="center" style="width: 11%;">${i.desc }</td>
						<td align="center" style="width: 11%;">${i.hrs }</td>

						<td align="center" style="width: 11%;">${i.date}</td>
						<td align="center" style="width: 11%;">${i.status}</td>
						<td align="center" style="width: 11%;">${i.comment}</td>
						<td align="center" style="width: 11%;"><a class="edit"
							href="#"><img class="imgLink" src="resources\edit.gif" width="20" height="20"></a></td>
						<td align="center" style="width: 11%;"><jstl:url
								var="deleteUrl" value="delete.do">
								<jstl:param name="actl_frn_id" value="${i.actl_frn_id}">
								</jstl:param>
								<jstl:param name="date" value="${i.date}">
								</jstl:param>
							</jstl:url> <a href="${deleteUrl}"><img class="imgLink" src="resources\ico_delete.gif" width="20" height="20"></a></td>


					</tr>
				</jstl:forEach>
			</jstl:otherwise> </jstl:choose>
			</tbody>
			<%
				}
			%>

		</table>
	</form>
	

	
	
</body>
</html>