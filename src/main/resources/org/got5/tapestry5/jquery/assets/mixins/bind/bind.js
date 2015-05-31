(function( $ ) {

	T5.extendInitializers(function() {

		function init(specs) {
			var el = $('#' + specs.elementId);
			var url = specs.url;
			var postData = specs.postData;
			var zoneId = specs.zoneId;
			var hide = specs.hide;
			var hideEffect = specs.hideEffect;
			var hideTime = specs.hideTime;
			var options = specs.hideOptions;
			var zoneUpdate = specs.zoneUpdate;
			var title = specs.title;
			var history = eval('[' + specs.history + ']')[0];
			// can't pass function thru JSON
			var callback = eval('[' + specs.callback + ']')[0];
			var contextMarker = specs.contextMarker;
			var preventDefault = specs.preventDefault;
			var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);
			$(el).bind( specs.eventType, function(event, ui) {

			    var u = {
			            url : url,
			            element : $(this)
			        },
                value = u.element.val();

				if ( preventDefault ) {
					event.preventDefault();
				}
				
				if ( title ) {
					document.title = title;
				}			

				if ( hide ) {
					$('#' + hide).hide(hideEffect,options,hideTime);
				}

				if ( zoneUpdate ) {
					zoneElement.tapestryZone('option', 'update', zoneUpdate);
				}

				//If the element using the bind mixin has a value, we automatically added to tue url
				if (value) {
					var indexOf = u.url.indexOf('?');                    
				    if (indexOf != -1){
				        var q = u.url.substring(indexOf);
				        var start = u.url.substring(0, indexOf);
				        u.url = start + "/" + value + q;
				    }else{
				        u.url += "/" + value;
				    }
				}
				u.context = contextMarker;		
				if ( history ) {
					history(event,ui,u);
				}
				if ( callback ) {					
					// TODO should work if value is array
					u.addContext = function(value) { u.url = u.url.replace(u.context,value) };
					callback(event,ui,u);
				}
				if ( u.url ) {
					if ( zoneId ) {
						var zoneUpdateParams = {
								url : u.url
						};
						if (postData) {
							var eventData = {
									params : event.data
							};
							$.extend(zoneUpdateParams, eventData);
						}
						zoneElement.tapestryZone('update', zoneUpdateParams);
					} else {
						var ajaxParams = {
								type: 'POST',
								url: u.url
							};
						if (postData) {
							var eventData = {
									data : event.data
							};
							$.extend(ajaxParams,eventData);
						}
						$.ajax({
							type: 'POST',
							url: u.url
						});
					}
				}
			});
		}

		return {
			jqbind : init
		};
	});
	
}) ( jQuery );