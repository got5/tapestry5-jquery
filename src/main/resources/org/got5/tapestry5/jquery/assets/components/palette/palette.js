(function( $ ) {

$.widget( "ui.palette", {
	options: {
	},

	_create: function() {
		this.element
			.addClass( "tapestry-palette" )

		var id = "#" + this.element.attr("id"),
			element = this.element,
			that = this;

		this.selected = element;
        this.avail = $(id + "-avail");
        this.hidden = $(id + "-values");
        this.select = $(id + "-select");
        this.deselect = $(id + "-deselect");

        this.up = $(id + "-up");
        this.down = $(id + "-down");

        this.valueToOrderIndex = {};

        if (this.options.reorder)
            this.reorder = this.options.reorder;

		$.each(this.options.naturalOrder, function(i, value) {
            that.valueToOrderIndex[value] = i;
        });

        this.avail.bind({
            change: function() {
                element.palette("updateButtons");
            },

            dblclick: function() {
                element.palette("selectClicked");
            }
        });

        this.selected.bind({
            change: function() {
                element.palette("updateButtons");
            },

            dblclick: function() {
                element.palette("deselectClicked");
            }
        });

        this.select.bind({
            click: function() {
                element.palette("selectClicked");
                return false;

            },
            dblclick: function() {
                element.palette("selectClicked");
            }
        });

        this.deselect.bind({
            click: function() {
                element.palette("deselectClicked");
                return false;
            }
        });


        if (this.reorder) {
            this.up.bind("click", function() {
                element.palette("moveUpClicked");
                return false;
            });

            this.down.bind("click", function() {
                element.palette("moveDownClicked");
                return false;
            });
        }

	},

	destroy: function() {
		this.element
			.removeClass( "tapestry-palette");

		$.Widget.prototype.destroy.apply( this, arguments );
	},


	updateButtons: function( arg ) {
	    this.select.attr("disabled", this.avail.get(0).selectedIndex < 0);

	    var nothingSelected = this.selected.get(0).selectedIndex < 0;

	    this.deselect.attr("disabled", nothingSelected);

	    if (this.reorder) {
	        this.up.attr("disabled", (nothingSelected || this.allSelectionsAtTop()));
	        this.down.attr("disabled", (nothingSelected || this.allSelectionsAtBottom()));
	    }
	},

    indexOfLastSelection: function() {
        var selectElem = this.selected.get(0);

        if (selectElem.selectedIndex < 0)
            return -1;

        for (var i = selectElem.options.length - 1; i >= selectElem.selectedIndex; i--) {
            if (selectElem.options[i].selected)
                return i;
        }

        return -1;
    },

	allSelectionsAtTop: function() {
        var last = this.indexOfLastSelection(this.selected);
        var options = this.selected[0].options;

        for (var i=0; i<last; i++)
            if (!options[i].selected) return false;
        return true;
    },

    allSelectionsAtBottom: function() {
        var options = this.selected[0].options;

        // Make sure that all elements from the (first) selectedIndex to the end are also selected.
        for (var i=0; i<options.length; i++)
            if (!options[i].selected) return false;
        return true;
    },

    selectClicked: function() {
        this.transferOptions(this.avail, this.selected, this.reorder);
    },

    deselectClicked: function() {
        this.transferOptions(this.selected, this.avail, false);
    },

    transferOptions: function (from, to, atEnd) {
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

        var movers = this.removeSelectedOptions(fromElem);

        this.moveOptions(movers, to.get(0), atEnd);
    },

    updateHidden: function() {
        // Every value in the selected list (whether enabled or not) is combined to form the value.
        var values = $.map(this.selected[0].options, function(o) {
            return o.value;
        });

        this.hidden[0].value =  $.toJSON(values);
    },

    moveUpClicked: function() {
        var pos = this.selected[0].selectedIndex - 1;
        var movers = this.removeSelectedOptions(this.selected[0]);

        var before = pos < 0 ? this.selected[0].options[0] : this.selected[0].options[pos];

        this.reorderSelected(movers, before);
    },

    removeSelectedOptions: function( from ) {
        var movers = [];

        var options = from.options;

        for (var i = from.selectedIndex; i < from.length; i++) {
            var option = options[i];

            if (option.selected) {
                try {
	               from.remove(i);
			    } catch (e){
				    //select.remove(..) throws exception in ie9, when select contains optgroup
				    //see http://social.msdn.microsoft.com/Forums/is/iewebdevelopment/thread/2ab12546-7bb4-41b6-8d36-442cc0ecb153
				    $(from).find("option[value='"+option.value+"']").remove();
				}
				i--;
                movers.push(option);
            }
        }

        return movers;
    },

    moveOptions: function (movers, to, atEnd) {
		var that = this;

        $.each(movers, function(i, option) {
            that.moveOption(option, to, atEnd);
        });

        this.updateHidden();
        this.updateButtons();
    },

    moveOption: function (option, to, atEnd) {
        var before,
			that=this;

        if (!atEnd) {
            var optionOrder = this.valueToOrderIndex[option.value],
				candidate;

            $.each(to.options, function(i, o) {
                if (that.valueToOrderIndex[o.value] > optionOrder) {
                    candidate = o;
                    return false;
                }
            });

            if (candidate)
                before = candidate;
        }

        this.addOption(to, option, before);
    },

    addOption: function (to, option, before) {
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

    },

    moveDownClicked: function () {
        var lastPos = 0;
        for (var i = 0 ; i < this.selected[0].options.length; i++)
            if (this.selected[0].options[i].selected) lastPos = i;
        var before = this.selected[0].options[lastPos + 2];
        var movers = this.removeSelectedOptions(this.selected[0]);

        this.reorderSelected(movers, before);
    },

    reorderSelected: function (movers, before) {
		var that=this;

        $.each(movers, function(i, option) {
            that.addOption(that.selected[0], option, before);
        });

        this.updateHidden();
		this.updateButtons();
    }
});

T5.extendInitializers(function(){

	function init(options) {
		$("#" + options.id).palette(options);
	}

	return {
		palette : init
	}
});

})( jQuery );
