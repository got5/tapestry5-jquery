requirejs.config({
    "shim" : {
        "tjq/vendor/mixins/contextmenu/jquery.contextMenu": ["tjq/vendor/ui/jquery-ui.custom"]
    }
});
define(["t5/core/ajax", "tjq/vendor/mixins/contextmenu/jquery.contextMenu"], function(ajax) {
    init = function(specs) {

        if (specs.id == undefined || specs.keys == undefined || specs.items == undefined || specs.items == null) {
            return;
        }

        var items = {};
        var nbKeys = specs.keys.length;
        for (var index = 0; index < nbKeys; index++) {
            var key = specs.keys[index];
            items[key] = specs.items[key];
        }

        jQuery.contextMenu({
            selector: '#' + specs.id,
            callback: function(key, options) {
                ajax(items[key].url, {
                    methpd: "POST"
                })

            },
            items: items,
            trigger: specs.trigger,
            delay: specs.delay,
            autoHide: specs.autoHide,
            zIndex: specs.zIndex
        });

    };

    return exports = init;
});