(function($) {

	T5.extendInitializers(function() {

		function init(specs) {
		    var snippet = $('#' + specs.id);

            var editor = CodeMirror.fromTextArea(document.getElementById(specs.id), { 
                lineNumbers: true,
                firstLineNumber: specs.beginLine !== 0 ? specs.beginLine : 1 
            });
		}

		return {
			source : init
		};
	});

})(jQuery);