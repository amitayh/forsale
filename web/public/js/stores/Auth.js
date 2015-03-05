var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loggedIn: false,
  error: null,
  nextPath: null
};

var Auth = assign({}, EventEmitter.prototype, {

  isLoggedIn: function() {
    return state.loggedIn;
  },

  getError: function() {
    return state.error;
  },

  getNextPath: function() {
    return state.nextPath;
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

function login(nextPath) {
  state.loggedIn = true;
  state.error = null;
  state.nextPath = nextPath;
  Auth.emitChange();
}

function logout(error) {
  state.loggedIn = false;
  state.error = error;
  state.nextPath = null;
  Auth.emitChange();
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.LOGIN_SUCCESS:
      login(action.nextPath);
      break;

    case Constants.LOGIN_FAILED:
      logout(action.error);
      break;

    case Constants.LOGOUT:
      logout(null);
      break;
  }

});

module.exports = Auth;
