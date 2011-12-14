(function( $ ) {
	

$.extend(Tapestry, {
	/** 
     * Key for storing virtualScripts data in the DOM
     */
    VIRTUAL_SCRIPTS: 'tapestry.virtualScripts',
	
    waitForPage: function() {
        return;
    },
    
    onDOMLoaded: function(callback) {
        $(document).ready(callback);
    },
    
    init: function(specs) {
        $.each(specs, function(functionName, params) {
            var initf = Tapestry.Initializer[functionName];
            
            if (!initf) {
                $().log("No Tapestry.Initializer function for : " + functionName);
            }
            
            $.each(params, function(i, paramList) {
            
                if (!jQuery.isArray(paramList)) {
                    paramList = [paramList];
                }
                
                initf.apply(this, paramList);
            });
        });
    },
    
    markScriptLibrariesLoaded: function(scripts) {
        var virtualScripts = $('html').data(Tapestry.VIRTUAL_SCRIPTS);

        if (!virtualScripts) {
            virtualScripts = [];
        }

        $.each(scripts, function(i, script) {
            var complete = $.tapestry.utils.rebuildURL(script);

	        if ($.inArray(complete, virtualScripts) === -1) {

	            virtualScripts.push(complete);
	        }
        });
        
        $('html').data(Tapestry.VIRTUAL_SCRIPTS, virtualScripts);
    }, 
    onDomLoadedCallback: function(){
    	
    	/*Tapestry.pageLoaded = true;

        Tapestry.ScriptManager.initialize();*/

        $(".t-invisible").each(function() {
            $(this).hide().removeClass("t-invisible");
        });

        /*
         * Adds a focus observer that fades all error popups except for the
         * field in question.
         */
        $(":input").each(function() {
            /*
             * Due to Ajax, we may execute the callback multiple times, and we
             * don't want to add multiple listeners to the same element.
             */
        	var element = $(this);
        	
            if (!element.data("observingFocusChange")) {
                element.focus(function() {
                    if (element != Tapestry.currentFocusField) {
                        $(document).trigger(Tapestry.FOCUS_CHANGE_EVENT);
                        Tapestry.currentFocusField = element[0];
                    }
                });

                element.data("observingFocusChange",true);
            }
        });

        /*
         * When a submit element is clicked, record the name of the element into
         * the associated form. This is necessary for some Ajax processing, see
         * TAPESTRY-2324.
         *
         * TAP5-1418: Added "type=image" so that they set the submitting element
         * correctly.
         */
        /*$$("INPUT[type=submit]", "INPUT[type=image]").each(function(element) {
            var t = $T(element);

            if (!t.trackingClicks) {
                element.observe("click", function() {
                    $(element.form).setSubmittingElement(element);
                });

                t.trackingClicks = true;
            }
        });
        */
        
    }
});

/** Container of functions that may be invoked by the Tapestry.init() function. */
$.extend(Tapestry.Initializer, {
	
	/** Make the given field the active field (focus on the field). */
	activate : function(id) {
		$("#" + id).focus();
	},

    ajaxFormLoop: function(spec) {
    
        $.each(spec.addRowTriggers, function(index, triggerId) {
        
            $("#" + triggerId).click(function(event) {
                $("#" + spec.rowInjector).tapestryFormInjector("trigger");
                return false;
            })
        });
    },

	/**
	 * evalScript is a synonym for the JavaScript eval function. It is used in
	 * Ajax requests to handle any setup code that does not fit into a standard
	 * Tapestry.Initializer call.
	 */
	evalScript : function(spec) {

		eval(spec);
	},
    
	formEventManager : function(spec) {
		$("#" + spec.formId).formEventManager({
	       
            'form' : $('#' + spec.formId),
            'validateOnBlur' : spec.validate.blur,
            'validateOnSubmit' : spec.validate.submit

	    });
		
	},
    
    formInjector: function(spec) {
        $("#" + spec.element).tapestryFormInjector(spec);
    },
    
    formLoopRemoveLink: function(spec) {
        defaults = {
            effect: "blind"
        };
        
        spec = $.extend(defaults, spec);
        
        var link = $("#" + spec.link),
			fragmentId = spec.fragment;
        
        link.click(function(e) {
        
            var successHandler = function(data) {
           
                var container = $("#" + fragmentId),
					fragment = container.data("tapestry.formFragment");
                
                if (fragment) {
                    // TODO : reimplement fragment.hideAndRemove();
                }
                else {
                    var options = {};
                   
                    container.fadeOut('slow', function() {
                    	$(this).remove();
                    });
                }
            }
            
            $.ajax({
                url: spec.url,
                success: successHandler
            });
            
            return false;
        });
    },
    
    /**
     * Rewrite the linkSubmit component. Because Tapestry will use a span tag.
     * We remove this span, and use an "a" tag and copy/paste all the parameters.
     */
    linkSubmit: function (spec) {

        var el = $("#" + spec.clientId), 
        	attrs = el.get(0).attributes;
        
        
       
		el.replaceWith("<a id='" + spec.clientId + "'>" + el.html() + "</a>");
		
		// reload element
		el = $("#" + spec.clientId);
		
		$.each(attrs , function(i, attrib){
			if(attrib.value) el.attr(attrib.name,attrib.value);
		});
		
		el.attr('href', '#');
		
        el.tapestryLinkSubmit({
            form: spec.form,
			validate: spec.validate,
			clientId: spec.clientId
			
        });
    },

    /**
     * Convert a form or link into a trigger of an Ajax update that
     * updates the indicated Zone.
     * @param element id or instance of <form> or <a> element
     * @param zoneId id of the element to update when link clicked or form submitted
     * @param url absolute component event request URL
     */
    linkZone: function(spec) {
    	Tapestry.Initializer.updateZoneOnEvent("click", spec.linkId,
				spec.zoneId, spec.url);
    },
	
   /**
     * Converts a link into an Ajax update of a Zone. The url includes the
     * information to reconnect with the server-side Form.
     * 
     * @param spec.selectId
     *            id or instance of <select>
     * @param spec.zoneId
     *            id of element to update when select is changed
     * @param spec.url
     *            component event request URL
     */
    linkSelectToZone : function(spec) {
        Tapestry.Initializer.updateZoneOnEvent("change", spec.selectId,
				spec.zoneId, spec.url);
    },
    
    /**
	 * Used by other initializers to connect an element (either a link or a
	 * form) to a zone.
	 * 
	 * @param eventName
	 *            the event on the element to observe
	 * @param element
	 *            the element to observe for events
	 * @param zoneId
	 *            identified a Zone by its clientId. Alternately, the special
	 *            value '^' indicates that the Zone is a container of the
	 *            element (the first container with the 't-zone' CSS class).
	 * @param url
	 *            The request URL to be triggered when the event is observed.
	 *            Ultimately, a partial page update JSON response will be passed
	 *            to the Zone's ZoneManager.
	 */
    updateZoneOnEvent: function(eventName, element, zoneId, url) {

        var el = $('#' + element);
		
        var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);

        if (el.is('form')) {
			
			el.addClass(Tapestry.PREVENT_SUBMISSION);

            el.bind(Tapestry.FORM_PROCESS_SUBMIT_EVENT, function() {
				var specs = {
					url : url,
					params: el.serialize()
				};
				zoneElement.tapestryZone("update", specs);

                return false;
            });
			
        } else if (el.is('select')) {
			
            el.change(function() {
                var parameters = {};
                var selectValue = $('#' + element).val();

                if (selectValue) {
                    parameters["t:selectvalue"] = selectValue;
                }

                zoneElement.tapestryZone("update" , {url : url, params : parameters});
                return false;
            });

		} else {
            el.bind(eventName, function() {
            	el.trigger(Tapestry.TRIGGER_ZONE_UPDATE_EVENT); 
                return false;
            });
            
            el.bind(Tapestry.TRIGGER_ZONE_UPDATE_EVENT, function() {
            	var parameters = {};
            	if (el.is('select') && element.value) {
    				parameters["t:selectvalue"] = element.value;
    			}
            	zoneElement.tapestryZone("update" , {url : url, parameters:parameters});

    		});
        }
    },
    
    zone: function(spec) {
        if (!jQuery.isPlainObject(spec)) {
            spec = {
                element: spec
            };
        }
        
        $('#' + spec.element).tapestryZone(spec);
    }, 
	
	/**
	 * 
	 * @param spec.action 
	 *             the event-link-url
	 * @param spec.params
	 *             
	 * @param spec.messages
     *             localized messages
	 * 
	 * @param spec.multiple 
	 *             enables multiple file upload if possible. (default: true)            
	 * 
	 * @param spec.maxConnections
	 *             maximum number of parallel connections. (default: 3)
	 * 
	 * @param spec.allowedExtensions
	 *             Array of file allowed extensions. Accept all files if emtpy (default)
     * 
     * @param spec.sizeLimit
     *              
     * @param spec.minSizeLimit
     * 
     * @param spec.showMessagesDialog
     *              The id of the error message dialog.
	 */
	uploadable: function(spec) {
        
		var el = $('#' + spec.elementId);
		
		$.extend(spec, {
			showMessage: function(message) {

				$('#' + spec.showMessagesDialog).text(message).dialog('open');

			},

			onComplete: function(id, fileName, responseJSON){
				
				if (responseJSON.zones) {

                    // perform multi zone update
                    $.each(responseJSON.zones, function(zoneId){

                        $('#' + zoneId).tapestryZone("applyContentUpdate", responseJSON.zones[zoneId]);
                    });

                    $.tapestry.utils.loadScriptsInReply(responseJSON);
                }
				
				if (responseJSON.updateZone) {

					var spec = { 
							url : responseJSON.updateZone.url, 
							params : responseJSON.updateZone.params
					};

					$('#' + responseJSON.updateZone.zoneId).tapestryZone("update", spec);
				}

			}, 
			
            template: '<div class="qq-uploader">' +
                '<div class="qq-upload-drop-area"><span>' + spec.messages.dropAreaLabel + ' </span></div>' +
                '<a class="qq-upload-button btn">' + spec.messages.uploadLabel + '</a>' +
                '<ul class="qq-upload-list"></ul>' +
                '</div>',

            // template for one item in file list
            fileTemplate: '<li>' +
                    '<span class="qq-upload-file"></span>' +
                    '<span class="qq-upload-spinner"></span>' +
                    '<span class="qq-upload-size"></span>' +
                    '<a class="qq-upload-cancel" href="#">' + spec.messages.cancelLabel + ' </a>' +
                    '<span class="qq-upload-failed-text">' + spec.messages.failedLabel + '</span>' +
                    '</li>'
			
		});
		
        el.fileuploader(spec);
        
    }, 
    cancelButton : function(clientId) {
		/*
		 * Set the form's skipValidation property and allow the event to
		 * continue, which will ultimately submit the form.
		 */
		$("#"+clientId).click(function(event) {
			var form = $(this).closest('form');
			form.formEventManager("skipValidation");
			form.formEventManager("setSubmittingElement", clientId);
			form.submit();
		});
	}
    
});






/**
 * Zone plugin
 * @param {Object} options
 */
$.widget( "ui.tapestryZone", {
	options: {
    	show: "highlight",
    	update: "highlight", 
    	opt: ""
	},

	_create: function() {
		this.element
			.addClass( "tapestry-zone" );
	},

	destroy: function() {
		this.element
			.removeClass( "tapestry-zone");
		
		$.Widget.prototype.destroy.apply( this, arguments );
	},

    /**
     * Refresh a zone
     * @zoneId
     * @url to request
     * @params optional, can contain data. Request will switch from GET to POST
     */
    update: function(specs) {
        
		var that = this;
		
        var ajaxRequest = {
        	type:"POST",
            url: specs.url,
            success: function(data) {
                
				if (data.content) {

	                that.applyContentUpdate(data.content);

				} else if (data.zones) {
                    // perform multi zone update
					$.each(data.zones, function(zoneId){

						$('#' + zoneId).tapestryZone("applyContentUpdate", data.zones[zoneId]);
					});
					
				}

                $.tapestry.utils.loadScriptsInReply(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(textStatus);
            }
        };
        
        if (specs.params) {
            ajaxRequest = $.extend(ajaxRequest, {
                data: specs.params
            });
        }
        $.ajax(ajaxRequest);
    }, 
	
	/**
	 * Updates the element's content and triggers the appropriate effect on the
	 * zone.
	 * 
	 * @param {Object} content the new content for this zone's body
	 */
	applyContentUpdate: function(content) {

		if (!content) {
			$().log("WARN: content is undefined. Aborting update for zone: " + this.element.attr("id"));
			return;
		}

		var el = this.element;
		var effect = el.is(":visible") ? this.options.update : this.options.show;

		el.html(content).effect(effect, this.options.opt.options,this.options.opt.speed,this.options.opt.callback);
        el.trigger(Tapestry.ZONE_UPDATED_EVENT); 
	}
	
});

$.widget( "ui.tapestryLinkSubmit", {
    options: {
		validate : true
    },

    _create: function() {
    	var that=this;
        var form = $("#" + this.options.form);
        var el = this.element;
		if (!this.options.validate) {
			// skips validation if the SubmitMode is CANCEL
			el.addClass("cancel");
			form.formEventManager("skipValidation");
		}
		
        el.click(function(e) {
        	form.formEventManager("setSubmittingElement", that.options.clientId);
        	that.clicked(e);
            return false;
        });
    },

    _destroy: function() {

        $.Widget.prototype.destroy.apply( this );
    },
    

    clicked: function(e) {
    	//we don't need to validate anything here, since everything is done w/ the formEventManager
    	$("#" + this.options.form).submit();
    }
	
});

$.widget("ui.formEventManager", {
    options: {validationError:false, skipValidation:false },

    _create: function() { 
    	//this.element is the jQuery object of the form, confusing isn't it?
    	this.element.submit({that:this}, this.handleSubmit);
	},

    /**
     * Identifies in the form what is the cause of the submission. The element's
     * id is stored into the t:submit hidden field (created as needed).
     * 
     * @param element
     *            id or element that is the cause of the submit (a Submit or
     *            LinkSubmit)
     */
    setSubmittingElement : function(element) {
    	
    	/**
    	 * Get The Form
    	 */
        var form = this.options.form;

		if (!this.options.submitHidden) {

            /**
             * Check if it is a form controlled by Tapestry
             */
			var hasNoFormData = true;
			$(form).find('input[type="hidden"][name="t:formdata"]').each(function(){
				hasNoFormData = ( $(this).attr('value') == '' );
			});
			
			/**
			 * If it is not, we stop.
			 */
            if (hasNoFormData) {
				return;
			}

            /**
             * Look for hidden input, called t:submit
             */
            var hiddens = $(form).find('input[type="hidden"][name="t:submit"]');

            if (hiddens.size() === 0) {

                /**
                 * Create a new hidden field directly after the first hidden
                 * field in the form.
                 */
            	this.options.submitHidden = $('<input type="hidden" name="t:submit"/>')
            		
            	$(form).append(this.options.submitHidden);
                

            } else
            	this.options.submitHidden = hiddens.first();
        }
		
		var t = element == null ? null : $("#"+element).attr("id");
		
		this.options.submitHidden.attr("value",t);
    }, 
    setValidationError : function(isInError){
    	this.options.validationError = isInError;
    },
    skipValidation : function(){
    	this.options.skipValidation = true;
    },
    handleSubmit : function(e) {
    	/*
		 * Necessary because we set the onsubmit property of the form, rather
		 * than observing the event. But that's because we want to specfically
		 * overwrite any other handlers.
		 */
		//Event.extend(domevent);

    	var thatOpt = e.data.that.options;
    	var $that = e.data.that.element;
    	thatOpt.validationError = false;
    	
		if (!thatOpt.skipValidation) {

			/* Let all the fields do their validations first. */
			$that.trigger(Tapestry.FORM_VALIDATE_FIELDS_EVENT);
			
			//this.form.fire(Tapestry.FORM_VALIDATE_FIELDS_EVENT, this.form);

			/*
			 * Allow observers to validate the form as a whole. The FormEvent
			 * will be visible as event.memo. The Form will not be submitted if
			 * event.result is set to false (it defaults to true). Still trying
			 * to figure out what should get focus from this kind of event.
			 */
			if (!thatOpt.validationError)
				$that.trigger(Tapestry.FORM_VALIDATE_EVENT);

			if (thatOpt.validationError) {
				//domevent.stop();
				e.preventDefault();
				/*
				 * Because the submission failed, the last submit element is
				 * cleared, since the form may be submitted for some other
				 * reason later.
				 */
				e.data.that.setSubmittingElement(null);
				
				//e.preventDefault();
				return false;
			}
		}

		$that.trigger(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT);

		/*
		 * This flag can be set to prevent the form from submitting normally.
		 * This is used for some Ajax cases where the form submission must run
		 * via Ajax.Request.
		 */

		if ($that.hasClass(Tapestry.PREVENT_SUBMISSION)) {
			//domevent.stop();
			e.preventDefault();
			/*
			 * Instead fire the event (a listener will then trigger the Ajax
			 * submission). This is really a hook for the ZoneManager.
			 */
			$that.trigger(Tapestry.FORM_PROCESS_SUBMIT_EVENT);
			//e.preventDefault();
			return false;
		}

		/* Validation is OK, not doing Ajax, continue as planned. */

		// form.submit() or just returning true is enough ??
		return true;
		
	}
	
});

/**
 * Form Injector
 */
$.widget( "ui.tapestryFormInjector", {
	options: {
    	show: "highlight"
	},

	_create: function() {
		this.element
			.addClass( "tapestry-forminjector" )
	},

	destroy: function() {
		this.element
			.removeClass( "tapestry-forminjector");
		
		$.Widget.prototype.destroy.apply( this, arguments );
	},

    trigger: function() {
		var that = this,
			el = $("#" + this.options.element);
		
        var successHandler = function(data) {
        	$(data).log("data");	
            $.tapestry.utils.loadScriptsInReply(data, function() {
                // Clone the FormInjector element (usually a div)
                // to create the new element, that gets inserted
                // before or after the FormInjector's element.			
            	
                var newElement = el.clone(false).attr("id", data.elementId).html(data.content);
                
                newElement = that.options.below ? el.after(newElement) : el.before(newElement);
                
                newElement.effect(that.options.show);
            });
            
        };

        $(this.options).log("this.options.url" + this.options.url)
        $.ajax({
        	type:"POST",
            url: this.options.url,
            success: successHandler
        });
    }
});


/**
 * Logger based on console
 */
jQuery.fn.log = function(msg) { 
    if (Tapestry.DEBUG_ENABLED && typeof console != "undefined" && typeof console.log != "undefined") 
        console.log("%s: %o", msg, this);
    return this;
};

/**
 * Tapestry static functions
 */
$.tapestry = {
    utils: {
        /**
         * Used to reconstruct a complete URL from a path that is (or may be) relative to window.location.
         * This is used when determining if a JavaScript library or CSS stylesheet has already been loaded.
         * Recognizes complete URLs (which are returned unchanged), otherwise the URLs are expected to be
         * absolute paths.
         *
         * @param path
         * @return complete URL as string
         */
        rebuildURL: function(path) {
            if (path.match(/^https?:/)) {
                return path;
            }
            
            if (path.indexOf("/") !== 0) {
                $(this).log(Tapestry.Messages.pathDoesNotStartWithSlash + " " + path);
                
                return path;
            }
            
            var l = window.location;
            return l.protocol + "//" + l.host + path;
        },
        
        /**
         * Add scripts, as needed, to the document, then waits for them all to load, and finally, calls
         * the callback function.
         * @param scripts Array of scripts to load
         * @param callback invoked after scripts are loaded
         */
        addScripts: function(scripts, callback) {
            if (scripts) {
            
                $.each(scripts, function(i, s) {
                    var assetURL = $.tapestry.utils.rebuildURL(s);
                    var virtualScripts = $('html').data(Tapestry.VIRTUAL_SCRIPTS);
                    
                    if (!virtualScripts) {
                        virtualScripts = [];
                        
                        $('script[src]').each(function(i, script) {
                            path = $(script).attr('src');
                            virtualScripts.push($.tapestry.utils.rebuildURL(path));
                        });
                    }
                    
                    if ($.inArray(assetURL, virtualScripts) === -1) {
                        $('head').append('<script src="' + assetURL + '" type="text/javascript" />');
                        virtualScripts.push(assetURL);
                    }
                    
                    $('html').data(Tapestry.VIRTUAL_SCRIPTS, virtualScripts);
                });
                
            }
            
            // TODO : re-implement scriptLoadMonitor
            callback.call(this);
        },
        
        addStylesheets: function(stylesheets) {
            if (stylesheets) {
                $.each(stylesheets, function(i, s) {
                    var assetURL = $.tapestry.utils.rebuildURL(s.href);
                    
                    if ($('head link[href="' + assetURL + '"]').size() === 0) {
                    
                        stylesheet = '<link href="' + assetURL + '" type="text/css" rel="stylesheet"';
                        
                        if (s.media) 
                            stylesheet += ' media="' + s.media + '" ';
                        
                        stylesheet += '/>';
                        
                        $('head').append(stylesheet);
                    }
                });
            }
        },
        
        /**
         * Passed the JSON content of a Tapestry partial markup response, extracts
         * the script and stylesheet information.  JavaScript libraries and stylesheets are loaded,
         * then the callback is invoked.  All three keys are optional:
         * <dl>
         * <dt>redirectURL</dt> <dd>URL to redirect to (in which case, the callback is not invoked)</dd>
         * <dt>scripts</dt><dd>Array of strings (URIs of scripts)</dd>
         * <dt>stylesheets</dt><dd>Array of hashes, each hash has key href and optional key media</dd>
         *
         * @param reply JSON response object from the server
         * @param callback function invoked after the scripts have all loaded (presumably, to update the DOM)
         */
        loadScriptsInReply: function(reply, callback) {
            var redirectURL = reply.redirectURL;
            
            if (redirectURL) {
                // Check for complete URL.
                if (/^https?:/.test(redirectURL)) {
                    window.location = redirectURL;
                    return;
                }
                
                window.location.pathname = redirectURL;
                
                // Don't bother loading scripts or invoking the callback.
                return;
            }
            
            $.tapestry.utils.addStylesheets(reply.stylesheets);
            
            $.tapestry.utils.addScripts(reply.scripts, function() {
                
				/* Let the caller do its thing first (i.e., modify the DOM). */
				if (callback) {
					callback.call(this);
				}

	            /* And handle the scripts after the DOM is updated. */
                if (reply.inits) {
	               $.tapestry.utils.executeInits(reply.inits);
				}					
                
            });
        },
		
	    /**
	     * Called from Tapestry.loadScriptsInReply to load any initializations from
	     * the Ajax partial page render response. Calls
	     * Tapestry.onDomLoadedCallback() last. This logic must be deferred until
	     * after the DOM is fully updated, as initialization often refer to DOM
	     * elements.
	     * 
	     * @param initializations
	     *            array of parameters to pass to Tapestry.init(), one invocation
	     *            per element (may be null)
	     */
	    executeInits : function(initializations) {

	        var initArray = $(initializations).toArray();

	        $(initArray).each(function(index) {

	            Tapestry.init(initArray[index]);
	        });
	        
	        Tapestry.onDomLoadedCallback();
	    }
    }
};
Tapestry.onDOMLoaded(Tapestry.onDomLoadedCallback);  
})(jQuery);
