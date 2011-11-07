function callbackFunction(){

	var columnChooser = function(){ 
		alert("This is the JavaScript callback function");
	}

	return columnChooser;
}


(function( $ ) {
	
	function fnFormatDetails ( name )
	{
		var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		sOut += '<tr><td>You clicked on </td><td>'+name+'</td></tr>';
		sOut += '</table>';
		return sOut;
	}
	
	$.extend(Tapestry.Initializer, {
		dataTable: function(specs) {
			
			var nCloneTd = '<a>open</a>';
			
			$('#' + specs.id + ' tbody tr td:first-child').each( function () {
				var context = $(this).html();
				
				$(this).contents().remove();
				
				var img = $("<img />").attr('src', specs.params.openImg);
				
				img.data('id', $.trim(context));
				
				$(this).append(img);
			});	
			
			var oTable= $('#' + specs.id).dataTable(specs.params);
			
			$('#' + specs.id + ' tbody td img').live('click', function () {
				
				var nTr = this.parentNode.parentNode;
				
				if($(this).attr('src')==specs.params.openImg){
					
					$(this).attr('src',specs.params.closeImg);
					
					//Ajax Request
					$.ajax({
						url: specs.params.ajaxUrl, 
						data: "name="+$(this).data('id'), 
						success: function(response){
							oTable.fnOpen(nTr, fnFormatDetails(response.name), 'details')
						}
					});
				}
				else {
					$(this).attr('src',specs.params.openImg);
					oTable.fnClose( nTr );
				}
				
				
			});
	    }
	});
}) ( jQuery );