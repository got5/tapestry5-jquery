define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/ui/custom" ], function(dom, zone, events) {
    var init = function(specs) {

        //JQuery dialog box configuration, if used.
        if (!specs.useDefaultConfirm) {
            var dialogBox = jQuery('<div id=\'dialogConfirmationJQuery\' />').html(specs.message).dialog({
                autoOpen : false,
                resizable : specs.isResizable,
                height : specs.height,
                width : specs.width,
                resize : 'auto',
                title : specs.title,
                modal : specs.isModal,
                draggable : specs.isDraggable,
                buttons : [ {
                    text : specs.validationMsg,
                    click : function() {
                        jQuery(this).dialog("close");
                        trigger(jQuery("#" + specs.id));
                    }
                }, {
                    text : specs.cancelMsg,
                    click : function() {
                        jQuery(this).dialog("close");
                    }
                } ]
            });
        }

        jQuery('#' + specs.id).bind("click", function(event) {

            var element = jQuery(this);
            if (element.data('confirmed')) {
                element.data('confirmed', false);
                // perform regular click event
                return true;
            }

        	
            event.preventDefault();
            event.stopImmediatePropagation();

            if (specs.useDefaultConfirm) {
                //Default javascript confirmation box.
                if(confirm(specs.message)){
                    trigger(element);
                }
            } else {
                dialogBox.dialog('open');
            }
        });

    };
    
    /**
	 * Sets a ‘confirmed’ flag and triggers the actual click event. 
	 * 
	 * @param HTML element
	 */
    function trigger(element) {
    	 element.data('confirmed', true);
         element[0].click();
    }

    return init;
});