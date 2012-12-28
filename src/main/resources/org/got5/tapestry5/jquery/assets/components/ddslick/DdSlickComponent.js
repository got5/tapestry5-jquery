//function JS to initialize the ddslick
(function($){
	$.extend(Tapestry.Initializer, {
		initDdSlickComponent : function(params){
		
			$('#'+params.elementId).ddslick({
				data: params.ddData,
				selectText: params.ddSelectText,
				defaultSelectedIndex: params.ddDefaultSelectedIndex,
				imagePosition: params.ddImagePosition,
				truncateDescription : params.ddTruncateDescription,
				background : params.ddBackground,
				width : params.ddWidth,
				height : params.ddHeight,
				showSelectedHTML : params.ddShowSelectedHTML,
						
				onSelectedElement: function(data){
					var zoneId = params.zone;
					var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);
					var url = params.url;
					var parameters = {};
					parameters = data.selectedData;
					
					if(zoneId){
						zoneElement.tapestryZone('update', {url : url, params : parameters});
					}else{
						$.ajax({url: url, params: parameters});
					}
				}
			}); 
		
		}
	});
})(jQuery);