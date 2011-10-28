function callbackFunction(){

	var columnChooser = function(){ 
		alert("This is the JavaScript callback function");
	}

	return columnChooser;
}


(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			$("#foo").data('extra',{test: 16, teste: 17});
	    }
		
		return {
			setData : init
		}
	});
	
}) ( jQuery );