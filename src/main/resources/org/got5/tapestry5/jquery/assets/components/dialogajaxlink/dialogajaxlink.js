(function( $ ) {

    T5.extendInitializers(function(){

        function init(spec) {
            var element = spec.element;
            var zoneId = spec.zoneId;
            var dialogId = spec.dialogId;
            var url = spec.url;

            var dialog = $('#' + dialogId),
                zone   = $("#" + zoneId);    

            $("#" + element).click(function(e) {

                e.preventDefault();
                zone.tapestryZone("update", {
                    url: url,
                    callback: function() {
                        dialog.dialog('open');
                    }
                });

                return false;
            });
        }

        return {
            dialogAjaxLink : init
        };
    });

}) ( jQuery );
