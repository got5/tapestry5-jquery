(function($) {
    T5.extendInitializers(function() {

		function init(specs) {
			
            //JQuery dialog box configuration, if used.
            if (!specs.useDefaultConfirm) {
                var dialogBox = $('<div id="dialogConfirmationJQuery" />').html(specs.message).dialog({
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
                            $(this).dialog('close');

                            trigger($('#' + specs.id));
                        }
                    },
                    {
                        text : specs.cancelMsg,
                        click : function() {
                            $(this).dialog('close');
                        }
                    } ]
                });
            }

            $('#' + specs.id).bind("click", function(event) {
                
                var element = $(this);

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
                        trigger($('#' + specs.id));
                    }
                } else {
                    dialogBox.dialog('open');
                }
            });
		}
		
		/**
		 * Sets a ‘confirmed’ flag and triggers the actual click event. 
		 * 
		 * @param HTML element
		 */
		function trigger(element) {
            element.data('confirmed', true);
            element[0].click();
		}

		return {
			confirm : init
		};
	});
})(jQuery);