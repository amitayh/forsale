angular.module('forsale.controllers')

  .controller('SalesRecentCtrl', function ($scope, Sales) {
    $scope.title = 'Recent';
    $scope.sales = [];
    Sales.recent().then(function (sales) {
      $scope.sales = sales;
    }, function () {
      $scope.sales = [];
    });
  })

  .controller('SalesPopularCtrl', function ($scope, Sales) {
    $scope.title = 'Popular';
    $scope.sales = [];
    Sales.popular().then(function (sales) {
      $scope.sales = sales;
    }, function () {
      $scope.sales = [];
    });
  })

  .controller('SalesFavoritesCtrl', function ($scope, $state, Auth, Sales) {
    $scope.title = 'Favorites';
    $scope.sales = [];
    Sales.favorites().then(function (sales) {
      $scope.sales = sales;
    }, function (error) {
      $scope.sales = [];
      if (error == Auth.ERROR_SESSION_EXPIRED) {
        $state.go('tab.auth-login');
      }
    });
  })

  .controller('SalesShowCtrl', function ($scope, $stateParams, Sales) {
    $scope.sale = null;
    Sales.detail($stateParams.saleId).then(function (sale) {
      $scope.sale = sale;
    }, function () {
      $scope.sale = null;
    });
  });