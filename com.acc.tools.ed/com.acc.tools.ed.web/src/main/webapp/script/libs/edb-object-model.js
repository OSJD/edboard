var EdbDataModel=(function($){
	
	function constructJSONString(formFields,formData) {
		
	
		var jsonObject={};

		for(var field in formData){
			var fieldName=formData[field].name;
			if(formFields.hasOwnProperty(fieldName)){
				var fieldValue=formData[field].value;
				var fieldValueType=formFields[fieldName];
				if(fieldValue){
					if(fieldValueType=="string"){
						jsonObject[fieldName]=fieldValue;
					} else if(fieldValueType=="int"){
						jsonObject[fieldName]=parseInt(fieldValue);
					} 
 
				}
			}
		}

		return JSON.stringify(jsonObject);
	
	}
	return {
		jsonString:constructJSONString
	};
})($);