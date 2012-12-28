(function ($) {

    $.extend(Tapestry.Initializer, {
        PageScroll:function (specs) {
            var scroller = jQuery("#" + specs.scroller);

            scroller.onScrollBeyond(
                function () {

                    if (typeof(this.pageIndex) == "undefined") {
                        this.pageIndex = 0;
                    }

                    if (this.pageIndex == -1 || this.disable) {
                        return;
                    }

                    var activeZone = $("#" + specs.zoneId);

                    var self = this;
                    this.disable = true;
                    scroller.addClass("scrollExtend-loading");
                    if (activeZone.length != 0) {
                        this.disable = true;
                        activeZone.tapestryZone('update', {
                            url:specs.scrollURI,
                            params:{"pageNumber": this.pageIndex + 1},
                            callback:function () {
                                if (activeZone.is(":empty")) {
                                    self.pageIndex = -1;
                                }

                                var html = activeZone.html();
                                activeZone.empty();
                                activeZone.before(html);
                                self.disable = false;
                                scroller.removeClass("scrollExtend-loading");
                            }
                        });

                        this.pageIndex++;
                    }

                },
                specs.params
            )
        }
    });

})(jQuery);