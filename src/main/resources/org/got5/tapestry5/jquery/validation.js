(function($) {
/** 
 * Container of functions that may be invoked by the Tapestry.init() function.
 */
$.extend(Tapestry.Initializer, {
    validate: function(/*field, */specs) {    	
    	var b = true;
        $.each(specs, function(i, ruleSpecs) {
            var field = i;
            var $form = $('#' + field).closest('form');
            if (b) {
            	b = false;
            	var settings = {errorClass: "t-error"};
            	if (typeof(validationErrorPlacementCallback) !== 'undefined' && $.isFunction(validationErrorPlacementCallback)) {
            		settings.errorPlacement = validationErrorPlacementCallback; 
            	}
            	if (typeof(validationShowErrorsCallback) !== 'undefined' && $.isFunction(validationShowErrorsCallback)) {
            		settings.showErrors = validationShowErrorsCallback; 
            	}
	            $form.validate(settings);
            }
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

//for compatibility with beanvalidation
$.extend(Tapestry, {Validator:{}});

$.validator.addMethod("regexp",function( value, element, param ) {
	var r = new RegExp(param);
    return this.optional(element) || r.test(value);
},"The text you entered doesn't match the regular expression");

$.validator.addMethod("isnull",function( value, element ) {
    return this.optional(element) || value == null;
},"The value is not null");

//naming the method "size" leads to unexpected behaviour...
//thus a manual mapping between sizeValidation and size is done in $.tapestry.rules
//-> need investigation !
$.validator.addMethod("sizeValidation",function( value, element, param ) {
	if($.type(value) === "string")
		return this.optional(element) || value.length > param.min || value.length < param.max;
	else if ($.isArray(value))
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
            minnumber:"min",
            size:"sizeValidation"
        }
    }
});

})(jQuery);