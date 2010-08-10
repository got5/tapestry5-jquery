(function($) {
/** 
 * Container of functions that may be invoked by the Tapestry.init() function.
 */
$.extend(Tapestry.Initializer, {
    validate: function(/*field, */specs) {    	
        
        $.each(specs, function(i, ruleSpecs) {
            var field = i;
            $('#' + field).closest('form').validate({
                errorClass: "t-error"
            });
            
            $.each(ruleSpecs, function(j, ruleSpec) {
                var jsRule = $.tapestry.validate.toJSRule(ruleSpec[0]);
                var ruleAsString = '{"' + jsRule + '" :' + ((ruleSpec[2] === undefined) ? 'true' : '"' + ruleSpec[2] + '"') + ', "messages": {"' + jsRule + '":"' + ruleSpec[1] + '"}}';
                $('#' + field).rules("add", $.parseJSON(ruleAsString));
            });
            
        });
    }
});


/**
 * Tapestry static functions
 */
$.extend($.tapestry, {
    validate: {
        /**
         * Translate a tapestry validation rule into a JQuery validate rule
         */
        toJSRule: function(rule) {
            return $.tapestry.validate.rules[rule] ? $.tapestry.validate.rules[rule] : rule;
        },
        
        rules: {
            numericformat: "number"
        }
    }
});

})(jQuery);







