requirejs.config({
    "shim" : {
        "tjq/vendor/mixins/contextmenu/jquery.contextMenu": ["tjq/vendor/ui/jquery-ui.custom"]
    }
});
define(["t5/core/ajax", "tjq/vendor/mixins/contextmenu/jquery.contextMenu"], function(ajax) {
    return function(specs) {

        var items, nbKeys, key, index;
        
        if (!specs.id || !specs.keys || !specs.items || !specs.items) {
            return;
        }

        items = {};
        nbKeys = specs.keys.length;
        for (index = 0; index < nbKeys; index++) {
            key = specs.keys[index];
            items[key] = specs.items[key];
        }

        jQuery.contextMenu({
            selector: '#' + specs.id,
            callback: function(key, options) {
                ajax(items[key].url, {
                    method: "POST"
                })
            },
            items: items,
            trigger: specs.trigger,
            delay: specs.delay,
            autoHide: specs.autoHide,
            zIndex: specs.zIndex
        });
    };
});