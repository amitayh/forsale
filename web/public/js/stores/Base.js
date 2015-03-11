var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;

var Constants = require('../Constants');

var Base = assign({}, EventEmitter.prototype, {

  addChangeListener: function(callback) {
    this.on(Constants.CHANGE_EVENT, callback);
  },

  removeChangeListener: function(callback) {
    this.removeListener(Constants.CHANGE_EVENT, callback);
  },

  emitChange: function() {
    this.emit(Constants.CHANGE_EVENT);
  }

});

module.exports = Base;
