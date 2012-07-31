/**
 * 
 * @param spec.action 
 *             the event-link-url
 * @param spec.params
 *             
 * @param spec.messages
 *             localized messages
 * 
 * @param spec.multiple 
 *             enables multiple file upload if possible. (default: true)            
 * 
 * @param spec.maxConnections
 *             maximum number of parallel connections. (default: 3)
 * 
 * @param spec.allowedExtensions
 *             Array of file allowed extensions. Accept all files if emtpy (default)
 * 
 * @param spec.sizeLimit
 *              
 * @param spec.minSizeLimit
 * 
 * @param spec.showMessagesDialog
 *              The id of the error message dialog.
 */
(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			
			var el = $('#' + spec.elementId);
			
			var options = {
				showMessage: function(message) {

					$('#' + spec.showMessagesDialog).text(message).dialog('open');

				},

				onComplete: function(id, fileName, responseJSON){
					
					if (responseJSON.zones) {

	                    // perform multi zone update
	                    $.each(responseJSON.zones, function(zoneId){

	                        $('#' + zoneId).tapestryZone("applyContentUpdate", responseJSON.zones[zoneId]);
	                    });

	                    $.tapestry.utils.loadScriptsInReply(responseJSON);
	                }
					
					if (responseJSON.updateZone) {

						var spec = { 
								url : responseJSON.updateZone.url, 
								params : responseJSON.updateZone.params
						};

						$('#' + responseJSON.updateZone.zoneId).tapestryZone("update", spec);
					}

				}, 
				
	            template: '<div class="qq-uploader">' +
	                '<div class="qq-upload-drop-area"><span>' + spec.messages.dropAreaLabel + ' </span></div>' +
	                '<a class="qq-upload-button btn">' + spec.messages.uploadLabel + '</a>' +
	                '<ul class="qq-upload-list"></ul>' +
	                '</div>',

	            // template for one item in file list
	            fileTemplate: '<li>' +
	                    '<span class="qq-upload-file"></span>' +
	                    '<span class="qq-upload-spinner"></span>' +
	                    '<span class="qq-upload-size"></span>' +
	                    '<a class="qq-upload-cancel" href="#">' + spec.messages.cancelLabel + ' </a>' +
	                    '<span class="qq-upload-failed-text">' + spec.messages.failedLabel + '</span>' +
	                    '</li>'
				
			};
			$.extend(options, spec);
	        el.fileuploader(options);
		}
		
		return {
			uploadable : init
		}
	});
	
}) ( jQuery );