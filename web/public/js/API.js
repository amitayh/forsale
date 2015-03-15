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
      require('./actions/Auth').sessionExpired();
    }
    throw new Error(result.error || 'Unknown error');
  }
}

function request(options) {
  return Qajax(options)
    .then(Qajax.toJSON)
    .then(checkStatus);
}

var API = {

  doGet: function(path, params) {
    return request({
      method: 'GET',
      url: BASE_URL + path,
      params: params
    });
  },

  doPost: function(path, data) {
    return request({
      method: 'POST',
      url: BASE_URL + path,
      data: Utils.toFormData(data),
      headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    });
  }

};

module.exports = API;