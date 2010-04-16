(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        validate: function(field, specs) {
        
            $('#' + field).closest('form').validate({
                errorClass: "t-error"
            });
            
            $.each(specs, function(i, ruleSpecs) {
                jsRule = $.tapestry.validate.toJSRule(ruleSpecs[0])
                
                ruleAsString = '{"' + jsRule + '" :' + ((ruleSpecs[2] === undefined) ? 'true' : '"' + ruleSpecs[2] + '"') + ', "messages": {"' + jsRule + '":"' + ruleSpecs[1] + '"}}';
                
                $('#' + field).rules("add", $.parseJSON(ruleAsString));
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







