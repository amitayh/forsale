var Q = require('q');
var Router = require('react-router');
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

  register: function(user) {
    API.register(user)
      .then(function() {
        Dispatcher.dispatch({
          actionType: Constants.LOGIN_SUCCESS
        });
      }, function(error) {
        alert(error);
      });
  },

  sessionExpired: function() {
    Dispatcher.dispatch({
      actionType: Constants.SESSION_EXPIRED
    });
  },

  loadSales: function(promise) {
    Dispatcher.dispatch({actionType: Constants.SALES_LOADING});

    promise.then(function(sales) {
      Dispatcher.dispatch({
        actionType: Constants.SALES_LOADED,
        sales: sales
      });
    });
  },

  loadFavorites: function() {
    Dispatcher.dispatch({actionType: Constants.FAVORITES_LOADING});

    var favorites = API.getFavorites();
    var vendors = API.getVendors();

    Q.all([favorites, vendors])
      .then(function(result) {
        Dispatcher.dispatch({
          actionType: Constants.FAVORITES_LOADED,
          favorites: result[0],
          allVendors: result[1]
        });
      });
  },

  removeFavorite: function(vendorId) {
    Dispatcher.dispatch({
      actionType: Constants.FAVORITES_REMOVE,
      vendorId: vendorId
    });
  },

  addFavorite: function(vendorId) {
    Dispatcher.dispatch({
      actionType: Constants.FAVORITES_ADD,
      vendorId: vendorId
    });
  }

};

module.exports = Actions;
