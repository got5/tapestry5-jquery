requirejs.config({
    "shim" : {
        "tjq/vendor/components/pagescroll/jquery.scrollExtend.min" : [ "jquery" ]
    }
});
define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/components/pagescroll/jquery.scrollExtend.min" ], function(dom, zone, events) {
    return function(specs) {
        var scroller = jQuery("#" + specs.scroller);

        scroller.onScrollBeyond(function() {
            
            var self = this,
                element,
                activeZone;

            if (typeof (this.pageIndex) == "undefined") {
                this.pageIndex = 0;
            }

            if (this.pageIndex === -1 || this.disable) {
                return;
            }

            element = dom.wrap(specs.scroller);
            element.attr("data-update-zone", specs.zoneId);
            activeZone = zone.findZone(element);

            this.disable = true;
            scroller.addClass("scrollExtend-loading");
            if (activeZone.length !== 0) {
                this.disable = true;

                activeZone.trigger(events.zone.refresh, {
                    url : specs.scrollURI,
                    parameters : {
                        "pageNumber" : this.pageIndex + 1
                    }
                });

                activeZone.on(events.zone.didUpdate, function() {
                    if (activeZone.$.is(":empty")) {
                        self.pageIndex = -1;
                    }

                    self.disable = false;
                    scroller.removeClass("scrollExtend-loading");

                });

                this.pageIndex++;
            }

        }, specs.params)
    };
});
