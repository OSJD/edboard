$(document)
.ready(
		function() {
			
			$(".vacationDatePicker").each(
			function()
			{
			var vacationId = this.id;	
			funtion(vacationid)(
			{
				showOn: 'button',
				buttonText: 'Show Date',
				buttonImageOnly: true,
				buttonImage: 'resources/cal.gif',
				dateFormat: 'mm/dd/yy',
				constrainInput: true
			})
			
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
			
			
			$( ".vctnEditStartDate" ).datepicker({
				minDate: 0,
				showOn: 'button',
				buttonText: 'Show Date',
				buttonImageOnly: true,
				buttonImage: 'resources/cal.gif',
				dateFormat: 'mm/dd/yy',
				constrainInput: true
			});

			$( ".vctnEditEndDate" ).datepicker({
				minDate: 0,
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

			$(".vacationApproveSubmit").button().unbind("click").on("click",function(){


				var vacationId=this.id;
				var status = $("#approvalType_"+vacationId+" option:selected").val();
				var approverComments=$("#VctnApproverComments_"+vacationId).val();
				//alert(approverComments);
				$.ajax({
					type : "POST",
					url : "./approveVacation.do",
					data : {
						vacationId:vacationId,
						status:status,
						approverComments:approverComments
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

			$(".vacationDeleteSubmit").button().unbind("click").on("click",function(){
				var vacationId=this.id;
				var vacationType = $("#vctnType_"+vacationId).val();

				$.ajax({
					type : "POST",
					url : "./deleteVacation.do",
					data : {
						vacationId:vacationId

					},
					success : function(response) {
						if(response=="success"){
							alert('Request status updated successfully!');
						}

					},
					error : function(data) {},
					complete:function(data){

					}
				});
			});


			$(".vacationEditSubmit").button().unbind("click").on("click",function(){


				var vacationId=this.id;
				var startDate = $("#vacationEditStartDate_"+vacationId).val();
				var endDate = $("#vacationEditEndDate_"+vacationId).val();
				var comments = $("#vctnComments_"+vacationId).val();

			//	alert('hi'+vacationId+startDate+ comments);

				$.ajax({
					type : "POST",
					url : "./updateVacation.do",
					data : {
						vacationId:vacationId,
						startDate:startDate,
						endDate:endDate,
						comments:comments
						
					},
					success : function(response) {
						if(response=="success"){
							alert('Request status updated successfully!');
						}
						
					},
					error : function(data) {},
					complete:function(data){

					}
				});
			});


		});
