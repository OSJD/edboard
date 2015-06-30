$(document).ready(
		function() {
		$("#reportStartDate").attr('disabled','disabled');
		$("#reportEndDate").attr('disabled','disabled');
	
	 $("#reportsName").unbind("change").on("change",function(){
			
			if($("#reportsName").val()=='RelMstr'){
				$( "#reportEndDate" ).datepicker( "option", "disabled", false );
				$( "#reportStartDate" ).datepicker( "option", "disabled", false );
				$("#reportEndDate").removeAttr('disabled');
				$("#reportStartDate").removeAttr('disabled');
				$("#reportStartDate").datepicker({
				 	showOn: 'button',
					buttonText: 'Show Date',
					buttonImageOnly: true,
					buttonImage: 'resources/cal.gif',
					dateFormat: 'mm/dd/yy',
					constrainInput: true,
					changeMonth: true, 
					changeYear: true 
			 }); 
			 $("#reportEndDate").datepicker({
				 	showOn: 'button',
					buttonText: 'Show Date',
					buttonImageOnly: true,
					buttonImage: 'resources/cal.gif',
					dateFormat: 'mm/dd/yy',
					constrainInput: true,
					changeMonth: true, 
					changeYear: true 
			 });
				
			} else if(($("#reportsName").val()=='EmpMstr') || ($("#reportsName").val()=='0') ) {
				$("#reportStartDate").val('');
				$("#reportEndDate").val('');
				$("#reportStartDate").attr('disabled','disabled');
				$("#reportEndDate").attr('disabled','disabled');
				$( "#reportEndDate" ).datepicker( "option", "disabled", true );
				$( "#reportStartDate" ).datepicker( "option", "disabled", true );
			}
			
		});
});




function downloadReportMaster(){
	
 	var lReportType= $("#reportsName").val();
	var lStartDate = $("#reportStartDate").val();
	var lEndDate = $("#reportEndDate").val();
	var lReportFormat = $("#reportFormat").val();
	
	
	if((lReportType == 'EmpMstr')){
		if((lReportType != '0') && (lReportFormat != '0')){
			$('#statusForm').submit();
		}else{
			alert("Please enter the complete details");
			
		}
		
	}else if(lReportType == 'RelMstr'){
		if((lReportType != '0') && (lStartDate != '') && (lEndDate != '') && (lReportFormat != '0')){	
			$('#statusForm').submit();
		}else{
			alert("Please enter the complete details");
	}
	}else{
			alert("Please enter the complete details");
			
		}
	
		
		
}
