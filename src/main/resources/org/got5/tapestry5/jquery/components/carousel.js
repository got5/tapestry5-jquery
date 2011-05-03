(function( $ ) {

$.extend(Tapestry.Initializer, {
    carousel: function(specs) {
        $("#" + specs.id).jcarousel(specs.params);
        
    }
});






}) ( jQuery );

var loadCarousel = function(url){
	function mycarousel_itemLoadCallback(carousel, state){
        if (state != 'init') {
			return;
		}
		jQuery.get(url, function(data) {
        	mycarousel_itemAddCallback(carousel, carousel.first, carousel.last, data);
   		});
	};
	return mycarousel_itemLoadCallback;
}


function mycarousel_itemAddCallback(carousel, first, last, data){
   	for (i = 0; i < data.length; i++) {
        carousel.add(i+1, mycarousel_getItemHTML(data[i]));
    }
    carousel.size(data.length);
};

function mycarousel_getItemHTML(url){
    return '<img src="' + url + '" width="75" height="75" alt="" />';
};
