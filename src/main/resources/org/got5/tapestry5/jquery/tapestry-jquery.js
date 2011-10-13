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
    /** Formats and displays an error message on the console. */
    error : function(message, substitutions) {
        Tapestry.invokeLogger(message, substitutions, Tapestry.Logging.error);
    },

    /** Formats and displays a warning on the console. */
    warn : function(message, substitutions) {
        Tapestry.invokeLogger(message, substitutions, Tapestry.Logging.warn);
    },

    /** Formats and displays an info message on the console. */
    info : function(message, substitutions) {
        Tapestry.invokeLogger(message, substitutions, Tapestry.Logging.info);
    },

    /** Formats and displays a debug message on the console. */
    debug : function(message, substitutions) {
        Tapestry.invokeLogger(message, substitutions, Tapestry.Logging.debug);
    },
    
    //Based on http://javascript.crockford.com/remedial.html
    supplant : function (message,o) {
        return message.replace(/#{([^{}]*)}/g,
            function (a, b) {
                var r = o[b];
                return typeof r === 'string' || typeof r === 'number' ? r : a;
            }
        );
    },
    
    invokeLogger : function(message, substitutions, loggingFunction) {
        if (substitutions != undefined){
            message = Tapestry.supplant(message,substitutions);
        }
		
        loggingFunction.call(this, message);
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
    }
});

/** Compatibility: set Tapestry.Initializer equal to T5.initializers. */

Tapestry.Initializer = T5.initializers;

T5.extendInitializers({
	
	/** Make the given field the active field (focus on the field). */
    activate : function(id) {
        $("#" + id).focus();
    },
    
    /**
     * evalScript is a synonym for the JavaScript eval function. It is
     * used in Ajax requests to handle any setup code that does not fit
     * into a standard Tapestry.Initializer call.
     */
    evalScript : function(spec) {

		eval(spec);
	},
    
    ajaxFormLoop : function(spec) {
    	$.each(spec.addRowTriggers, function(index, triggerId) {
            
            $("#" + triggerId).click(function(event) {
                $("#" + spec.rowInjector).tapestryFormInjector("trigger");
                return false;
            })
        });
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
     * Convert a form or link into a trigger of an Ajax update that
     * updates the indicated Zone.
     *
     * @param spec.linkId
     *            id or instance of &lt;form&gt; or &lt;a&gt; element
     * @param spec.zoneId
     *            id of the element to update when link clicked or form
     *            submitted
     * @param spec.url
     *            absolute component event request URL
     */
    linkZone : function(spec) {
    	Tapestry.Initializer.updateZoneOnEvent("click", spec.linkId,
				spec.zoneId, spec.url);
    },
    
    /**
     * Converts a link into an Ajax update of a Zone. The url includes
     * the information to reconnect with the server-side Form.
     *
     * @param spec.selectId
     *            id or instance of &lt;select&gt;
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
		
		if (spec.cancel) {
			el.attr("name", "cancel");
        }
		
        el.tapestryLinkSubmit({
            form: spec.form,
			validate: spec.validate,
			clientId: spec.clientId
			
        });
    },
    
    /**
     * Used by other initializers to connect an element (either a link
     * or a form) to a zone.
     *
     * @param eventName
     *            the event on the element to observe
     * @param element
     *            the element to observe for events
     * @param zoneId
     *            identified a Zone by its clientId. Alternately, the
     *            special value '^' indicates that the Zone is a
     *            container of the element (the first container with the
     *            't-zone' CSS class).
     * @param url
     *            The request URL to be triggered when the event is
     *            observed. Ultimately, a partial page update JSON
     *            response will be passed to the Zone's ZoneManager.
     */
    updateZoneOnEvent : function(eventName, element, zoneId, url) {
    	var el = $('#' + element);
		
        var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);
        
        if (!zoneElement) {
            Tapestry
                .error(
                "Could not find zone element '#{zoneId}' to update on #{eventName} of element '#{elementId}",
                {
                    zoneId : zoneId,
                    eventName : eventName,
                    elementId : element.id
                });
            return;
        }
        
        if (el.is('form')) {
			
			el.addClass(Tapestry.PREVENT_SUBMISSION);

			el.submit(function() {
				
				el.trigger(Tapestry.FORM_PROCESS_SUBMIT_EVENT);
				
				return false;
			});
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
    
    /**
     * Sets up a Tapestry.FormEventManager for the form, and enables
     * events for validations. This is executed with
     * InitializationPriority.EARLY, to ensure that the FormEventManager
     * exists vefore any validations are added for fields within the
     * Form.
     *
     * @since 5.2.2
     */
    formEventManager : function(spec) {
    	$("#" + spec.formId).formEventManager({
 	       
            'form' : $('#' + spec.formId),
            'validateOnBlur' : spec.validate.blur,
            'validateOnSubmit' : spec.validate.submit

	    });
    },
    
    zone: function(spec) {
        if (!jQuery.isPlainObject(spec)) {
            spec = {
                element: spec
            };
        }
        
        $('#' + spec.element).tapestryZone(spec);
    }, 
    
    formInjector: function(spec) {
        $("#" + spec.element).tapestryFormInjector(spec);
    },
    
    cancelButton : function(clientId) {
    	$("#" + clientId).click(function() {
    		//TODO
    	});
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
        
    }
});

$.widget("ui.formEventManager", {
    options: { },

    _create: function() { 
	
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
		
		var t = element == null ? null : $.toJSON([element,
		                                           $("#"+element).attr("name")]);
		
		this.options.submitHidden.attr("value", t);
	},
	
	handleSubmit : function(element) {
		//TODO ?
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
				url: specs.url,
				type: "POST", 
				success: function(data){
					if (data.content) {

		                that.applyContentUpdate(data.content);

					} else if (data.zones) {

	                    // perform multi zone update
						$.each(data.zones, function(zoneId, content){
							
							if (zoneId === "" || ! $('#' + zoneId).length) {

				                that.applyContentUpdate(content);

							} else {
								
								$('#' + zoneId).tapestryZone("applyContentUpdate", content);
							}
						});
						
					}

	                $.tapestry.utils.loadScriptsInReply(data);
				}
		};
		
		if (specs.params) {
			$.extend(ajaxRequest, {
                data: specs.params
            });
        }
		
        $.tapestry.utils.ajaxRequest(ajaxRequest);
		
		
		 /*var ajaxRequest = {
		        	type:"POST",
		            url: specs.url,
		            error : $.tapestry.utils.ajaxFailureHandler,
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
		            }
		        };
		        
		        if (specs.params) {
		            ajaxRequest = $.extend(ajaxRequest, {
		                data: specs.params
		            });
		        }
		        $.ajax(ajaxRequest);
		*/
		
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

        var el = $(this.element);
		if (!this.options.validate) {
			// skips validation if the SubmitMode is CANCEL
			el.addClass("cancel");
		}
		
        el.click(function() {
        	
        	$("#"+that.options.form).formEventManager("setSubmittingElement", that.options.clientId);
        	
        	$(this).tapestryLinkSubmit("clicked");

            return false;
        });
    },

    destroy: function() {

        $.Widget.prototype.destroy.apply( this, arguments );
    },
    
    clicked: function() {
    	
		var form = $("#" + this.options.form);
		
		if (form.hasClass(Tapestry.PREVENT_SUBMISSION)) {
	   
            $(form).trigger(Tapestry.FORM_PROCESS_SUBMIT_EVENT);         	

		} else {
			
            form.submit();  
		}
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
            	
                var newElement = el.clone(false);
                newElement.attr("id", data.elementId);
                newElement.html(data.content);
                
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
	    }, 
	    
	    /**
	     * Default function for handling a communication error during an Ajax
	     * request.
	     */
	    ajaxExceptionHandler : function(response, exception) {
	    	
	        Tapestry.error(Tapestry.Messages.communicationFailed + exception);

	        Tapestry.debug(Tapestry.Messages.ajaxFailure + exception, response);

	        throw exception;
	    },

	    /**
	     * Default function for handling Ajax-related failures.
	     */
	    ajaxFailureHandler : function(response) {
	        var rawMessage = response.getResponseHeader("X-Tapestry-ErrorMessage");

	        var message = unescape(rawMessage);

	        Tapestry.error(Tapestry.Messages.communicationFailed + message);
	        
	        var JSONresponse = {
	        		status: response.status
	        }
	        Tapestry.debug(Tapestry.Messages.ajaxFailure + message, JSONresponse);

	        var contentType = response.getResponseHeader("content-type")

	        var isHTML = contentType && (contentType.split(';')[0] === "text/html");

	        if (isHTML) {
	            T5.ajax.showExceptionDialog(response.responseText)
	        }
	    }, 
	    
	    /**
	     * Processes a typical Ajax request for a URL. In the simple case, a success
	     * handler is provided (as options). In a more complex case, an options
	     * object is provided, with keys as per Ajax.Request. The onSuccess key will
	     * be overwritten, and defaults for onException and onFailure will be
	     * provided. The handler should take up-to two parameters: the
	     * XMLHttpRequest object itself, and the JSON Response (from the X-JSON
	     * response header, usually null).
	     *
	     * @param url
	     *            of Ajax request
	     * @param options
	     *            either a success handler
	     * @return the Ajax.Request object
	     */
	    ajaxRequest : function(options) {
	    	
	    	if ($.isFunction(options)) {
	            return $.tapestry.utils.ajaxRequest(url, {
	                success : options
	            });
	        }
	        var successHandler = options.success;
	        
	        
	        var finalOptions = {
	        		onException : $.tapestry.utils.ajaxExceptionHandler,	
	        		error : $.tapestry.utils.ajaxFailureHandler
	        };
	        
	        $.extend(finalOptions, options);
	        
	        $.extend(finalOptions, {
	        	success : function(response, jsonResponse,textStatus) {
	        		/*
                     * When the page is unloaded, pending Ajax requests appear to
                     * terminate as successful (but with no reply value). Since
                     * we're trying to navigate to a new page anyway, we just ignore
                     * those false success callbacks. We have a listener for the
                     * window's "beforeunload" event that sets this flag.
                     */
                    if (Tapestry.windowUnloaded)
                        return;

                    /*
                     * Prototype treats status == 0 as success, even though it seems
                     * to mean the server didn't respond.
                     */
                    if (textStatus.status!=200) {
                        finalOptions.error.call(this, response);
                        return;
                    }
                    try {
                        /* Re-invoke the success handler, capturing any exceptions. */
                        if(successHandler != undefined) successHandler.call(this, response, jsonResponse);
                    	
                    	
                    } catch (e) {
                        finalOptions.onException.call(this, ajaxRequest, e);
                    }
	        	}
	        });
	        var ajaxRequest = $.ajax(finalOptions);

	        return ajaxRequest;
	    }


    }
};
    
})(jQuery);
