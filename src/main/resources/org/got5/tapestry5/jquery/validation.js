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

var Tapestry.Validator={};
$.validator.addMethod("regexp",function( value, element, param ) {
	var r = new RegExp(param);
    return this.optional(element) || r.test(value);
},"The text you entered doesn't match the regular expression");

$.validator.addMethod("isnull",function( value, element ) {
    return this.optional(element) || value == null;
},"The value is not null");

$.validator.addMethod("size",function( value, element, param ) {
	
	if (Object.isString(value))
		return this.optional(element) || value.length > param.min || value.length < param.max;
	else if (Object.isArray(value))
	{	
		if(element.tagName == "SELECT")
		{
			var selectedOptions = Element.childElements(this).size();
			return this.optional(element) || selectedOptions > param.min || selectedOptions < param.max;
		}
	}
    return this.optional(element);
},"The text you entered doesn't have the right size");

$.extend($.tapestry, {
    validate: {
        /**
         * Translate a tapestry validation rule into a JQuery validate rule
         */
        toJSRule: function(rule) {
            return $.tapestry.validate.rules[rule] ? $.tapestry.validate.rules[rule] : rule;
        },
        
        rules: {
            numericformat: "number", 
            pattern:"regexp",
            notnull:"required",
            maxnumber:"max", 
            minnumber:"min"
        }
    }
});

})(jQuery);







