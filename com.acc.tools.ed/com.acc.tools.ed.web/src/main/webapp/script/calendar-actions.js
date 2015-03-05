	$(document)
			.ready(
					function() {
						
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
						
						$("#vacationApproveSubmit").button().on("click",function(){
							$.ajax({
								type : "POST",
								url : "./approveVacation.do",
								data :$("#vacationStatusForm").serialize(),
								beforeSend : function() {
								},
								success : function(response) {
									if(response=="success"){
										alert('Request status updated successfully!');
									}
									vacationRequestPopup.dialog("close");
								},
								error : function(data) {},
								complete:function(data){
									
								}
							});
						});
						

	});
