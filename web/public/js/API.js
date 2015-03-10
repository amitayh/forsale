var Q = require('q');
var Qajax = require('qajax');

var Utils = require('./Utils');

var STATUS_OK = 200;
var STATUS_UNAUTHORIZED = 401;
var BASE_URL = '/forsale';

function checkStatus(result) {
  result = result || {};
  if (result.status == STATUS_OK) {
    return result.data;
  } else {
    if (result.status == STATUS_UNAUTHORIZED) {
      require('./Actions').sessionExpired();
    }
    throw new Error(result.error || 'Unknown error');
  }
}

function request(options) {
  return Qajax(options)
    .then(Qajax.toJSON)
    .then(checkStatus);
}

function doGet(path, params) {
  return request({
    method: 'GET',
    url: BASE_URL + path,
    params: params
  });
}

function doPost(path, data) {
  return request({
    method: 'POST',
    url: BASE_URL + path,
    data: Utils.toFormData(data),
    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
  });
}

function convertSale(sale) {
  sale.start = new Date(sale.start);
  sale.end = new Date(sale.end);
  return sale;
}

function convertSales(sales) {
  return sales.map(convertSale);
}

var API = {

  login: function(email, password) {
    return doPost('/auth/login', {email: email, password: password});
  },

  logout: function() {
    return doGet('/auth/logout');
  },

  register: function(user) {
    return doPost('/auth/register', user);
  },

  getProfile: function() {
    return doGet('/users/profile');
  },

  getFavorites: function() {
    return doGet('/users/favorites');
  },

  getVendors: function() {
    return doGet('/vendors/list');
  },

  updateAccount: function(profile, favorites) {
    var favoritesIds = favorites.map(function(vendor) {
      return vendor.id;
    });
    var updateProfile = doPost('/users/profile', profile);
    var updateFavorites = doPost('/users/favorites', {vendor_id: favoritesIds});
    return Q.all([updateProfile, updateFavorites]);
  },

  getRecentSales: function() {
    return doGet('/sales/recent').then(convertSales);
  },

  getPopularSales: function() {
    return doGet('/sales/popular').then(convertSales);
  },

  getFavoritesSales: function() {
    return doGet('/sales/favorites').then(convertSales);
  },

  getSale: function(saleId) {
    return doGet('/sales/show', {sale_id: saleId}).then(convertSale);
  },

  search: function(query) {
    return doGet('/search', {query: query}).then(convertSales);
  }

};

module.exports = API;