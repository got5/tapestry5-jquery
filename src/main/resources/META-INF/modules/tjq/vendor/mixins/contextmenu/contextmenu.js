(function($) {
	
	T5.extendInitializers(function() {
		
		function init(specs) {
			
			if (specs.id == undefined || specs.keys == undefined || specs.items == undefined || specs.items == null) {
				return;
			}
			
			var items = {};
			var nbKeys = specs.keys.length;
			for (var index = 0; index < nbKeys; index++) {
				var key = specs.keys[index];
				items[key] = specs.items[key];
			}
			
			$.contextMenu({
		        selector: '#' + specs.id, 
		        callback: function(key, options) {
		            var ajaxRequest = {
						 	type:"POST",
	                    	url:items[key].url
	                    };
	                    $.ajax(ajaxRequest);
		        },
		        items: items,
		        trigger: specs.trigger,
		        delay: specs.delay,
		        autoHide: specs.autoHide,
		        zIndex: specs.zIndex
		    });
		}
	
		return {
			contextmenu : init
		};
	});
})(jQuery);
