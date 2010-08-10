(function($) {

$.extend(Tapestry.Initializer, {
	
    /**
     * Initialize jquery dialog popup on click of an element.
     */
    dialogLink: function(spec) {
        $('#' + spec.triggerId).click(function() {
            jQuery('#' + spec.dialogId).dialog('open');
			
			return false;
        });
    }
});
    
})(jQuery);






