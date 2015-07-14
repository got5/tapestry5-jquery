requirejs.config({
    "shim" : {
        "tjq/vendor/ui/custom" : [ "jquery" ]
    }
});
define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/ui/custom" ], function(dom, zone, events) {
    return function(specs) {
        var el = jQuery("#" + specs.sliderId);

        if (!specs.displayTextField) {

            jQuery("#" + specs.textFieldId).css("display", "none");
        }
        
        var options = {
            value : jQuery("#" + specs.textFieldId).val(),
            slide : function(e, u) {
                jQuery("#" + specs.textFieldId).val(u.value);
            },
            change : function(e, u) {
                if (specs.url) {

                    var zoneElement = specs.zoneId === '^' ? $(el).closest('.t-zone').attr("id") : specs.zoneId;

                    var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
                    var z = dom.wrap(zoneElement);
                    z.trigger(events.zone.refresh, {
                        url : specs.url + sep + "slider=" + u.value
                    });
                }
            }
        };
        el.slider(specs.params).slider("option", options);
    };
});