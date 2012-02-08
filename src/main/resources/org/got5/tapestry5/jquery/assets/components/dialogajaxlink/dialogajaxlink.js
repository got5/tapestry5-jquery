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
                dialog.dialog('open');
                zone.tapestryZone("update", {
                    url: url
                });

                return false;
            });
        }

        return {
            dialogAjaxLink : init
        };
    });

}) ( jQuery );
