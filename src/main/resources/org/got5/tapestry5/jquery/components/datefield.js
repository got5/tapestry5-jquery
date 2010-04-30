(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        dateField: function(specs) {
            $("#" + specs.field).datepicker({
                firstDay: specs.localization.firstDay,
                gotoCurrent: true
            });
        }
    });
})(jQuery);






