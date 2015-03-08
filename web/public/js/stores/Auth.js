var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loggedIn: false,
  error: null
};

var Auth = assign({}, EventEmitter.prototype, {

  isLoggedIn: function() {
    return state.loggedIn;
  },

  getError: function() {
    return state.error;
  },

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

function login() {
  state.loggedIn = true;
  state.error = null;
  Auth.emitChange();
}

function logout(error) {
  state.loggedIn = false;
  state.error = error;
  Auth.emitChange();
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.LOGIN_SUCCESS:
      login();
      break;

    case Constants.LOGIN_FAILED:
      logout(action.error);
      break;

    case Constants.SESSION_EXPIRED:
      logout('Session expired');
      break;
  }

});

module.exports = Auth;
