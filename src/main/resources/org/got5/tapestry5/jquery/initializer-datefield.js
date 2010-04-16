(function($){
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        dateField: function(specs){
            $("#" + specs.field).datepicker({
                firstDay: specs.localization.firstDay,
                gotoCurrent: true
            });
        }
    });
})(jQuery);


/*     { "dateField": [{


 "field": "datefield",


 "localization": {


 "firstDay": 0,


 "months": ["janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre", ""],


 "days": "lmmjvsd"


 },


 "formatURL": "/tapestry-jquery/jquerycalendar.datefield:format",


 "parseURL": "/tapestry-jquery/jquerycalendar.datefield:parse"


 }]


 }; */






