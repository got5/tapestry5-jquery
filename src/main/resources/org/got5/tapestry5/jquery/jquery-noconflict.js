(function( $ ) {
	$.widget( "ui.tapestryZone", {
		options: {
	    	show: "highlight",
	    	update: "hightlight"
		},

		_create: function() {
			this.element
				.addClass( "tapestry-zone" )
		},

		destroy: function() {
			this.element
				.removeClass( "tapestry-zone");
			
			$.Widget.prototype.destroy.apply( this, arguments );
		},
	    update: function(specs) {
			$T(this.element.attr('id')).zoneManager.updateFromURL(specs.url);
	    }
	});

	$.extend(Tapestry.Initializer, {
		zone: function(spec) {
		if (!$.isPlainObject(spec)) {
	        spec = {
	            element: spec
	        };
	    }
	    $('#' + spec.element).tapestryZone();
	    new Tapestry.ZoneManager(spec);
	    }
	});

})(jQuery);
