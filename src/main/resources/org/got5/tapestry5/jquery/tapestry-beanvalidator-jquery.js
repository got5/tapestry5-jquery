(function($) {

	$.extend(Tapestry.Validator,{
						
								
								notnull : function(field, message) {
									Tapestry.Validator.required(field, message);
								},
	
								isnull : function(field, message, pattern) {
									$.tapestry.utils.addValidator(field, function(
											value) {
										if (value != null)
											throw message;
									});
								},
	
								maxnumber : function(field, message, spec) {
									Tapestry.Validator.max(field, message,
											spec.value);
								},
	
								minnumber : function(field, message, spec) {
									Tapestry.Validator.min(field, message,
											spec.value);
								},
	
								size : function(field, message, spec) {
									$.tapestry.utils
											.addValidator(
													field,
													function(value) {
														if ($.type(value)=="string") {
															if (value.length < spec.min)
																throw message;
															if (value.length > spec.max)
																throw message;
	
														} else if ($.type(value) == "array") {
															if (this.tagName == "SELECT") {	
																var selectedOptions = this.childElementCount
																
																if (selectedOptions < spec.min)
																	throw message;
																if (selectedOptions > spec.max)
																	throw message;
															}
														}
													});
								},
	
								pattern : function(field, message, pattern) {
									Tapestry.Validator.regexp(field, message,
											pattern);
								}
						
	});


})(jQuery);