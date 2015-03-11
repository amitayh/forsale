var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var NOTIFY = 0;
var ERROR = 1;

var Messages = {

  send: function(text, type) {
    Dispatcher.dispatch({
      actionType: Constants.MESSAGE,
      message: {text: text, type: type}
    });
  },

  notify: function(text) {
    Messages.send(text, NOTIFY);
  },

  error: function(text) {
    Messages.send(text, ERROR);
  }

};

module.exports = Messages;
