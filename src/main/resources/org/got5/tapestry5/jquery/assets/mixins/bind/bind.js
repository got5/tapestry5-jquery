(function( $ ) {
	$.extend(Tapestry.Initializer, {
		jqbind: function(specs) {

			var el = $('#'+specs.elementId);
			var url = specs.url;
			var zoneId = specs.zoneId;
			var hide = specs.hide;
			var hideEffect = specs.hideEffect;
			var hideTime = specs.hideTime;
			var options = specs.hideOptions;
			var zoneUpdate = specs.zoneUpdate;
			var title = specs.title;
			// can't pass function thru JSON
			var callback = eval('[' + specs.callback + ']')[0];
			var contextMarker = specs.contextMarker;
			var preventDefault = specs.preventDefault;
			var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);

			$(el).bind( specs.eventType, function(event,ui) {
				if ( preventDefault ) {
					event.preventDefault();
				}
				// do history here
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
				if ( callback ) {
					u.context = contextMarker;					
					u.element = $(this);
					// TODO should work if value is array
					u.addContext = function(value) { u.url = u.url.replace(u.context,value) };
					callback(event,ui,u);
				}
				if ( u.url ) {
					
					if ( zoneId ) {
						zoneElement.tapestryZone("update",{url : u.url});
					} else {
						$.ajax({url: u.url});
					}
				}
			});
		}
	});
}) ( jQuery );