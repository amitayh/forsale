var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  status: Constants.STATUS_READY,
  sales: []
};

var Sales = assign({}, EventEmitter.prototype, {

  getStatus: function() {
    return state.status;
  },

  getSales: function() {
    return state.sales;
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

function loadRecentSales() {
  state.status = Constants.STATUS_LOADING;
  state.sales = [];
  Sales.emitChange();

  setTimeout(function() {
    state.status = Constants.STATUS_READY;
    state.sales = [
      {id: 1, title: 'Sale #1'},
      {id: 2, title: 'Sale #2'},
      {id: 3, title: 'Sale #3'}
    ];
    Sales.emitChange();
  }, 1500);
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.LOAD_RECENT_SALES:
      loadRecentSales();
      break;
  }

});

module.exports = Sales;
