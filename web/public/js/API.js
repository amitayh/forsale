var Q = require('q');
var Qajax = require('qajax');

var STATUS_OK = 'OK';
var SESSION_EXPIRED = 'Session expired';

function toFormData(obj) {
  var encode = encodeURIComponent;
  var params = [];
  for (var key in obj) {
    if (obj.hasOwnProperty(key) && obj[key] !== undefined) {
      var encodedKey = encode(key);
      var value = obj[key];
      if (value instanceof Array) {
        for (var i = 0, l = value.length; i < l; i++) {
          params.push(encodedKey + '=' + encode(value[i]));
        }
      } else {
        params.push(encodedKey + '=' + encode(value));
      }
    }
  }
  return params.join('&');
}

function checkStatus(result) {
  result = result || {};
  if (result.response_code == STATUS_OK) {
    return result.data;
  } else {
    var errorMessage = result.error || 'Unknown error';
    if (errorMessage == SESSION_EXPIRED) {
      require('./Actions').sessionExpired();
    }
    throw new Error(errorMessage);
  }
}

function request(options) {
  return Qajax(options)
    .then(Qajax.filterSuccess)
    .then(Qajax.toJSON)
    .then(checkStatus);
}

function doGet(path, params) {
  return request({
    method: 'GET',
    url: path,
    params: params
  });
}

function doPost(path, data) {
  return request({
    method: 'POST',
    url: path,
    data: toFormData(data),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
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
  }

};

module.exports = API;