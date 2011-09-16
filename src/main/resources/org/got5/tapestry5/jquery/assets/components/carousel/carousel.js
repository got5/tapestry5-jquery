(function( $ ) {

var loadCarousel = function(spec){
	function mycarousel_itemLoadCallback(carousel, state){
        if (state != 'init') {
			return;
		}
		jQuery.get(spec.loadCallbackUrl, function(data) {
        	mycarousel_itemAddCallback(carousel, carousel.first, carousel.last, data, spec);
   		});
	};
	return mycarousel_itemLoadCallback;
};

function mycarousel_itemAddCallback(carousel, first, last, data, spec){
	$.each(data, function(i, d){
        carousel.add(i, mycarousel_getItemHTML(d, spec));
	});
    carousel.size(data.length);
}

function mycarousel_getItemHTML(url, spec){
	var w=spec.width ? spec.width : "75px";
	var h=spec.height ? spec.height : "75px";
    return '<img src="' + url + '" width="'+w+'" height="'+h+'" alt="" />';
}
T5.extendInitializers(function(){
	
	function init(specs) {
		if(specs.params.loadCallbackUrl != undefined){
    		specs.params.itemLoadCallback = loadCarousel(specs.params); 
    	}
        $("#" + specs.id).jcarousel(specs.params);
	}
	
	return {
		carousel : init
	}
});	

}) ( jQuery );

