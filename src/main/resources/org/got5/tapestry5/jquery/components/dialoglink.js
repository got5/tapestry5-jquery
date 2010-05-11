(function($) {

$.extend(Tapestry.Initializer, {
	
    /**
     * Initialize jquery dialog popup on click of a elt.
     */
    dialogLink: function(triggerId, dialogId) {
        $('#' + triggerId).click(function() {
            jQuery('#' + dialogId).dialog('open');
			
			return false;
        });
    }
});
    
})(jQuery);






