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
	
	return {
		openWindow:openWindow
	};
})($);