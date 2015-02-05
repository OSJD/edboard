<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<tr>
  <td colspan="6" style="background-image: none;border-bottom-color: white;">
  	  <div style="width: 1000px;overflow: auto;">
		  <table style="width: 100%;" class="innertable1">
		    <tr>
			 <th rowspan="3">Resources</td>
			  <jstl:forEach var="months" items="${releasePlan.monthsNoOfDays}">
			     <th align="center" colspan="${months.value}">${months.key}</th>
			  </jstl:forEach>
			</tr>
			<tr>		   
			  <jstl:forEach var="weeks" items="${releasePlan.weeksAndDays}">		              
			     <th align="center" style="background-color: #f59c63;" colspan="${fn:length(weeks.value)}">${weeks.key}</th>
			  </jstl:forEach>	
			</tr>
			<tr>	  
			   <jstl:forEach var="weeks" items="${releasePlan.weeksAndDays}">
			       <jstl:forEach var="day" items="${weeks.value}">
			           <th style="width:40px;background-color: #f9bf9a;">${day}</th>
			       </jstl:forEach>
			  </jstl:forEach>
			</tr>
			 <jstl:forEach var="resourceHours" items="${releasePlan.resourcesAndHours}" varStatus="res">
			  <tr>	 	    	
			     <td id="resource${res.index}" >${resourceHours.key.label}</td>	
			       <input type="hidden" id="resId${res.index}" value="${resourceHours.key.id}"></input>      
			       <jstl:forEach var="hour" items="${resourceHours.value}" varStatus="days">
			         <c:choose>
		                <c:when test="${hour=='0'}">
		                     <td> <input id="resDayHour${res.index}${days.index}" type="text" size="1" value="" disabled="disabled"></input> </td>
		                </c:when>
		
		               <c:otherwise>
		                     <td> <input id="resDayHour${res.index}${days.index}" size="1" value="${hour}"></input> </td>
		               </c:otherwise>
		             </c:choose>
			          
			       </jstl:forEach>
			  </tr>
			 </jstl:forEach>
			 <tr>
			     <td>Planned Hours</td>
			     <jstl:forEach var="weekHour" items="${releasePlan.weeklyTotalHours}">	    
			           <c:set var="colSpan" value="${fn:split(weekHour, '~')}"/>	           
			           <td colspan="${colSpan[0]}">${colSpan[1]}</td>
			       </jstl:forEach>
			 </tr>
		  </table>	  
	  </div>
  </td>
</tr>
