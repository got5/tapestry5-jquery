(function( $ ) {

$.widget( "ui.tapestryLinkSubmit", {
	options: {
	},

	_create: function() {
		this.form = $("#" + this.options.form);
		
		this.element.click(function() {
			$(this).tapestryLinkSubmit("clicked");

	        return false;
		});
	},

	destroy: function() {
		this.element
			.removeClass( "tapestry-palette");
		
		$.Widget.prototype.destroy.apply( this, arguments );
	},
        
    _createHidden: function () {    
	    var hidden = $("<input></input>").attr({ "type":"hidden",
	        "name": this.element.id + ":hidden",
	        "value": this.element.id});
	
	    this.element.after(hidden);
    },
	
	clicked: function() {
		var	onsubmit = this.form.get(0).onsubmit;

        this._createHidden();

        this.form.get(0).submit();	
	}
});

$.extend(Tapestry.Initializer, {
	linkSubmit: function (spec) {
		$("#" + spec.clientId).tapestryLinkSubmit({
			form: spec.formId
		});
	}
});

})( jQuery );


