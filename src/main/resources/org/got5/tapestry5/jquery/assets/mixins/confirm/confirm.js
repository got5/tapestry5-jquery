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
		 * Trigger url redirection or firm submission from element.
		 * 
		 * @param HTML element
		 */
		function trigger(element) {
			var tagName = element.prop('tagName');
			switch (tagName) {
			//Simple link (pagelink, actionlink, etc...)
			case 'A':
				var href = element.prop('href');
				if (href != undefined) {
					var urlSuffix = href.substring(href.lastIndexOf('.') + 1);
					if (urlSuffix != null && element.prop('id').toLowerCase().indexOf(urlSuffix.toLowerCase()) == 0) {
						//ActionLink
						element.trigger(Tapestry.TRIGGER_ZONE_UPDATE_EVENT);
					} else {
						window.location.href = href;
					}
				}
				break;
			//submit button.
			case 'INPUT':
				element.parents('form').submit();
				break;
			default:
				break;
			}
		}

		return {
			confirm : init
		};
	});
})(jQuery);