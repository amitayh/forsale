var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  message: null
};

var Messages = assign({}, BaseStore, {

  getMessage: function() {
    return state.message;
  }

});

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.MESSAGE:
      state.message = action.message;
      Messages.emitChange();
      break;
  }

});

module.exports = Messages;
