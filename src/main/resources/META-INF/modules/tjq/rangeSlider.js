requirejs.config({
	"shim" : {
		"tjq/vendor/ui/jquery-ui.custom": ["jquery"]
	}
});
define(["t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/ui/jquery-ui.custom"], 
function(dom, zone, events) {
	init = function(specs) {

            var el = jQuery("#" + specs.sliderId);

            if(!specs.displayTextField) {
				jQuery("#" + specs.idMinField).css("display", "none");
				jQuery("#" + specs.idMaxField).css("display", "none");
			}
			var options={
				slide:function(e,u){
					jQuery("#" + specs.idMinField).val(u.values[0]);
					jQuery("#" + specs.idMaxField).val(u.values[1]);
				}, 
				change:function(e,u){
                    var zoneElement = specs.zoneId === '^' ? $(el).closest('.t-zone').attr("id") : specs.zoneId
					if(specs.url) {
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						var z = dom.wrap(zoneElement);
						z.trigger(events.zone.refresh, {
							url: specs.url + sep + "min="+u.values[0]+"&max="+u.values[1]
						});
					}
				}
			};
            el.slider(specs.params).slider("option", options);
	  };
  	
  	return exports = init;
});