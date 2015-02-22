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
						
						$("#vacationRequestSubmit").button().on("click",function(){
							$.ajax({
								type : "POST",
								url : "./addVacation.do",
								data :$("#vacationForm").serialize(),
								beforeSend : function() {
								},
								success : function(response) {
									alert('hello :'+response);
								},
								error : function(data) {},
								complete:function(data){
									
								}
							});
						});
						
						$('#calendar').fullCalendar({
							header: {
								left: 'prev,next today',
								center: 'title',
								right: 'month,basicWeek,basicDay'
							},
							defaultDate: '2015-02-12'
							
						});
						
					});
