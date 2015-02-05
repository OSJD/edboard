	$(document)
			.ready(
					function() {
						
						var addResourceDialog = $("#addemp-popup").dialog({
							autoOpen : false,
							height : 550,
							width : 650,
							modal : true,
							buttons : {
								"Add Resource" : function() {
									$.ajax({
										type : "POST",
										url : "./addEmpDetailsForm.do",
										data : 
											$("#addEmpDetailsForm").serialize(),
										beforeSend : function() {
										},
										success : function(response) {
											alert(response);
										},
										error : function(data) {

										},
										complete: function(data){
										}
									});

								},
								Cancel : function() {
									addResourceDialog.dialog("close");
								},
							},

						});
						
						$("#addResource").button().unbind("click").on("click", function() {
							addResourceDialog.dialog("open");
						});
						
	 });

	