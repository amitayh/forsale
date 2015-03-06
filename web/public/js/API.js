var Qajax = require('qajax');

var STATUS_OK = 'OK';
var SESSION_EXPIRED = 'Session expired';

function toFormData(obj) {
  var encode = encodeURIComponent;
  var params = [];
  for (var key in obj) {
    if (obj.hasOwnProperty(key) && obj[key] !== undefined) {
      params.push(encode(key) + '=' + encode(obj[key]));
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
      require('./Actions').logout();
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

  getProfile: function() {
    return doGet('/users/profile');
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