var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loading: false,
  sales: []
};

var Sales = assign({}, EventEmitter.prototype, {

  isLoading: function() {
    return state.loading;
  },

  getSales: function() {
    return state.sales;
  },

  getRemaining: function() {
    var selected = state.selected;
    return state.allVendors.filter(function(vendor) {
      return !selected[vendor.id];
    });
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

function salesLoading() {
  state.loading = true;
  state.sales = [];
  Sales.emitChange();
}

function salesLoaded(sales) {
  state.loading = false;
  state.sales = sales;
  Sales.emitChange();
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.SALES_LOADING:
      salesLoading();
      break;

    case Constants.SALES_LOADED:
      salesLoaded(action.sales);
      break;
  }

});

module.exports = Sales;
