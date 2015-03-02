$(document)
.ready(
		function() {
	

	/**
	 * This function will be used to load sub tab pages
	 */	

	$(".subtabs").unbind("click").on("click",function(){
		
		
		
		var tabId=$(this).attr('id');
		var mainContainerId="#"+tabId.substring(0, 2)+"MainContainer";
		$("#"+tabId).parent().css({
			"background" : "white",
			"border" : "1px solid black",
			"border-bottom" : "none"
			} );

		$('#subtab-container > div > a').map(function() {
		   var allTabIds=this.id;
		   if(tabId!=allTabIds){
			   $("#"+allTabIds).parent().css({
					"background" : "#94DAF0",
					"border-top" : "none",
					"border-left" : "none",
					"border-bottom" : "1px solid black"
					}  );
		   }
		});

		var href=$(this).attr('action');
		$.ajax({
			type : "POST",
			url : href,
			success : function(xhr) {	
				$(mainContainerId).html(xhr);
				//temp fix
				$(mainContainerId).find("#subtab-container").remove();
			},
			error : function(xhr) {	
				$(mainContainerId).html("Application error! Please call help desk.");
			}
		});	
		return false;
	});

});
