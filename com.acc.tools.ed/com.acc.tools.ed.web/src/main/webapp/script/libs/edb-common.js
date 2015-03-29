var edb=(function($){
	
	var openWindow=function (action, width, height, name) {
	    var screenLeft=0, screenTop=0;

	    if(!name) name = 'MyWindow';
	    if(!width) width = 600;
	    if(!height) height = 600;

	    if(typeof window.screenLeft !== 'undefined') {
	        screenLeft = window.screenLeft;
	        screenTop = window.screenTop;
	    } else if(typeof window.screenX !== 'undefined') {
	        screenLeft = window.screenX;
	        screenTop = window.screenY;
	    }

	    var features_dict = {
	        toolbar: 'no',
	        titlebar:'no',
	        location: 'no',
	        directories: 'no',
	        left: screenLeft + ($(window).width() - width) / 2,
	        top: screenTop + ($(window).height() - height) / 2,
	        status: 'no',
	        menubar: 'no',
	        scrollbars: 'no',
	        resizable: 'no',
	        width: width,
	        height: height
	    };
	    features_arr = [];
	    for(var k in features_dict) {
	        features_arr.push(k+'='+features_dict[k]);
	    }
	    features_str = features_arr.join(',')

	    var win = window.open(action, 'AddQuestion', features_str);
	    win.document.title=name;
	    win.focus();
	    return false;
	}
	
	
	/*
	 * Edb Context Object to hold all application objects while moving from page to another page.
	 */
	var edbContextInstance = null;
	function initializeEdbContext() {
		 var contextAttributes=[];
		 function addEdbAttribute(key,value) {
			 contextAttributes[key]=value;
		 }
		 function getEdbAttribute(key) {
			 return contextAttributes[key];
		 }
		 function cleanContext(){
			 contextAttributes=[];
		 }
		 
		 return {
			 addAttribute : addEdbAttribute,
			 getAttribute : getEdbAttribute,
			 clean : cleanContext
		 };
	 }

	 function getInstance() {
		 if( ! edbContextInstance ) {
			 edbContextInstance = new initializeEdbContext();
		 }
		 return edbContextInstance;
	 } 
	 
	function constructJSONString(formFields,formObject) {
			
			var jsonObject={};
			
			for(var formField in formFields){
				var dataType=formFields[formField].dataType;
				if(dataType=="int" || dataType=="string"){
					var formFieldValue=formObject.find("*[name='"+formField+"']").val();
					if(formFieldValue!=undefined){
						//alert(formField+"|"+formFields[formField].dataType+"|"+formFieldValue);	
						if(formFieldValue){
							if(dataType=="string"){
								jsonObject[formField]=formFieldValue;
							} else if(dataType=="int"){
								jsonObject[formField]=parseInt(formFieldValue);
							} 
						}
					}
				} else if(dataType=="array"){
					var formFieldValue=formObject.find("*[id^='"+formField+"']");
					jsonObjectArray=[];
					formFieldValue.each(function(index, obj){
						jsonObjectArray.push(obj.value);
					});
					jsonObject[formField]=jsonObjectArray;
				}
				
			}

			return JSON.stringify(jsonObject);
		
	}
	
	return {
		openWindow:openWindow,
		getEDBContextInstance :getInstance,
		jsonString:constructJSONString
	};
})($);