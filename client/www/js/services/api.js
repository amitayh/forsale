angular.module('forsale.services')
  .factory('API', function ($q, $http) {
    var BASE_URL = 'http://localhost:9000';
    var STATUS_OK = 'OK';

    function createPromise(options) {
      return $q(function (resolve, reject) {
        $http(options)
          .success(function (data) {
            data = data || {};
            if (data.response_code == STATUS_OK) {
              resolve(data.data);
            } else {
              reject(data.error || 'Unknown error');
            }
          })
          .error(reject);
      });
    }

    function objToFormData(obj) {
      var str = [], encode = encodeURIComponent;
      for (var p in obj) {
        if (obj.hasOwnProperty(p)) {
          str.push(encode(p) + '=' + encode(obj[p]));
        }
      }
      return str.join('&');
    }

    function getUrl(path) {
      return BASE_URL + path;
    }

    function doGet(path, params) {
      return createPromise({
        method: 'get',
        url: getUrl(path),
        params: params
      });
    }

    function doPost(path, data) {
      return createPromise({
        method: 'post',
        url: getUrl(path),
        data: data,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        transformRequest: objToFormData
      });
    }

    return {
      get: doGet,
      post: doPost
    };
  });