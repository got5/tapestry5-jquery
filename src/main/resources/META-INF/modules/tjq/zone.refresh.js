define(["t5/core/dom", "t5/core/zone", "t5/core/events", "jquery"], 
function(dom, zone, events) {
	init = function(params) {
	    //  Ensure a valid period. Not required as PeriodicalUpdater already takes care of it
	   if(params.period <= 0){
	      return;
	   }

	   // get zone
	   var z = dom.wrap(params.id);	
	   
	   // Function to be called for each refresh
	   var keepUpdatingZone = function(e)
	   {
	   	  
		  if(z){
			z.trigger(events.zone.refresh, {
				url: params.URL 
			
			});
		  }	
	     
	    
	      
	   };

	   var timer = window.setInterval(keepUpdatingZone,params.period*1000);
	   
	   jQuery(window).unload(function(){
	     window.clearInterval(timer); 
	   });
	   
	   z.on('stopRefresh', function(){
    	   window.clearInterval(timer); 
       });
	   
	   z.on('startRefresh', function(){
		   timer = window.setInterval(keepUpdatingZone,params.period*1000);
       });
	  };
  	
  	return exports = init;
});