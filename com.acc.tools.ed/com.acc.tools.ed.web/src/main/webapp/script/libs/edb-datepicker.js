$.widget("ui.edbdatepicker", {
		    _init: function() {
		        var $el = this.element;
		        $el.datepicker(this.options);
		        
		        if (this.options && this.options.trigger) {
		            $(this.options.trigger).bind("click", function () {
		                $el.datepicker("show");
		            });
		        }
		    }
});