angular.module('forsale.services')
  .factory('Sales', function (API) {

    function recent() {
      return API.get('/sales/recent');
    }

    function popular() {
      return API.get('/sales/popular');
    }

    function favorites() {
      return API.get('/sales/favorites');
    }

    function detail(saleId) {
      return API.get('/sales/show', {sale_id: saleId});
    }

    return {
      recent: recent,
      popular: popular,
      favorites: favorites,
      detail: detail
    };
  });