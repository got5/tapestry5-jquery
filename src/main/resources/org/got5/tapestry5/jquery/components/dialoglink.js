(function($) {

$.extend(Tapestry.Initializer, {
	
    /**
     * Initialize jquery dialog popup on click of an element.
     */
    dialogLink: function(triggerId, dialogId) {
    	console.debug(arguments);
        $('#' + triggerId).click(function() {
            jQuery('#' + dialogId).dialog('open');
			
			return false;
        });
    }
});
    
})(jQuery);






