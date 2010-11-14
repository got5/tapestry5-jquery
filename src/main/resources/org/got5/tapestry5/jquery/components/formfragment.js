(function( $ ) {

$.widget( "ui.formFragment", {
	options: {
		hide: true,
		showFunc : "blind",
		hideFunc : "blind"
	},

	_create: function() {
		this.element
			.addClass( "tapestry-formfragment" )

		// : used by jQuery so need to escape 
		this.hidden = $("#" + this.element.attr("id") + "\:hidden");
		
		var form = $(this.element).closest('form');
		

		// use to be iform.bind(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT, function()
		form.bind("submit", {element: this.element, hidden: this.hidden}, function(event)
		{
			// On a submission, if the fragment is not visible, then wipe out its
			// form submission data, so that no processing or validation occurs on the server.
			if (!event.data.element.is(":visible"))
				event.data.hidden.value = "";
			
		});
	},

	destroy: function() {
		this.element
			.removeClass( "tapestry-formfragment");
		
		$.Widget.prototype.destroy.apply( this, arguments );
	},

	hide : function() {
		this.element
			.filter(":visible")
			.hide(this.options.hideFunc);
	},

	hideAndRemove : function() {
		this.element.hide(this.options.hideFunc, {}, "normal", function() {
			this.element.remove();
		});
	},

	show : function() {
		this.element
			.not(":visible")
			.show(this.options.showFunc);
	},

	toggle : function()	{
		this.setVisible(!this.element.is(":visible"));
	},

	setVisible : function(visible) {
		if (visible) {
			this.show();
			return;
		}

		this.hide();
	},
	
	_setOption: function(option, value) {  
    	$.Widget.prototype._setOption.apply( this, arguments );  
  
	    switch (option) {  
	        case "setVisible":  
	            this.setVisible(value);  
	            break;
		}  
    }   

});

$.extend(Tapestry.Initializer, {
	
	formFragment: function ( specs ) {
		$("#" + specs ).formFragment();
	},
	
	linkTriggerToFormFragment : function(trigger, element) {
        trigger = $("#" + trigger);


        if (trigger.attr("type") == "radio") {
            $(trigger).closest("form").click(function() {
                $("#" + element).formFragment("setVisible", trigger.attr("checked"));
                //jQuery validator rules are not apply to input disabled
                if(!trigger.attr("checked"))
                	$("#" + element).find("input").attr("disabled", true);
                else
                	$("#" + element).find("input").removeAttr("disabled");

            });

            return;
        }

        trigger.click(function()
        {
            $("#" + element).formFragment("setVisible" , trigger.attr("checked"));
            //jQuery validator rules are not apply to input disabled
            if(!trigger.attr("checked"))
            	$("#" + element).find("input").attr("disabled", true);
            else
            	$("#" + element).find("input").removeAttr("disabled");
        });
    }
});

})( jQuery );
