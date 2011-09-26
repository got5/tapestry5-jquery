(function( $ ) {
	T5.extendInitializers(function() {
	
	        var on = T5.dom.observe;
	        var DISMISS_ALERTS = "tapestry:dismiss-all";
	        var addAlertPublisher = T5.pubsub.createPublisher(T5.events.ADD_ALERT, document);
	
	        function construct(clientId, dismissText) {
	            $("#"+clientId).html("<div class='t-alert-container'></div>" +
	                "<div class='t-alert-controls'><a href='#'>"+dismissText+"</a></div>");
	
	            var list = $("#"+clientId).find(".t-alert-container");
	            var link = $("#"+clientId).find("a");
	
	            T5.dom.publishEvent(link, "click", DISMISS_ALERTS);
	
	            return list;
	        }
	
	        /**
	         * Specification with keys:
	         * <dl>
	         *   <dt>id</dt> <dd>id of empty client element</dd>
	         *   <dt>dismissURL</dt> <dd>URL used to dismiss an alert</dd>
	         * </dl>
	         */
	        function alertManager(spec) {
	
	            var visible = true;
	            var constructed = false;
	            var list = null;
	
	            T5.sub(DISMISS_ALERTS, null, function() {
	                if (constructed) {
	                    visible = false;
	                    $("#"+spec.id).hide();
	                    visible = false;
	
	                    T5.dom.removeChildren(list);
	
	                    // Don't care about the response.
	                    var ajaxRequest = {
	                        	type:"POST",
	                            url: spec.dismissURL
	                    };
	                    
	                    $.ajax(ajaxRequest);
	                    
	                }
	            });
	
	            // For the moment, there's a bit of prototype linkage here.
	
	            T5.sub(T5.events.ADD_ALERT, null, function(alertSpec) {
	                if (!constructed) {
	                    list = construct(spec.id, spec.dismissText);
	                    constructed = true;
	                }
	
	                if (!visible) {
	                    $("#"+spec.id).show();
	                    visible = true;
	                }
	
	                // This part is Prototype specific, alas.
	
	                var alertDiv = $("<div></div>").addClass(alertSpec['class']).html("<div class='t-dismiss' title='Dismiss'></div>" +
	                    "<div class='t-message-container'>" + alertSpec.message + "</div>");
	
	                list.append(alertDiv);
	
	                var dismiss = alertDiv.find(".t-dismiss");
	
	
	                function removeAlert() {
	                    T5.dom.remove(alertDiv);
	                    if (list.html() == '') {
	                    	$("#"+spec.id).hide();
	                        visible = false;
	                    }
	                }
	
	                // transient is a reserved word in JavaScript, which cause YUICompressor
	                // to fail.
	                if (alertSpec['transient']) {
	                    setTimeout(removeAlert, T5.alerts.TRANSIENT_DELAY);
	                }
	
	                on(dismiss, "click", function(event) {
	                    $(dismiss).stop();
	
	                    removeAlert();
	
	                    // TODO: Switch this to T5.ajax.sendRequest when implemented/available
	
	                    // Send a request, we don't care about the response.
	                   
	                    if (alertSpec.id) {
	                    	$.tapestry.utils.ajaxRequest({url:spec.dismissURL, type: "POST", data: {id: alertSpec.id}});
	                    }
	                });
	
	            });
	        }
	
	        return {
	            alertManager : alertManager,
	            addAlert : addAlertPublisher
	        }
	    }
	
	)
	    ;
	
	T5.define('alerts', {
	    /** Time, in ms, that a transient message is displayed before automatically dismissing. */
	    TRANSIENT_DELAY : 15000
	});
})(jQuery);
