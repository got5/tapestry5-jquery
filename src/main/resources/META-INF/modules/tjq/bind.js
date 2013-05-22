define(["t5/core/dom", "t5/core/zone", "t5/core/events", "t5/core/ajax", "jquery"], function(dom, zone, events, ajax) {
	init = function(specs) {
		var el = jQuery('#'+specs.elementId);
			var url = specs.url;
			var zoneId = specs.zoneId;
			var hide = specs.hide;
			var hideEffect = specs.hideEffect;
			var hideTime = specs.hideTime;
			var options = specs.hideOptions;
			var zoneUpdate = specs.zoneUpdate;
			var title = specs.title;
			var history = eval('[' + specs.history + ']')[0];
			// can't pass function thru JSON
			var callback = eval('[' + specs.callback + ']')[0];
			var contextMarker = specs.contextMarker;
			var preventDefault = specs.preventDefault;
			
			jQuery(el).bind( specs.eventType, function(event,ui) {
				if ( preventDefault ) {
					event.preventDefault();
				}
				
				if ( title ) {
					document.title=title;
				}			

				
				var u = new Object();

				u.url = url;
				
				//If the element using the bind mixin has a value, we automatically added to tue url
				if(jQuery(this).val() !== "") u.url += ("/" + jQuery(this).val())
					
				u.context = contextMarker;		
				u.element = jQuery(this);
				
				if ( history ) {
					history(event,ui,u);
				}
				if ( callback ) {					
					// TODO should work if value is array
					u.addContext = function(value) { u.url = u.url.replace(u.context,value) };
					callback(event,ui,u);
				}
				if ( u.url ) {
					
					if ( zoneId ) {
							
						var z = dom.wrap(zoneId);
						if(z){
							z.trigger(events.zone.refresh, {
								url: url 
							
							});
						}	
					} else {
						ajax(u.url, {});
					}
				}
			});
	  };
  	
  	return exports = init;
});
