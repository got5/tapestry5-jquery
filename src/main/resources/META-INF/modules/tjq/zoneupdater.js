define(["t5/core/dom", "t5/core/zone", "t5/core/events", "jquery"], 
function(dom, zone, events) {
	init = function(spec) {
	   var element = dom.wrap(spec.elementId);
	   
	   element.attribute("data-update-zone", spec.zone); 
	   
	   var z = zone.findZone(element);

		var updateZone = function() {
			var params = {};  
			if (element.value()) {
				params.param = element.value();
			}
			
			z.trigger(events.zone.refresh, {
				url: spec.url,
				parameters: params
			});
		}

		if (spec.event) {
			var event = spec.event;
			element.on(event, updateZone);
		}
	   
	};
  	
  	return exports = init;
});