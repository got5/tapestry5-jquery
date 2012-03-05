(function( $ ) {

$.extend(Tapestry.Initializer, {
	zoneRefresh: function(params) {  
	
	   //  Ensure a valid period. Not required as PeriodicalUpdater already takes care of it
	   if(params.period <= 0){
	      return;
	   }

	   // get zone
	   var element = $("#" + params.id);
	  
	   // Function to be called for each refresh
	   var keepUpdatingZone = function(e)
	   {
	      try
	      {
	            element.tapestryZone("update" , {url : params.URL});
	      }
	      catch(e)
	      {
	         e.stop();
	         Tapestry.error(Tapestry.Messages.invocationException, {
	            fname : "Tapestry.Initializer.zoneRefresh",
	            params : params,
	            exception : e
	            });
	      }
	      
	   };

	   var timer = window.setInterval(keepUpdatingZone,params.period*1000);
	   
	   $(window).unload(function(){
	     window.clearInterval(timer); 
	   });
	   
	   element.bind('stopRefresh', function(){
    	   window.clearInterval(timer); 
       });
	   
	   element.bind('startRefresh', function(){
		   timer = window.setInterval(keepUpdatingZone,params.period*1000);
       });
	}
});

}) ( jQuery );