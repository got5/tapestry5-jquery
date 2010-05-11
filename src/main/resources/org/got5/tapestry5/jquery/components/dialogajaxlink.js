(function($) {

$.extend(Tapestry.Initializer, {
    dialogAjaxLink: function(element, zoneId, dialogId, url) {
        var onOpen = function(event, ui) {
            $("#" + zoneId).tapestryZone("update", {
                url: url
            });
		};
		

        $("#" + element).click(function(e) {
			$('#' + dialogId).one("dialogopen", onOpen);
            $('#' + dialogId).dialog('open');
			
			return false;
        });
    }
});

})(jQuery);






