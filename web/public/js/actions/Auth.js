var MessagesActions = require('./Messages');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var API = require('../API');

function loginSuccess() {
  Dispatcher.dispatch({
    actionType: Constants.LOGIN_SUCCESS
  });
}

var Auth = {

  login: function(email, password) {
    var data = {email: email, password: password};
    API.doPost('/auth/login', data)
      .then(loginSuccess, function (error) {
        Dispatcher.dispatch({
          actionType: Constants.LOGIN_FAILED,
          error: error.message
        });
      });
  },

  logout: function() {
    API.doGet('/auth/logout')
      .then(function() {
        Dispatcher.dispatch({
          actionType: Constants.LOGOUT
        });
      });
  },

  register: function(user) {
    API.doPost('/auth/register', user)
      .then(loginSuccess, function(error) {
        MessagesActions.error(error);
      });
  },

  sessionExpired: function() {
    Dispatcher.dispatch({
      actionType: Constants.SESSION_EXPIRED
    });
  }

};

module.exports = Auth;
