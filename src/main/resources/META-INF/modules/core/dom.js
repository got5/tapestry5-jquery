
define(["_", "core/utils", "prototype"], function(_, utils) {
  var $, ElementWrapper, EventWrapper, ajaxRequest, animate, bodyWrapper, convertContent, exports, fireNativeEvent, onevent, parseSelectorToElements, wrapElement;
  $ = window.$;
  fireNativeEvent = function(element, eventName) {
    var event;
    if (document.createEventObject) {
      event = document.createEventObject();
      return element.fireEvent("on" + eventName, event);
    }
    event = document.createEvent("HTMLEvents");
    event.initEvent(eventName, true, true);
    element.dispatchEvent(event);
    return !event.defaultPrevented;
  };
  convertContent = function(content) {
    var _ref;
    if (_.isString(content)) {
      return content;
    }
    if (_.isElement(content)) {
      return content;
    }
    if (((_ref = content.constructor) != null ? _ref.name : void 0) === "ElementWrapper") {
      return content.element;
    }
    throw new Error("Provided value <" + content + "> is not valid as DOM element content.");
  };
  animate = function(element, styleName, initialValue, finalValue, duration, callbacks) {
    var animator, first, initialTime, range, styles, timeoutID;
    styles = {};
    range = finalValue - initialValue;
    initialTime = Date.now();
    first = true;
    animator = function() {
      var elapsed, newValue;
      elapsed = Date.now() - initialTime;
      if (elapsed >= duration) {
        styles[styleName] = finalValue;
        element.setStyle(styles);
        window.clearInterval(timeoutID);
        callbacks.oncomplete && callbacks.oncomplete();
      }
      newValue = initial + range * (elapsed / duration);
      element.setStyle(styles);
      if (first) {
        callbacks.onstart && callbacks.onstart();
        return first = false;
      }
    };
    timeoutID = window.setInterval(animator);
    styles[styleName] = initialValue;
    return element.setStyle(styles);
  };
  EventWrapper = (function() {

    function EventWrapper(event) {
      var name, _i, _len, _ref;
      this.nativeEvent = event;
      _ref = ["memo", "type", "char", "key"];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        name = _ref[_i];
        this[name] = event[name];
      }
    }

    EventWrapper.prototype.stop = function() {
      return this.nativeEvent.stop();
    };

    return EventWrapper;

  })();
  onevent = function(elements, eventNames, match, handler) {
    var element, eventName, wrapped, _i, _j, _len, _len1;
    if (handler == null) {
      throw new Error("No event handler was provided.");
    }
    wrapped = function(prototypeEvent) {
      var elementWrapper, eventWrapper, result;
      elementWrapper = new ElementWrapper(prototypeEvent.findElement());
      eventWrapper = new EventWrapper(prototypeEvent);
      result = handler.call(elementWrapper, eventWrapper, eventWrapper.memo);
      if (result === false) {
        prototypeEvent.stop();
      }
    };
    for (_i = 0, _len = elements.length; _i < _len; _i++) {
      element = elements[_i];
      for (_j = 0, _len1 = eventNames.length; _j < _len1; _j++) {
        eventName = eventNames[_j];
        Event.on(element, eventName, match, wrapped);
        //jQuery("#"+element).on(eventName, wrapped);
      }
    }
  };
  ElementWrapper = (function() {

    function ElementWrapper(element) {
      this.element = element;
    }

    ElementWrapper.prototype.hide = function() {
      this.element.hide();
      return this;
    };

    ElementWrapper.prototype.show = function() {
      this.element.show();
      return this;
    };

    ElementWrapper.prototype.remove = function() {
      this.element.remove();
      return this;
    };

    ElementWrapper.prototype.attribute = function(name, value) {
      var current;
      if (_.isObject(name)) {
        for (name in name) {
          value = name[name];
          this.element.writeAttribute(name, value);
        }
        return this;
      }
      current = this.element.readAttribute(name);
      if (arguments.length > 1) {
        this.element.writeAttribute(name, value);
      }
      return current;
    };

    ElementWrapper.prototype.focus = function() {
      this.element.focus();
      return this;
    };

    ElementWrapper.prototype.hasClass = function(name) {
      return this.element.hasClassName(name);
    };

    ElementWrapper.prototype.removeClass = function(name) {
      this.element.removeClassName(name);
      return this;
    };

    ElementWrapper.prototype.addClass = function(name) {
      this.element.addClassName(name);
      return this;
    };

    ElementWrapper.prototype.update = function(content) {
      this.element.update(content && convertContent(content));
      return this;
    };

    ElementWrapper.prototype.append = function(content) {
      this.element.insert({
        bottom: convertContent(content)
      });
      return this;
    };

    ElementWrapper.prototype.prepend = function(content) {
      this.element.insert({
        top: convertContent(content)
      });
      return this;
    };

    ElementWrapper.prototype.insertBefore = function(content) {
      this.element.insert({
        before: convertContent(content)
      });
      return this;
    };

    ElementWrapper.prototype.insertAfter = function(content) {
      this.element.insert({
        after: convertContent(content)
      });
      return this;
    };

    ElementWrapper.prototype.fadeIn = function(duration, callback) {
      var _this = this;
      animate(this.element, "opacity", 0, 1, duration * 1000, {
        onstart: function() {
          return _this.element.show();
        },
        oncomplete: callback
      });
      return this;
    };

    ElementWrapper.prototype.fadeOut = function(duration, callback) {
      animate(this.element, "opacity", 1, 0, duration * 1000, {
        oncomplete: callback
      });
      return this;
    };

    ElementWrapper.prototype.findFirst = function(selector) {
      var match;
      match = this.element.down(selector);
      if (match) {
        return new ElementWrapper(match);
      } else {
        return null;
      }
    };

    ElementWrapper.prototype.find = function(selector) {
      var matches;
      matches = this.element.select(selector);
      return _.map(matches, function(e) {
        return new ElementWrapper(e);
      });
    };

    ElementWrapper.prototype.findContainer = function(selector) {
      var container;
      container = this.element.up(selector);
      if (container) {
        return new ElementWrapper(container);
      } else {
        return null;
      }
    };

    ElementWrapper.prototype.container = function() {
      var parentNode;
      parentNode = this.element.parentNode;
      if (!parentNode) {
        return null;
      }
      return new ElementWrapper(parentNode);
    };

    ElementWrapper.prototype.visible = function() {
      return this.element.visible();
    };

    ElementWrapper.prototype.deepVisible = function() {
      var cursor;
      cursor = this;
      while (cursor) {
        if (!cursor.visible()) {
          return false;
        }
        cursor = cursor.container();
        if (cursor && cursor.element === document.body) {
          return true;
        }
      }
      return false;
    };

    ElementWrapper.prototype.trigger = function(eventName, memo) {
      var event;
      if (eventName == null) {
        throw new Error("Attempt to trigger event with null event name");
      }
      if (!((_.isNull(memo)) || (_.isObject(memo)) || (_.isUndefined(memo)))) {
        throw new Error("Event memo may be null or an object, but not a simple type.");
      }
      if ((eventName.indexOf(':')) > 0) {
        event = this.element.fire(eventName, memo);
        return !event.defaultPrevented;
      }
      if (memo) {
        throw new Error("Memo must be null when triggering a native event");
      }
      return fireNativeEvent(this.element, eventName);
    };

    ElementWrapper.prototype.value = function(newValue) {
      var current;
      current = this.element.getValue();
      if (arguments.length > 0) {
        this.element.setValue(newValue);
      }
      return current;
    };

    ElementWrapper.prototype.meta = function(name, value) {
      var current;
      current = this.element.retrieve(name);
      if (arguments.length > 1) {
        this.element.store(name, value);
      }
      return current;
    };

    ElementWrapper.prototype.on = function(events, match, handler) {
      exports.on(this.element, events, match, handler);
      return this;
    };

    return ElementWrapper;

  })();
  parseSelectorToElements = function(selector) {
    if (_.isString(selector)) {
      return $$(selector);
    }
    if (_.isArray(selector)) {
      return selector;
    }
    return [selector];
  };
  bodyWrapper = null;
  ajaxRequest = function(url, options) {
    var finalOptions;
    if (options == null) {
      options = {};
    }
    finalOptions = {
      method: options.method || "post",
      contentType: options.contentType || "application/x-www-form-urlencoded",
      parameters: options.parameters || {},
      onException: function(ajaxRequest, exception) {
        if (options.onexception) {
          return options.onexception(exception);
        } else {
          throw exception;
        }
      },
      onFailure: function(response) {
        var message, text;
        if (options.onfailure) {
          return options.onfailure(response);
        } else {
          message = "Request to " + url + " failed with status " + (response.getStatus());
          text = response.getStatusText();
          if (!_.isEmpty(text)) {
            message += " -- " + text;
          }
          message += ".";
          if (options.onfailure) {
            return options.onfailure(response, message);
          } else {
            throw new Error(message);
          }
        }
      },
      onSuccess: function(response) {
        if ((!response.getStatus()) || (!response.request.success())) {
          finalOptions.onFailure(response);
          return;
        }
        return options.onsuccess && options.onsuccess(response);
      }
    };
    return new Ajax.Request(url, finalOptions);
  };
  exports = wrapElement = function(element) {
    if (_.isString(element)) {
      element = $(element);
      if (!element) {
        return null;
      }
    } else {
      if (!element) {
        throw new Error("Attempt to wrap a null DOM element");
      }
    }
    return new ElementWrapper(element);
  };
  return _.extend(exports, {
    wrap: wrapElement,
    escapeHTML: function(str) {
      return str.escapeHTML();
    },
    ajaxRequest: ajaxRequest,
    on: function(selector, events, match, handler) {
      var elements;
      if (handler == null) {
        handler = match;
        match = null;
      }
      elements = parseSelectorToElements(selector);
      onevent(elements, utils.split(events), match, handler);
    },
    onDocument: function(events, match, handler) {
      return exports.on(document, events, match, handler);
    },
    body: function() {
      return bodyWrapper != null ? bodyWrapper : bodyWrapper = wrapElement(document.body);
    }
  });
});
