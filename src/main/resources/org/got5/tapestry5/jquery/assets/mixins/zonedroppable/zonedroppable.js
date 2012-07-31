(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
    	zoneDroppable: function(specs){
    		$( "#" + specs.id ).droppable(specs.params);
    		$( "#" + specs.id ).bind( "drop", function(event, ui) {
    			 var contexte=$(ui.draggable).data("contexte");
    			 var element = $("#" + specs.id);
				 
				 var parts = specs.BaseURL.split("?");
				 parts[0] += "/" + encodeURIComponent(contexte);
				 var urlWithContexte = parts.join("?");

    			 element.tapestryZone("update" , {url : urlWithContexte});
    		});
        }
    });
})(jQuery);

