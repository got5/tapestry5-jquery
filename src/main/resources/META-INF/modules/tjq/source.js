requirejs.config({
	"shim" : {
		"tjq/vendor/components/showsource/jquery.snippet": ["jquery"]
	}
});

define(["tjq/vendor/components/showsource/codemirror"], function() {
	init = function(specs) {

        var snippet = jQuery('#' + specs.id),
            snippetContainer = snippet.parent(),
            editor = CodeMirror.fromTextArea(document.getElementById(specs.id), specs.options),
            show = snippetContainer.parent().find('.show'),
            hide = snippetContainer.parent().find('.hide');

        hide.hide();
        snippetContainer.hide();

        show.on('click', {}, function () {
            show.hide();
            hide.show();
            snippetContainer.show();
        });

        hide.on('click', {}, function () {
            hide.hide();
            show.show();
            snippetContainer.hide();
        });
        	
        
	};
  	
  	return exports = init;
});