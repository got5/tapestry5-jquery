requirejs.config({
	"shim" : {
		"tjq/vendor/ui/jquery-ui": ["jquery"],
		"tjq/vendor/jquery.json-2.4": ["jquery"], 
	}
});
define([  "t5/core/dom", "t5/core/events", "tjq/vendor/ui/jquery-ui", "tjq/vendor/jquery.json-2.4"], function(dom, events) {

	var draggable = function(spec) {
		jQuery( "#" + spec.id ).draggable(spec.params).data("contexte",spec.context);
	};

	var droppable = function(spec) {
		jQuery( "#" + spec.id ).droppable(spec.params);
    		jQuery( "#" + spec.id ).bind( "drop", function(event, ui) {
    			 var contexte=jQuery(ui.draggable).data("contexte");
    			 
				 var parts = spec.BaseURL.split("?");
				 parts[0] += "/" + encodeURIComponent(contexte);
				 var urlWithContexte = parts.join("?");
				
				var z = dom.wrap(spec.id);
						if(z){
							z.trigger(events.zone.refresh, {
								url: urlWithContexte 
							
							});
				}	
    			
    		});
	};

	var resizable = function(spec) {
		// TODO
	};

	var selectable = function(spec) {
		// TODO
	};

	var sortable = function(spec) {
		var sep = (spec.url.indexOf("?") >= 0) ? "&" : "?";

		if(!spec.params.update)
			spec.params.update=function(a,b){
				ajaxRequest = {
                        url: spec.url + sep + "list="+ jQuery("#"+spec.id).sortable("toArray").toString()
                };
				jQuery.ajax(ajaxRequest);
		};
		jQuery("#"+spec.id).sortable(spec.params);	
		jQuery("#"+spec.id).disableSelection();
	};

	var accordion = function(spec) {
		jQuery("#" + spec.id).accordion(spec.params);
	};

	var autocomplete = function(spec) {
		var conf = {
				source: function(request, response){
					
					var params = {};
					
					var extra = jQuery("#" + spec.id).data('extra');
					if(extra) {
						params["extra"] = extra;
					}
					
					params[spec.paramName] = request.term;
					
					var ajaxRequest = {
					 	type:"POST",
                    	url:spec.url,
                    	dataType: "json",
        				success: function(data){
                            response(eval(data));
                        }, 
                        data:{
                        	"data" : jQuery.toJSON( params )
                        } 
                    };
                    this.xhr = jQuery.ajax(ajaxRequest);
                }
        };
        
        if (spec.delay >= 0) 
        	conf.delay = spec.delay;
            
        if (spec.minLength >= 0) 
        	conf.minLength = spec.minLength;

        if (spec.options) {
        	jQuery.extend(conf, spec.options);
        }
        
        jQuery("#" + spec.id).autocomplete(conf);
	};

	var button = function(spec) {
		if (spec.type == "buttonset")
			jQuery("#" + spec.id).buttonset(spec.params);
		else
			jQuery("#" + spec.id).button(spec.params);
	};

	var datepicker = function(spec) {
		jQuery("#" + spec.field).datepicker({
            gotoCurrent: true,
            showOn: "button",
            buttonImageOnly: false,
            disabled: $("#" + spec.field).attr("disabled")
        });
	};

	var dialog = function(spec) {
		 jQuery("#" + spec.id).dialog(spec.params);
	};

	var menu = function(spec) {
		// TODO
	};

	var progressbar = function(spec) {
		// TODO
	};

	var slider = function(spec) {
		// TODO
	};

	var spinner = function(spec) {
		// TODO
	};

	var tabs = function(spec) {
		var p = spec.params;
		if (!p.ajaxOptions)
			p.ajaxOptions = {};
		if (!p.ajaxOptions.beforeSend)
			jQuery.extend(p.ajaxOptions, {
				beforeSend : function() {
					// returning false in beforeSend function cancels the AJAX
					// request, see issue #52
					return false;
				}
			});
		jQuery("#" + spec.id).tabs(p);
	};

	var tooltip = function(spec) {
		jQuery("#" + spec.id).tooltip(spec.options);
	};

	return {
		// Interactions
		draggable : draggable,
		droppable : droppable,
		resizable : resizable,
		selectable : selectable,
		sortable : sortable,

		// Widgets
		accordion : accordion,
		autocomplete : autocomplete,
		button : button,
		datepicker : datepicker,
		dialog : dialog,
		menu : menu,
		progressbar : progressbar,
		slider : slider,
		spinner : spinner,
		tabs : tabs,
		tooltip : tooltip
	};
});