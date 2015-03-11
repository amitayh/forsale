var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var Utils = require('../Utils');
var API = require('../API');

var searchDelay = 350;

function convertSale(sale) {
  sale.start = new Date(sale.start);
  sale.end = new Date(sale.end);
  return sale;
}

function convertSales(sales) {
  return sales.map(convertSale);
}

function loadPromise(promise) {
  Dispatcher.dispatch({actionType: Constants.SALES_LOADING});
  promise.then(Sales.load);
}

var Sales = {

  load: function(sales) {
    Dispatcher.dispatch({
      actionType: Constants.SALES_LOADED,
      sales: sales
    });
  },

  loadFavorites: function() {
    var promise = API.doGet('/sales/favorites').then(convertSales);
    loadPromise(promise);
  },

  loadPopular: function() {
    var promise = API.doGet('/sales/popular').then(convertSales);
    loadPromise(promise);
  },

  loadRecent: function() {
    var promise = API.doGet('/sales/recent').then(convertSales);
    loadPromise(promise);
  },

  show: function(id) {
    var promise = API.doGet('/sales/show', {sale_id: id}).then(convertSale);
    loadPromise(promise);
  },

  search: Utils.throttle(function(query) {
    if (query === '') {
      Sales.load([]);
    } else {
      var promise = API.doGet('/search', {query: query}).then(convertSales);
      loadPromise(promise);
    }
  }, searchDelay)

};

module.exports = Sales;
