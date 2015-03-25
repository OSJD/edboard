var EdbDataModel=(function($){
	
	function constructJSONString(formFields,formData) {
		
/*		var componentId,
			taskName,
			taskDesc,
			taskHrs,
			taskType,
			taskStatus,
			taskReviewUser;
		function componentId(componentId){
			this.componentId=componentId;
		}
		
		function taskName(taskName){
			this.taskName=taskName;
		}*/
		
		var jsonAttributes={};

		for(var field in formData){
			var fieldName=formData[field].name;
			if(formFields.hasOwnProperty(fieldName)){
				var fieldValue=formData[field].value;
				alert(fieldValue);
				if(fieldValue!=0){
					formAttributes[fieldName]=fieldValue;
					alert(fieldName+"|"+fieldValue);
				}
			}
		}

		return JSON.stringify(jsonAttributes);
	
	}
	return {
		jsonString:constructJSONString
	};
})($);