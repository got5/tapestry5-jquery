(function($) {
    function isEmpty(object) {

        for (var i in object) {
            if (object.hasOwnProperty(i)) { return false; }
        }

        return true;
    }

	$.extend(Tapestry.Initializer, {
		gmap: function(specs) {

		    // the plugin doesn't work if params is an empty object
		    if (isEmpty(specs.params)) {

		        $('#' + specs.id).gmap3();

		    }  else {

		        $('#' + specs.id).gmap3(specs.params);
		    }
        }
	});
})(jQuery);