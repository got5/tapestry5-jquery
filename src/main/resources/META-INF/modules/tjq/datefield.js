(function() {
	 locale = (document.documentElement.getAttribute("data-locale")) || "en";	
	 var datepickerLocal= "tjq/vendor/ui/i18n/jquery.ui.datepicker-"+locale;
define(["t5/core/dom", "t5/core/events","tjq/vendor/ui/jquery.ui.datepicker", "tjq/vendor/ui/jquery-ui.custom",datepickerLocal], function(dom, events) {

  scan = function(root) {
    var container, _i, _len, _ref, _results, field;
    _ref = root.find("[data-component-type='core/DateField']");
    _results = [];
    var params = {};
    
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      container = _ref[_i];
      container.attr("data-component-type", null);
      
      container.findFirst("button").remove();
      field = container.findFirst("input").$

      //jQuery.datepicker.setDefaults(jQuery.datepicker.regional[locale]);
      params = jQuery.extend({
                gotoCurrent: true,
                showOn: "button",
                buttonImageOnly: false,
                disabled: field.attr("disabled"), 
                defaultDate: field.val() 
            }, field.data('cutomDatepicker'));
      _results.push(
      		field.datepicker(params));
    }
    return _results;
  };
  scan(dom.body);
  dom.onDocument(events.zone.didUpdate, function() {
    return scan(this);
  });
  return null;
});
}).call(this);
