(function($) {
    /**
     *  Container of functions that may be invoked by the Tapestry.init() function.
     */
    $.extend(Tapestry.Initializer, {
        palette: function(options) {
            $("#" + options.id).tapestryPalette(options);
        }
    });
    
    
    $.fn.extend({
        tapestryPalette: function(options, arg) {
            if (options && typeof(options) == 'object') {
                options = $.extend({}, $.tapestry.palette.defaults, options);
            }
            
            this.each(function() {
                new $.tapestry.palette(this, options, arg);
            });
            
            return;
        }
    });
    
    
    $.tapestry.palette = function(elem, options, arg) {
        var selected = $("#" + elem.id);
        var avail = $("#" + elem.id + "-avail");
        
        var hidden = $("#" + elem.id + "-values");
        
        var select = $("#" + elem.id + "-select");
        var deselect = $("#" + elem.id + "-deselect");
        
        var up = $("#" + elem.id + "-up");
        var down = $("#" + elem.id + "-down");
        
        
        /**
         * Function caller
         */
        if (options && typeof(options) == 'string') {
            switch (options) {
                case "updateButtons":
                    updateButtons(arg);
                    return true;
                case "selectClicked":
                    selectClicked(arg);
                    return true;
                case "deselectClicked":
                    deselectClicked(arg);
                    return true;
                default:
                    return true;
            }
        }
        
        var valueToOrderIndex = {};
        var reorder;
        
        if (options) {
            reorder = options.reorder;
            
            $.each(options.naturalOrder, function(i, value) {
                valueToOrderIndex[value] = i;
            });
            
            select.data("tapestry.palette", {
                reorder: reorder,
                valueToOrderIndex: valueToOrderIndex
            })
        }
        
        avail.bind({
            change: function() {
                $(selected).tapestryPalette("updateButtons");
            },
            
            dblclick: function() {
                $(selected).tapestryPalette("selectClicked");
            }
        });
        
        selected.bind({
            change: function() {
                $(selected).tapestryPalette("updateButtons");
            },
            
            dblclick: function() {
                $(selected).tapestryPalette("deselectClicked");
            }
        });
        
        select.bind({
            click: function() {
                $(selected).tapestryPalette("selectClicked");
                return false;
                
            },
            dblclick: function() {
                $(selected).tapestryPalette("selectClicked");
            }
        });
        
        deselect.bind({
            click: function() {
                $(selected).tapestryPalette("deselectClicked");
                return false;
            }
        });
        
        
        
        if (reorder != undefined) {
            up.bind("click", function() {
                $(selected).tapestryPalette("moveUpClicked");
                return false;
            });
            
            down.bind("click", function() {
                $(selected).tapestryPalette("moveDownClicked");
                return false;
            });
        }
        
        /**
         * Functions
         */
        function updateButtons(arg) {
            select.attr("disabled", avail.get(0).selectedIndex < 0);
            
            var nothingSelected = selected.get(0).selectedIndex < 0;
            var reorder = select.data("tapestry.palette").reorder;
            
            deselect.attr("disabled", nothingSelected);
            
            if (reorder) {
                up.attr("disabled", (nothingSelected || allSelectionsAtTop()));
                down.attr("disabled", (nothingSelected || allSelectionsAtBottom()));
            }
        }
        
        function indexOfLastSelection() {
            var selectElem = select.get(0);
            
            if (selectElem.selectedIndex < 0) 
                return -1;
            
            for (var i = selectElem.options.length - 1; i >= selectElem.selectedIndex; i--) {
                if (selectElem.options[i].selected) 
                    return i;
            }
            
            return -1;
        };
        
        function allSelectionsAtTop() {
            var last = indexOfLastSelection(selected);
            var options = selected.options;
            
            $.each(options.slice(0, last), function(i, value) {
                if (!value.selected) 
                    return false;
            });
            return true;
        };
        
        function allSelectionsAtBottom() {
            var options = selected.options;
            
            // Make sure that all elements from the (first) selectedIndex to the end are also selected.
            $.each(options, function(i, value) {
                if (!value.selected) 
                    return false;
            });
            return true;
        };
        
        function selectClicked() {
            transferOptions(avail, selected, this.reorder);
        };
        
        function deselectClicked() {
            transferOptions(selected, avail, false);
        };
        
        function transferOptions(from, to, atEnd) {
            // don't bother moving the options if nothing is selected. this can happen
            // if you double-click a disabled option
            var fromElem = from.get(0);
            
            if (fromElem.selectedIndex == -1) 
                return;
            
            // from: SELECT to move option(s) from (those that are selected)
            // to: SELECT to add option(s) to
            // atEnd : if true, add at end, otherwise by natural sort order
            
            $.each(to.get(0).options, function(i, option) {
                option.selected = false;
            });
            
            var movers = removeSelectedOptions(fromElem);
            
            moveOptions(movers, to.get(0), atEnd);
        };
        
        function updateHidden() {
            // Every value in the selected list (whether enabled or not) is combined to form the value.
            var values = selected.map(function(o) {
                return o.value;
            });
            
            hidden.value = values;
        };
        
        function moveUpClicked() {
            var pos = selected.selectedIndex - 1;
            var movers = removeSelectedOptions(selected);
            
            var before = pos < 0 ? selected.options[0] : selected.options[pos];
            
            this.reorderSelected(movers, before);
        };
        
        function removeSelectedOptions(from) {
            var movers = [];
            
            var options = from.options;
            
            for (var i = from.selectedIndex; i < from.length; i++) {
                var option = options[i];
                
                if (option.selected) {
                    from.remove(i--);
                    movers.push(option);
                }
            }
            
            return movers;
        };
        
        function moveOptions(movers, to, atEnd) {
            $.each(movers, function(i, option) {
                moveOption(option, to, atEnd);
            });
            
            updateHidden();
            updateButtons();
        };
        
        
        function moveOption(option, to, atEnd) {
            var before = null;
            var valueToOrderIndex = select.data("tapestry.palette").valueToOrderIndex;
            
            if (!atEnd) {
                var optionOrder = valueToOrderIndex[option.value];
                var candidate;
                $.each(to.options, function(i, o) {
                    if (valueToOrderIndex[o.value] > optionOrder) {
                        candidate = o;
                        return false;
                    }
                });
                if (candidate) 
                    before = candidate;
            }
            addOption(to, option, before);
        };
        
        function addOption(to, option, before) {
            try {
                to.add(option, before);
            } 
            catch (ex) {
                //probably IE complaining about type mismatch for before argument;
                if (before == null) {
                    //just add to the end...
                    to.add(option);
                }
                else {
                    //use option index property...
                    to.add(option, before.index);
                }
            }
            
        };
        
        function moveDownClicked() {
            var lastSelected = $A(selected.options).reverse(true).find(function(option) {
                return option.selected;
            });
            var lastPos = lastSelected.index;
            var before = selected.options[lastPos + 2];
            
            // TODO: needs to be "reorder options"
            reorderSelected(removeSelectedOptions(selected), before);
            
        };
        
        function reorderSelected(movers, before) {
            $.each(movers, function(i, option) {
                addOption(selected, option, before);
            });
            
            updateHidden();
            updateButtons();
        }
    };
    
    $.tapestry.palette.defaults = {};
    
    
    
    
})(jQuery);
