(function( $ ) {

	T5.extendInitializers(function(){
		function init(specs) {
			$("#" + specs.id).fullCalendar({
				events: specs.events,
				header: specs.header,
				weekMode: "liquid",
				editable: "true",
				eventClick: function(calEvent, jsEvent, view) {
					$.ajax({
						url: specs.eventclickurl,
						data: calEvent,
						dataType: "json",
						success: function(data) {
							$(window.location).attr("href", data.redirectURL);
						}
					});
				},
				dayClick: function(eventdate, eventallday) {
					var epochdate = eventdate.getTime()/1000.0;
					$.ajax({
						url: specs.dayclickurl,
						data: {date: epochdate, allDay: eventallday},
						dataType: "json",
						success: function(data) {
							alert(data);
							$(window.location).attr("href", data.redirectURL);
						}
					});
				}
			});
        }
        
        return {
        	fcal: init
        }
	});
}) (jQuery);
