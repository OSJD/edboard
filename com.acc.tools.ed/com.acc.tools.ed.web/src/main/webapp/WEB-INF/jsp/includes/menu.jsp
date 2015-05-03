					<ul>
						<jstl:choose>
							<jstl:when test="${(edbUser.role =='MANAGER') || (edbUser.role =='Admin')}">
								<li><a href="#projectPlanTab">Project Management</a></li>
								<li><a href="#projectWorkTab">Project Work</a></li>
								<li><a href="#resourceManagementTab">Resource Management</a></li>
							</jstl:when>
							<jstl:when	test="${(edbUser.role =='SUPERVISOR') || (edbUser.role =='Lead')}">
								<li><a href="#projectPlanTab">Project Management</a></li>
								<li><a href="#projectWorkTab">Project Work</a></li>
								<li><a href="#resourceManagementTab">Resource Management</a></li>
							</jstl:when>
							<jstl:otherwise><!-- DEVELOPER -->
								<li><a href="#projectWorkTab" id="projectWorkDisplay">Project Work</a></li>
							</jstl:otherwise>
						</jstl:choose>
					</ul>