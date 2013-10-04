define(["t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/ui/jquery-ui.custom"],
    function(dom,zone,events) {
    init = function(specs) {

        //JQuery dialog box configuration, if used.
        if (!specs.useDefaultConfirm) {
            var dialogBox = jQuery('<div id=\'dialogConfirmationJQuery\' />').html(specs.message).dialog({
                autoOpen : false,
                resizable : specs.isResizable,
                height : specs.height,
                resize : 'auto',
                title : specs.title,
                modal : specs.isModal,
                draggable : specs.isDraggable,
                buttons : [ {
                    text : specs.validationMsg,
                    click : function() {
                        jQuery(this).dialog("close");


                        //trigger(jQuery("#" + specs.id));
                        trigger(dom(specs.id));
                    }
                }, {
                    text : specs.cancelMsg,
                    click : function() {
                        jQuery(this).dialog("close");
                    }
                } ]
            });
        }

        jQuery("#" + specs.id).click(function(event) {
            if (specs.useDefaultConfir) {
                //Default javascript confirmation box.
                return confirm(specs.message);
            } else {
                //JQuery confirmation box : we stop the event propagation before displaying the box.
                //Otherwise the redirection will occur while confirmation box is displayed.

                event.preventDefault();
                event.stopImmediatePropagation();

                dialogBox.dialog("open");
            }
        });

    };

    function trigger(element) {
        var tagName = element.$.prop("tagName");
        switch (tagName) {
            //Simple link (pagelink, actionlink, etc...)
            case "A":
                var href = element.$.prop("href");
                if (href != undefined) {
                    var urlSuffix = href.substring(href.lastIndexOf('.') + 1);
                    if (element.attribute("data-update-zone")) {
                        //ActionLink
                        var z = dom.wrap(element.attribute("data-update-zone"));
                        if(z){
                            z.trigger(events.zone.refresh, {
                                url: element.$.prop("href")

                            });
                        }

                    } else {
                        window.location.href = href;
                    }
                }
                break;
            //submit button.
            case "INPUT":
                element.$.parents("form").submit();
                break;
            default:
                break;
        }
    }

    return exports = init;
});