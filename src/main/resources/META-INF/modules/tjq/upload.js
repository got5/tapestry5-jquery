/**
 * 
 * @param spec.action
 *            the event-link-url
 * @param spec.params
 * 
 * @param spec.messages
 *            localized messages
 * 
 * @param spec.multiple
 *            enables multiple file upload if possible. (default: true)
 * 
 * @param spec.maxConnections
 *            maximum number of parallel connections. (default: 3)
 * 
 * @param spec.allowedExtensions
 *            Array of file allowed extensions. Accept all files if emtpy
 *            (default)
 * 
 * @param spec.sizeLimit
 * 
 * @param spec.minSizeLimit
 * 
 * @param spec.showMessagesDialog
 *            The id of the error message dialog.
 */
 define(["vendor/upload"], function() {
	init = function(spec) {
		var el = jQuery('#' + spec.elementId);

					var options = {
						showMessage : function(message) {

							jQuery('#' + spec.showMessagesDialog).text(message)
									.dialog('open');

						},

						onComplete : function(id, fileName, responseJSON) {

							if (responseJSON.zones) {

								// perform multi zone update

								var zoneManager = Tapestry
										.findZoneManagerForZone("uploadResult");
								if (!zoneManager)
									return;
								zoneManager.processReply(responseJSON);

							}

							if (responseJSON.updateZone) {

								var spec = {
									url : responseJSON.updateZone.url,
									params : responseJSON.updateZone.params
								};

								var zoneObject = Tapestry
										.findZoneManagerForZone(responseJSON.updateZone.zoneId);

								if (!zoneObject)
									return;

								zoneObject.updateFromURL(spec.url, spec.params);

							}

						},

						template : '<div class="qq-uploader">'
								+ '<div class="qq-upload-drop-area"><span>'
								+ spec.messages.dropAreaLabel
								+ ' </span></div>'
								+ '<a class="qq-upload-button btn">'
								+ spec.messages.uploadLabel + '</a>'
								+ '<ul class="qq-upload-list"></ul>' + '</div>',

						// template for one item in file list
						fileTemplate : '<li>'
								+ '<span class="qq-upload-file"></span>'
								+ '<span class="qq-upload-spinner"></span>'
								+ '<span class="qq-upload-size"></span>'
								+ '<a class="qq-upload-cancel" href="#">'
								+ spec.messages.cancelLabel + ' </a>'
								+ '<span class="qq-upload-failed-text">'
								+ spec.messages.failedLabel + '</span>'
								+ '</li>'

					};
					jQuery.extend(options, spec);
					el.fileuploader(options);
	  };
  	
  	return exports = init;
});