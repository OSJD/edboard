					<ul>
						<jstl:choose>
							<jstl:when test="${(edbUser.role =='MANAGER') || (edbUser.role =='Admin')}">
								<li><a href="#projectPlanTab">Project Management</a></li>
								<li><a href="./projectWork.do">Project Work</a></li>
								<li><a href="./resourceManagement.do">Resource Management</a></li>
							</jstl:when>
							<jstl:when	test="${(edbUser.role =='SUPERVISOR') || (edbUser.role =='Lead')}">
								<li><a href="#projectPlanTab">Project Management</a></li>
								<li><a href="./projectWork.do">Project Work</a></li>
								<li><a href="./resourceManagement.do">Resource Management</a></li>
							</jstl:when>
							<jstl:otherwise><!-- DEVELOPER -->
								<li><a href="./projectWork.do" id="projectWorkDisplay">Project Work</a></li>
							</jstl:otherwise>
						</jstl:choose>
					</ul>