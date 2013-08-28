function callbackFunction(){

	var columnChooser = function() { 
		alert('This is the JavaScript callback function');
	};

	return columnChooser;
}

(function( $ ) {

	T5.extendInitializers(function() {
		
		function fnFormatDetails ( name ) {
			var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
			sOut += '<tr><td>You clicked on </td><td>' + name + '</td></tr>';
			sOut += '</table>';
			return sOut;
		}

		return {
			setData : function init(specs) {
	            $('#foo').data('extra',{test: 16, teste: 17});
	        },

			dataTable : function dataTableExtra(specs) {

	            var nCloneTd = '<a>open</a>';

	            $('#' + specs.id + ' tbody tr td:first-child').each( function () {
	                var context = $(this).html();

	                $(this).contents().remove();

	                var img = $('<img />').attr('src', specs.params.openImg);

	                img.data('id', $.trim(context));

	                $(this).append(img);
	            }); 

	            var oTable = $('#' + specs.id).dataTable(specs.params);

                $(document).ready(function () {
                    
                    var body = $('body');
    	            body.delegate('#' + specs.id + ' tbody td img', 'click', function () {
    
    	                var nTr = this.parentNode.parentNode;
    
    	                if ($(this).attr('src') === specs.params.openImg) {
    
    	                    $(this).attr('src',specs.params.closeImg);
    
    	                    //Ajax Request
    	                    $.ajax({
    	                        url: specs.params.ajaxUrl, 
    	                        data: 'name='+$(this).data('id'), 
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
                });
	        },

			ajaxFormLoopCallback : function ajaxFormLoopCallback(specs) {
			    
			    $(document).ready(function () {
			        
			        var body = $('body');
			        
    	            body.delegate('.t-forminjector', Tapestry.AJAXFORMLOOP_ROW_ADDED, function () {
    	                $('#testCallback').removeClass('removed');
    	                $('#testCallback').addClass('added');
    	            });
    	            
    	            body.delegate('div.tapestry-forminjector, div.tapestry-formfragment', Tapestry.AJAXFORMLOOP_ROW_REMOVED, function(){
    	                $('#testCallback').removeClass('added');
    	                $('#testCallback').addClass('removed');
    	            });
			    });
	        } 
		};
	});

}) ( jQuery );