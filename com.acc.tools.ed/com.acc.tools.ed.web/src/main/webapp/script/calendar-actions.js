	$(document)
			.ready(
					function() {
						
						var vacationRequestPopup=$("#vacationRequestPopup").dialog({
							autoOpen : false,
							height : 360,
							width : 430,
							modal : true,
							buttons : {
								"Submit Request" : function() {
									
								},
								Cancel : function() {
									vacationRequestPopup.dialog("close");
								},
							},

						});
						
						$("#vacationRequestBtn").on("click",function(){
							vacationRequestPopup.dialog("open");
						});
						
						$( "#vacationStartDate" ).datepicker({
							showOn: 'button',
							buttonText: 'Show Date',
							buttonImageOnly: true,
							buttonImage: 'resources/cal.gif',
							dateFormat: 'mm/dd/yy',
							constrainInput: true
						 });
						
						$( "#vacationEndDate" ).datepicker({
							showOn: 'button',
							buttonText: 'Show Date',
							buttonImageOnly: true,
							buttonImage: 'resources/cal.gif',
							dateFormat: 'mm/dd/yy',
							constrainInput: true
						 });
						
						$("#vacationType").on("change",function(){
							var vacationType=this.value;
							if(vacationType==-4){
								$( "input[name*='location']" ).attr("disabled",false);
							} else {
								$( "input[name*='location']" ).attr("disabled",true);
							}
								
						});
						
						$("#vacationRequestSubmit").button().on("click",function(){
							$.ajax({
								type : "POST",
								url : "./addVacation.do",
								data :$("#vacationForm").serialize(),
								beforeSend : function() {
								},
								success : function(response) {
									if(response=="success"){
										alert('Request submitted successfully!');
									}
									
								},
								error : function(data) {},
								complete:function(data){
									
								}
							});
						});
						

					});
