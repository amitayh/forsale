var Dispatcher = require('./Dispatcher');
var Constants = require('./Constants');
var API = require('./API');

var Actions = {

  login: function(email, password) {
    API.login(email, password)
      .then(function() {
        Dispatcher.dispatch({
          actionType: Constants.LOGIN_SUCCESS
        });
      },
      function(error) {
        Dispatcher.dispatch({
          actionType: Constants.LOGIN_FAILED,
          error: error.message
        });
      });
  },

  logout: function() {
    Dispatcher.dispatch({
      actionType: Constants.LOGOUT
    });
  }

};

module.exports = Actions;
