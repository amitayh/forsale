var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loggedIn: false,
  error: null
};

var Auth = assign({}, BaseStore, {

  isLoggedIn: function() {
    return state.loggedIn;
  },

  getError: function() {
    return state.error;
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
