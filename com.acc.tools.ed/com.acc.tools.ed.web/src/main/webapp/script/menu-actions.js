$(document)
.ready(
		function() {
			$("#resource_management").click(function(){
			var href=$(this).attr('action');
			$.ajax({
			type : "POST",
			url : href,
			success : function(response) {
				$("#rmMainContainer").html(response);
			},
			error : function(xhr) {	
				$(mainContainerId).html("Application error! Please call help desk.");
			}
		});	
		return false;
		});
		});
	
	


