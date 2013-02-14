requirejs.config({
	"shim" : {
		"tjq/jgrowl/jquery.jgrowl": ["jquery"]
	}
});

define(["t5/core/dom", "t5/core/console", "t5/core/messages", "t5/core/builder", "t5/core/ajax", "_", "tjq/jgrowl/jquery.jgrowl"], 
function(dom, console, messages, builder, ajax, _) {
  var alert, dismissAll, dismissOne, exports, findInnerContainer, getURL, removeAlert, setupUI, severityToClass;
  severityToClass = {
    success: "success alert alert-success",
    warn: "warn alert alert-warning",
    error: "error alert alert-error",
    info: "info alert alert-success",
  };
  getURL = function(container) {
    return container.attribute("data-dismiss-url");
  };
  
  dismissOne = function(id) {
    
    console.debug("dismiss single");
    
    return ajax(getURL(findInnerContainer()), {
      parameters: {
        id: id
      },
      success: function() {
        
      }
    });
  };
  
  findInnerContainer = function() {
    var outer;
    outer = dom.body().findFirst("[data-container-type=alerts]");
    if (!outer) {
      console.error("Unable to locate alert container element to present an alert.");
      return null;
    }
    return outer;
  };
  
  alert = function(data) {
    var className, container, content, element;
   
    className = "jgrowl-" + (severityToClass[data.severity] || "alert");
    content = data.markup ? data.message : dom.escapeHTML(data.message);
    
	var params = {
			close : function(){
				dismissOne(data.id);
    		}, 
    		life : exports.TRAINSIENT_DURATION, 
    		sticky : !data.transient, 
    		theme : className 
	};
	
	jQuery.jGrowl(content, params);
  };
  alert.TRAINSIENT_DURATION = 5000;
  return exports = alert;
});
