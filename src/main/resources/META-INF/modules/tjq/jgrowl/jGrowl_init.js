(function( $ ) {
	T5.extendInitializers(function() {
	
	        var on = T5.dom.observe;
	        var DISMISS_ALERTS = "tapestry:dismiss-all";
	        var addAlertPublisher = T5.pubsub.createPublisher(T5.events.ADD_ALERT, document);
	
	        function alertManager(spec) {
	        	
	        	$.extend($.jGrowl.defaults, spec.jgrowl);
	        	
	            T5.sub(T5.events.ADD_ALERT, null, function(alertSpec) {
	            	
	            	var sticky = true;
	            	if (alertSpec['transient']) {
	                    sticky = false;
	                }
	            	
	            	var params = {
	            			close : function(){
		            			if (alertSpec.id) {
			                    	$.tapestry.utils.ajaxRequest({url:spec.dismissURL, type: "POST", data: {id: alertSpec.id}});
			                    }
		            		}, 
		            		life : T5.alerts.TRANSIENT_DELAY, 
		            		sticky : sticky, 
		            		theme : "jgrowl-" + alertSpec['class']
	            	};
	            	
	            	$.jGrowl(alertSpec.message, params);
	            	
	            	$("div#jGrowl div.jGrowl-closer").live("click", function(){
	            		var ajaxRequest = {
	                        	type:"POST",
	                            url: spec.dismissURL
	                    };
	                    
	                    $.ajax(ajaxRequest);
	            	});
	            	
	             
	            });
	        }
	
	        return {
	        	jGrowlAlertManager : alertManager,
	        	addjGrowlAlert : addAlertPublisher
	        }
	    }
	
	)
	    ;
	
	T5.define('alerts', {
	    /** Time, in ms, that a transient message is displayed before automatically dismissing. */
	    TRANSIENT_DELAY : 15000
	});
})(jQuery);
