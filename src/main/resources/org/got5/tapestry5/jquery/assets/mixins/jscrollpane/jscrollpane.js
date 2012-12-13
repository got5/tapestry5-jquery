(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        jscrollpane: function(spec){
            $("#" + spec.id).jScrollPane(spec.params);
        }
    });
})(jQuery);

