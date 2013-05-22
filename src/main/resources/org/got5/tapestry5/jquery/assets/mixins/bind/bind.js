(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var el = $('#'+specs.elementId);
			var url = specs.url;
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

			$(el).bind( specs.eventType, function(event,ui) {
				if ( preventDefault ) {
					event.preventDefault();
				}
				
				if ( title ) {
					document.title=title;
				}			
				if ( hide ) {
					$('#' + hide).hide(hideEffect,options,hideTime);
				}
				if ( zoneUpdate ) {
					zoneElement.tapestryZone('option','update',zoneUpdate);
				}
				
				var u = new Object();
				u.url = url;
				
				//If the element using the bind mixin has a value, we automatically added to tue url
				if($(this).val() !== "") u.url += ("/" + $(this).val()) 	
					
				u.context = contextMarker;		
				u.element = $(this);
				
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
						zoneElement.tapestryZone("update",{url : u.url});
					} else {
						$.ajax({
							type: "POST",
							url: u.url
						});
					}
				}
			});
		}
		
		return {
			jqbind : init
		}
	});
	
}) ( jQuery );