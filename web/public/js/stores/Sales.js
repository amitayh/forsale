var Q = require('q');
var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var DB = require('../DB');

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

function clearSales() {
  return DB.executeSql('DELETE FROM sales');
}

function insertSales(sales) {
  sales = Array.isArray(sales) ? sales : [sales];
  var sql = 'INSERT INTO sales (id, title, start, end, vendor) values (?, ?, ?, ?, ?)';
  var paramsList = sales.map(function(sale) {
    return [sale.id, sale.title, sale.start, sale.end, sale.vendor];
  });
  return DB.executeMulti(sql, paramsList);
}

function selectSales(orderBy) {
  orderBy = orderBy || Constants.DEFAULT_ORDERING;
  var sql = 'SELECT * FROM sales ORDER BY ' + orderBy;
  return DB.executeSql(sql)
    .then(function(resultSet) {
      state.loading = false;
      state.sales = DB.toArray(resultSet.rows);
      Sales.emitChange();
    });
}

function salesLoaded(sales) {
  return clearSales()
    .then(function() {
      return insertSales(sales);
    }).then(function() {
      return selectSales();
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

    case Constants.SALES_ORDER:
      selectSales(action.field);
      break;
  }

});

module.exports = Sales;
