var Q = require('q');
var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var DB = require('../DB');

var state = {
  loading: false
};

var Sales = assign({}, BaseStore, {

  isLoading: function() {
    return state.loading;
  },

  getSales: function() {
    var deferred = Q.defer();
    DB.transaction(function(tx) {
      var sql = 'SELECT * FROM sales';
      tx.executeSql(sql, [], function(tx, resultSet) {
        deferred.resolve(resultSet.rows);
      }, function(error) {
        deferred.reject(error);
      });
    });
    return deferred.promise;
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
  DB.transaction(function(tx) {
    var sql = 'INSERT INTO sales (id, title, start, end, vendor) values (?, ?, ?, ?, ?)';
    sales.forEach(function(sale) {
      tx.executeSql(sql, [sale.id, sale.title, sale.start, sale.end, sale.vendor]);
    });
    state.loading = false;
    Sales.emitChange();
  });
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
