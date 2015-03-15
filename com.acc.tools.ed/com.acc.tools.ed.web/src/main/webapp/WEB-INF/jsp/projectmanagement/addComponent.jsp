<div id="componentPopup" title="Add Component to Developer">
	<table class="ebdtable">
		<tr>
			<th style="width: 140px;">Component Name</th>
			<td>
				<select name="componentName" id = "componentName" class="text">
					<option value="-1">---Select Component---</option>
					<jstl:forEach var="component" items="${viewProjRelDetails.releases[0].components}">
					    <jstl:if test="${not empty component.componentName}">
							<option value="${component.componentName}" id="compName">${component.componentName}</option>
						</jstl:if>
					</jstl:forEach>
					<option value="0">Create New Component</option>
				</select>
				<div id="newComp" style="display:none"><input name="newComponent" id="newComponent" type="text" class="textbox" style="width: 150px;"></div>
			</td>
			<th style="width: 140px;">Component Phase</th>
			<td>
				<select name="componentPhase" id="componentPhase">
					<option value="0">Select Phase</option>
					<jstl:forEach var = "phase" items="${viewProjRelDetails.phases}">
						<jstl:choose>
							<jstl:when test="${phase.trim() =='1'}">
								<option value="1">Analysis</option>
							</jstl:when>
							<jstl:when test="${phase.trim() =='2'}">
								<option value="2">Design</option>
							</jstl:when>
							<jstl:when test="${phase.trim() =='3'}">
								<option value="3">Build</option>
							</jstl:when>
							<jstl:when test="${phase.trim() =='4'}">
								<option value="4">Test</option>
							</jstl:when>
							<jstl:otherwise>
								<option value="5">Support</option>
							</jstl:otherwise>
						</jstl:choose>
					</jstl:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>Functional Desc</th>
			<td colspan="3"><textarea id="functionalDesc" style="overflow: auto; resize: none" rows="6"
					cols="80" class="textarea"></textarea></td>
		</tr>
		<tr>
			<th>Start Date</th>
			<td><input type="text" id ="compStartDate" name="compStartDate" class="textbox" /></td>
			<th style="width: 70px;">End Date</th>
			<td><input type="text" id="compEndDate" name="compEndDate" class="textbox" /></td>
		</tr>
		<tr>
			<th>Resource</th>
			<td colspan="3">
				<select id = "compResourceList" name="compResourceList" class="textbox" >
					<option value="0">Select Resource</option>		
					<jstl:forEach items="${viewProjRelDetails.resources}" var="resource">
					        <option value="${resource.id}" <jstl:if test="${resource.selected==true}">selected</jstl:if> >${resource.label}</option>
					 </jstl:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>Work Description</th>
			<td colspan="3"><textarea id="workDesc" style="overflow: auto; resize: none" rows="6"
					cols="80" class="textarea"></textarea></td>
		</tr>
	</table>
</div>