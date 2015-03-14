var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var Utils = require('../Utils');
var API = require('../API');

var searchDelay = 350;

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

  orderBy: function(field) {
    Dispatcher.dispatch({
      actionType: Constants.SALES_ORDER,
      field: field
    });
  },

  loadFavorites: function() {
    var promise = API.doGet('/sales/favorites');
    loadPromise(promise);
  },

  loadPopular: function() {
    var promise = API.doGet('/sales/popular');
    loadPromise(promise);
  },

  loadRecent: function() {
    var promise = API.doGet('/sales/recent');
    loadPromise(promise);
  },

  show: function(id) {
    var promise = API.doGet('/sales/show', {sale_id: id});
    loadPromise(promise);
  },

  search: Utils.throttle(function(query) {
    if (query === '') {
      Sales.load([]);
    } else {
      var promise = API.doGet('/search', {query: query});
      loadPromise(promise);
    }
  }, searchDelay)

};

module.exports = Sales;
