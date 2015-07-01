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
	 
		$("#rptproject").unbind("change").on("change", function() {
			
			var selectedProject=$("#rptproject").val();
			$.ajax({
				type : "POST",
				url : "./fetchReleases.do",
				data : {projectId:selectedProject},
				dataType : 'json',	
				
				beforeSend:function(){
				  },
				success : function(response) {
		
					$('#rptreleases')
					  	.find('option')
					  	.remove();
					$('#rptreleases').append('<option value="SR">Select Release</option>');
					for(var obj in response){
						$('#rptreleases').append('<option value='+response[obj].id+'>'+response[obj].label+'</option>');
					};
				},
				error : function(data) {	
					$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
				}
			});	
		});
		
	$("#rptprogram").unbind("change").on("change", function() {
			var selectedProgram=$("#rptprogram").val();
			$.ajax({
				type : "POST",
				url : "./fetchProjects.do",
				data : {programId:selectedProgram},
				dataType : 'json',	
				
				beforeSend:function(){
				  },
				success : function(response) {
		
					$('#rptproject')
					  	.find('option')
					  	.remove();
					$('#rptproject').append('<option value="SR">Select Project</option>');
					for(var obj in response){
						$('#rptproject').append('<option value='+response[obj].id+'>'+response[obj].label+'</option>');
					};
				},
				error : function(data) {	
					$("#mainContainer").html("Application error! Please call help desk. Error:"+data.status);
				}
			});	
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
