(function($) {
	window.T5JQZoneUpdater = function(spec) {
		var elementId = spec.elementId;
		var element = document.getElementById(elementId);
		var url = spec.url;
		var $zone = $('#' + spec.zone);

		var updateZone = function() {
			var updatedUrl = url;
			var params = {};  
			if (element.value) {
				params.param = element.value;
			}
			$zone.tapestryZone('update', {
				url : updatedUrl, 
				params : params
			});
		}

		if (spec.event) {
			var event = spec.event;
			$(element).bind(event, updateZone);
		}

		return {
			updateZone : updateZone
		};
	};
})(jQuery);
