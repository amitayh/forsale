angular.module('forsale.services')
  .factory('Utils', function ($filter) {

    function formatDate(date) {
      return $filter('date')(date, 'yyyy-MM-dd');
    }

    return {
      formatDate: formatDate
    };
  });