(function($) {

	// for compatibility with beanvalidation
	$.extend(Tapestry, {
		Validator : {}
	});

	$.extend(Tapestry.Initializer, {
		validate : function(specs) {

			$.each(specs, function(field, ruleSpecs) {
				var field = $('#' + field);
				field.tapestryFieldEventManager();

				$.each(ruleSpecs, function(j, ruleSpec) {
					var name = ruleSpec[0];
					var message = ruleSpec[1];
					var constraint = ruleSpec[2];

					var vfunc = Tapestry.Validator[name];

					if (vfunc == undefined) {
						$().log(
								Tapestry.Messages.missingValidator
										+ "\n--> validator name : " + name
										+ "\n & field name :"
										+ field.attr('id'));
						return;
					}

					/*
					 * Pass the extended field, the provided message, and the
					 * constraint object to the Tapestry.Validator function, so
					 * that it can, typically, invoke field.addValidator().
					 */
					try {
						vfunc.call(this, field, message, constraint);
					} catch (e) {
						$().log(
								Tapestry.Messages.invocationException
										+ "\n--> fname : "
										+ "Tapestry.Validator." + name
										+ "\n & params :" + field.id + ", "
										+ message + ", " + constraint
										+ "\n& exeption :" + e);
					}
				});

			});
		}
	});

	$.widget("ui.tapestryFieldEventManager", {
		options : {
			show : "highlight",
			validationError : false,
			requiredCheck : null,
			translator : function(x) {
				return (x);
			}
		},

		_create : function() {
			var that = this;
			var field = this.element;
			this.options.id = field.attr("id");
			var form = field.closest('form');
			if (form.formEventManager("option", "validateOnBlur")) {
				$(document).bind(Tapestry.FOCUS_CHANGE_EVENT, function(ev) {
					if (Tapestry.currentFocusField == field[0]) {
						that.validateInput();
					}
				});
			}
			if (form.formEventManager("option", "validateOnSubmit")) {
				form.bind(Tapestry.FORM_VALIDATE_FIELDS_EVENT, function(event) {
					that.validateInput();
				});
			}
		},

		getLabel : function() {
			if (!this.options.label) {
				var selector = "label[for='" + this.options.id + "']";
				this.options.label = $(selector);
			}
			return this.options.label;
		},

		getIcon : function() {
			if (!this.options.icon) {
				this.options.icon = $("#" + this.options.id + "_icon");
			}

			return this.options.icon;
		},

		/**
		 * Removes validation decorations if present. Hides the ErrorPopup, if
		 * it exists.
		 */
		removeDecorations : function() {
			var field = this.element;
			field.removeClass("t-error");

			this.getLabel() && this.getLabel().removeClass("t-error");

			this.getIcon() && this.getIcon().hide();

			Tapestry.ErrorPopup
					.hide($("#" + field.attr('id') + "\\:errorpopup"));
		},

		/**
		 * Show a validation error message, which will add decorations to the
		 * field and it label, make the icon visible, and raise the field's
		 * Tapestry.ErrorPopup to show the message.
		 * 
		 * @param message
		 *            validation message to display
		 */
		showValidationMessage : function(message) {
			var field = this.element;
			var form = field.closest('form');

			this.options.validationError = true;
			form.formEventManager("setValidationError", true);

			field.addClass("t-error");

			this.getLabel() && this.getLabel().addClass("t-error");

			var icon = this.getIcon();

			if (icon)
				icon.show();

			var id = field.attr('id') + "\\:errorpopup";
			if ($("#" + id).size() == 0) // if the errorpopup isn't on the
											// page yet, we create it
				field.after("<div id='" + field.attr('id')
						+ ":errorpopup' class='tjq-error-popup'/>");
			Tapestry.ErrorPopup.show($("#" + id), "<span>" + message
					+ "</span>");

		},

		/**
		 * Invoked when a form is submitted, or when leaving a field, to perform
		 * field validations. Field validations are skipped for disabled fields.
		 * If all validations are succesful, any decorations are removed. If any
		 * validation fails, an error popup is raised for the field, to display
		 * the validation error message.
		 * 
		 * @return true if the field has a validation error
		 */
		validateInput : function() {
			var field = this.element;
			if (field.filter(":disabled").size() != 0) // disabled field
				return false;

			if (field.filter(":visible").size() == 0) // invisible field
				return false;

			this.options.validationError = false;

			var value = field.val();

			if (this.options.requiredCheck)
				this.options.requiredCheck.call(this, value);

			/*
			 * Don't try to validate blank values; if the field is required,
			 * that error is already noted and presented to the user.
			 */

			if (!this.options.validationError
					&& !((typeof value == "string") && value == "")) { // value.blank()
																		// =>
																		// value==""
																		// ??
																		// also
																		// null
																		// maybe
																		// ?
				var translated = this.options.translator(value);

				/*
				 * If Format went ok, perhaps do the other validations.
				 */
				if (!this.options.validationError) {
					field.trigger(Tapestry.FIELD_VALIDATE_EVENT, [ value,
							translated ]);
				}
			}

			/*
			 * Lastly, if no validation errors were found, remove the
			 * decorations.
			 */

			if (!this.options.validationError)
				this.removeDecorations();

			return this.options.validationError;
		}

	});

	$
			.extend(
					Tapestry,
					{
						Validator : {

							required : function(field, message) {
								$(field)
										.tapestryFieldEventManager(
												{
													requiredCheck : function(
															value) {
														if ((typeof value == "string" && value == '')
																|| value == null)
															$(field)
																	.tapestryFieldEventManager(
																			"showValidationMessage",
																			message);
													}
												});
							},

							/**
							 * Supplies a client-side numeric translator for the
							 * field.
							 */
							numericformat : function(field, message, isInteger) {
								$(field)
										.tapestryFieldEventManager(
												{
													translator : function(input) {
														
														try {
															return $.tapestry.utils.formatLocalizedNumber(input, isInteger);
														} catch (e) {
															$(field)
															.tapestryFieldEventManager(
																	"showValidationMessage",
																	message);
														}
														
													}
												});
							},
							minlength : function(field, message, length) {
								$.tapestry.utils.addValidator(field, function(
										value) {
									if (value.length < length)
										throw message;
								});
							},

							maxlength : function(field, message, maxlength) {
								$.tapestry.utils.addValidator(field, function(
										value) {
									if (value.length > maxlength)
										throw message;
								});
							},

							min : function(field, message, minValue) {
								$.tapestry.utils.addValidator(field, function(
										value) {
									if (value < minValue)
										throw message;
								});
							},

							max : function(field, message, maxValue) {
								$.tapestry.utils.addValidator(field, function(
										value) {
									if (value > maxValue)
										throw message;
								});
							},

							regexp : function(field, message, pattern) {
								var regexp = new RegExp(pattern);
								$.tapestry.utils.addValidator(field, function(
										value) {
									if (!regexp.test(value))
										throw message;
								});
							}
						}
					});

	$.extend($.tapestry.utils, {
		/**
		 * Adds a standard validator for the element, an observer of
		 * Tapestry.FIELD_VALIDATE_EVENT. The validator function will be passed
		 * the current field value and should throw an error message if the
		 * field's value is not valid.
		 * 
		 * @param element
		 *            field element to validate
		 * @param validator
		 *            function to be passed the field value
		 */
		addValidator : function(element, validator) {
			$(element).bind(
					Tapestry.FIELD_VALIDATE_EVENT,
					function(e, data, translated) {
						try {
							validator.call(this, translated);
						} catch (message) {
							$(element).tapestryFieldEventManager(
									"showValidationMessage", message);
						}
					});

			return element;
		}
	});

	$.extend(Tapestry, {
		ErrorPopup : {
			show : function(el, msg) {
				el.html(msg).show();
			},
			hide : function(el) {
				el.hide();
			}
		}
	});
})(jQuery);