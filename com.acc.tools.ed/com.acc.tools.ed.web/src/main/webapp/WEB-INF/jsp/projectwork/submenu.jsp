<script src="<%=request.getContextPath()%>/script/submenu-actions.js"></script>
<div class="subtab-container" id="subtab-container" style="clear: both;">
	 	<div style="text-align:center; height:18px; width: 150px;border-right: 1px solid black;margin-left:10px;margin-top: 7px;background: white;border: 1px solid black;border-bottom: none;float: left;">
			<a href="#" action="./myTasks.do" id="pwsubtab1" class="subtabs">My Tasks</a>
		</div>
		<jstl:if	test="${(edbUser.role =='SUPERVISOR') || (edbUser.role =='Lead')}">
			 <div style="text-align:center; height:18px; width: 100px;border-right: 1px solid black;margin-top: 7px;float: left;">
				<a href="#" action="./teamTasks.do" id="pwsubtab2" class="subtabs">Team Tasks</a>
			</div> 
		</jstl:if>
		<div style="text-align:center; height:18px; width: 100px;border-right: 1px solid black;margin-top: 7px;float: left;">
			<a href="#" action="./calendar.do" id="pwsubtab3" class="subtabs">Calendar</a>
		</div>	
		<div style="text-align:center; height:18px; width: 120px;border-right: 1px solid black;margin-top: 7px;float: left;">
			<a href="#" action="./announcements.do" id="pwsubtab4" class="subtabs">Announcement</a>
		</div>
		<jstl:if test="${(edbUser.role =='Admin')}">
			<div style="text-align:center; height:18px; width: 100px;border-right: 1px solid black;margin-top: 7px;float: left;">
				<a href="#" action="./settings.do" id="pwsubtab5" class="subtabs">Settings</a>
			</div>
		</jstl:if>
</div>	