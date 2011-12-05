/* Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function($){
$.extend(Tapestry.Initializer, {treeNode :  function (spec) {
	
	var id = spec.clientId;
    var loaded = spec.expanded || spec.leaf;
    var expanded = spec.expanded;
    var loading = false;
    if (expanded)
        $("#"+id).addClass("t-tree-expanded");
    var cfg = spec.options || {};
    function doAnimate(element) {
        var sublist = $("#"+element).parent('li').first().children("ul").first();
        sublist.toggle(cfg.effect, cfg.options);
    }

    function animateRevealChildren(element) {
        $("#"+element).addClass("t-tree-expanded");
        doAnimate(element);
    }

    function animateHideChildren(element) {
        $("#"+element).removeClass("t-tree-expanded");
        doAnimate(element);
    }

    function successHandler(reply) {
        // Remove the Ajax load indicator
        $("#"+id).html("").removeClass("t-empty-node");

        $.tapestry.utils.loadScriptsInReply(reply, function() {
            var element = $("#"+id).parent("li").first();
            element.children("span.t-tree-label").first().after(reply.content);
            // Hide the new sublist so that we can animate revealing it.
            element.children("ul").first().hide();

            animateRevealChildren(id);

            loading = false;
            loaded = true;
            expanded = true;
        });

    }

    function doLoad() {
        if (loading)
            return;

        loading = true;

        $("#"+id).addClass("t-empty-node").html("<span class='t-ajax-wait'/>");

        $.ajax({url:spec.expandChildrenURL, success:successHandler});
    }

    $("#"+id).click(function(event) {
    	event.stopPropagation();

        if (!loaded && spec.expandChildrenURL) {

            doLoad();

            return;
        }

        // Children have been loaded, just a matter of toggling
        // between showing or hiding the children.

        var f = expanded ? animateHideChildren : animateRevealChildren;
        f.call(null, id);

        var url = expanded ? spec.markCollapsedURL : spec.markExpandedURL;

        // Send request, ignore response.

        $.ajax({url:url});

        expanded = !expanded;
        return false;
    });


    if (spec.selectURL) {

        var selected = spec.selected;

        var label = $("#"+id).next("span.t-tree-label").addClass("t-selectable");
		
		
        if (selected) {
            label.addClass("t-selected-leaf-node-label");
        }

        label.click(function(event) {
        	event.stopPropagation();

            selected = ! selected;

            if (selected) {
                label.addClass("t-selected-leaf-node-label");
            }
            else {
                label.removeClass("t-selected-leaf-node-label");
            }

            // TODO: In the future, we may want to select children when a parent is selected,
            // or vice-versa. There's a lot of use cases. These will be directed from new methods
            // on the TreeSelectionModel interface and encoded into the response. For now,
            // the response is empty and ignored.
            $.ajax({url:spec.selectURL, data: { "t:selected": selected }});
            return false;
        });
    }
}});
})(jQuery);