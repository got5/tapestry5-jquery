(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var p = specs.params;
			if(!p.ajaxOptions)
				p.ajaxOptions={};
			if(!p.ajaxOptions.beforeSend)
				$.extend(p.ajaxOptions, {beforeSend:function(){
					//returning false in beforeSend function cancels the AJAX request, see issue #52
					return false;
				}});
			$("#" + specs.id).tabs(p);
		}
		
		return {
			tabs : init
		}
	});
	
}) ( jQuery );