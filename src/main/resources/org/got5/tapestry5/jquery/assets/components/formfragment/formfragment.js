(function( $ ) {

	$.widget( "ui.formFragment", {
		options: {
			hide: true,
			showFunc : "blind",
			hideFunc : "blind"
		},

		_create: function() {
			this.element.addClass( "tapestry-formfragment" )

			this.hidden = $("#" + this.element.id + ":hidden");

			var form = $(this.hidden).closest('form');

			form.bind(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT, function(){
				// On a submission, if the fragment is not visible, then wipe out its
				// form submission data, so that no processing or validation occurs on the server.
				if (this.element.is(":visible") != undefined)
					this.hidden.get(0).value = "";
			});
		},

		destroy: function() {
			this.element.removeClass( "tapestry-formfragment");

			$.Widget.prototype.destroy.apply( this, arguments );
		},

		hide : function() {
			this.element.filter(":visible").hide(this.options.hideFunc);
		},

		hideAndRemove : function() {
			this.element.hide(this.options.hideFunc, {}, "normal", function() {
				this.element.remove();
			});
		},

		show : function() {
			this.element.not(":visible").show(this.options.showFunc);
		},
	
		toggle : function() {
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

T5.extendInitializers(function(){
	
	function init(spec) {
		$("#" + spec.element ).formFragment();
	}
	
	function linkTriggerToFormFragment(spec) {
		var trigger = spec.triggerId;
		var element = spec.fragmentId;
		var invert = spec.invert;

        trigger = $("#" + trigger);
        
        var update = function() {
        	var checked = trigger.is(':checked');
        	var makeVisible = checked == !spec.invert;
            $("#" + element).formFragment({"setVisible" : makeVisible});
        };

        /* Let the event bubble up to the form level. */
        if (trigger.attr("type") == "radio") {
            $(trigger).closest("form").click(update);
            return;
        }

        /* Normal trigger is a checkbox; listen just to it. */
        trigger.click(update);
    }
	return {
		formFragment : init, 
		linkTriggerToFormFragment : linkTriggerToFormFragment
	}
});

})( jQuery );
