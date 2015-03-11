var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loading: false,
  sales: []
};

var Sales = assign({}, BaseStore, {

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
