<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- styles -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-login.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-main.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-login.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-message-box.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.dataTables.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.sceditor.default.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui-timepicker.css"/>

<!-- javascripts -->
<script src="<%=request.getContextPath()%>/script/libs/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery-ui-1.11.0.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery.form.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath()%>/script/login-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/submenu-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/projectmanagement-actions.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery.sceditor.bbcode.min.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/jquery-ui-timepicker.js"></script>
<script src="<%=request.getContextPath()%>/script/libs/announcement-actions.js"></script>


<!-- datepicker for all pages -->
<script>  
 $(function() {
       $( "#startDate" ).datepicker({
              onSelect: function (date) {
             var date2 = $('#startDate').datepicker('getDate');
             date2.setDate(date2.getDate() + 1);
             $('#endDate').datepicker('setDate', date2);
             //sets minDate to dt1 date + 1
             $('#endDate').datepicker('option', 'minDate', date2);
         }
       });
       $( "#endDate" ).datepicker({
              onClose: function () {
             var dt1 = $('#startDate').datepicker('getDate');
             var dt2 = $('#endDate').datepicker('getDate');
             if (dt2 <= dt1) {
                 var minDate = $('#endDate').datepicker('option', 'minDate');
                 $('#endDate').datepicker('setDate', minDate);
             }
         }
       });
              
       });  
 </script>
