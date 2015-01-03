requirejs.config({
    "shim" : {
        "tjq/vendor/components/jcrop/jquery.Jcrop" : [ "jquery" ]
    }
});
define([ "t5/core/zone", "t5/core/dom", "t5/core/events", "tjq/vendor/components/jcrop/jquery.Jcrop" ], function(z, dom, events) {
    return function(spec) {

        var img = jQuery("#" + spec.id);
        img.Jcrop({
            onSelect : function(c) {

                if (spec.url) {
                    var zone = z.findZone(dom(spec.id));

                    zone.trigger(events.zone.refresh, {
                        url : spec.url + "?x=" + c.x + "&y=" + c.y + "&x2=" + c.x2 + "&y2=" + c.y2 + "&w=" + c.w + "&h=" + c.h
                    });

                }
            },
            aspectRatio : spec.aspectRatio,
            setSelect : spec.setSelect

        });
    };
});