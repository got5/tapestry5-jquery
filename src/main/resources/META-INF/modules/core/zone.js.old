
define(["core/dom", "core/events", "core/ajax", "core/console", "core/forms", "_"], function(dom, events, ajax, console, forms, _) {
  var deferredZoneUpdate, findZone;
  findZone = function(element) {
    var zone, zoneId;
    zoneId = element.attribute("data-update-zone");
    if (zoneId === "^") {
      zone = element.findContainer("[data-container-type=zone]");
      if (zone === null) {
        console.error("Unable to locate containing zone for " + element + ".");
      }
      return zone;
    }
    zone = dom(zoneId);
    if (zone === null) {
      console.error("Unable to locate zone '" + zoneId + "'.");
    }
    return zone;
  };
  dom.onDocument("click", "a[data-update-zone]", function() {
    var zone;
    zone = findZone(this);
    if (zone) {
      zone.trigger(events.zone.refresh, {
        url: this.attribute("href")
      });
    }
    return false;
  });
  dom.onDocument("submit", "form[data-update-zone]", function() {
    var formParameters, zone;
    zone = findZone(this);
    if (zone) {
      formParameters = forms.gatherParameters(this);
      zone.trigger(events.zone.refresh, {
        url: this.attribute("action"),
        parameters: formParameters
      });
    }
    return false;
  });
  dom.onDocument(events.zone.update, function(event) {
    var content;
    this.trigger(events.zone.willUpdate);
    content = event.memo.content;
    if (content !== void 0) {
      this.update(content);
    }
    return this.trigger(events.zone.didUpdate);
  });
  dom.onDocument(events.zone.refresh, function(event) {
    var attr, parameters,
      _this = this;
    attr = this.attribute("data-zone-parameters");
    parameters = attr && JSON.parse(attr);
    return ajax(event.memo.url, {
      parameters: _.extend({
        "t:zoneid": this.attribute("id")
      }, parameters, event.memo.parameters),
      onsuccess: function(reply) {
        var _ref;
        return _this.trigger(events.zone.update, {
        	//The responseJSON property does not exist in jQuery
          content: (_ref = reply.responseJSON) != null ? _ref.content : void 0
        });
      }
    });
  });
  deferredZoneUpdate = function(id, url) {
    return _.defer(function() {
      var zone;
      zone = dom(id);
      if (zone === null) {
        console.error("Could not locate element '" + id + "' to update.");
        return;
      }
      return zone.trigger(events.zone.refresh, {
        url: url
      });
    });
  };
  return {
    deferredZoneUpdate: deferredZoneUpdate,
    findZone: findZone
  };
});
