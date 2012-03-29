(function( $ ) {

    T5.extendInitializers(function(){

        function init(spec) {
            $('#'+spec.div).addClass('reveal-modal');

            $(spec.id).click(function(e) {
                e.preventDefault();
                $('#'+spec.div).reveal({
                    animation:spec.animation,
                    animationspeed:spec.animationspeed,
                    closeonbackgroundclick:spec.closeonbackgroundclick,
                    dismissmodalclass:spec.dismissmodalclass
                });
            });
        }

        return {
            reveal : init
        };
    });

}) ( jQuery );
