angular.module('forsale.controllers')

  .controller('AuthLoginCtrl', function ($scope, $state, Auth) {
    function resetCredentials() {
      $scope.credentials = {email: '', password: ''};
    }

    resetCredentials();

    $scope.login = function () {
      Auth.login($scope.credentials)
        .then(function () {
          resetCredentials();
          $state.go('tab.sales');
        }, function (error) {
          alert(error);
        });
    };

    $scope.register = function () {
      $state.go('tab.auth-register');
    };
  })

  .controller('AuthRegisterCtrl', function ($scope, $state, Auth) {
    function resetForm() {
      $scope.form = {
        email: '',
        name: '',
        password: '',
        birth: new Date('2000-01-01'),
        gender: 'MALE'
      };
    }

    resetForm();

    $scope.register = function () {
      Auth.register($scope.form)
        .then(function () {
          resetForm();
          $state.go('tab.sales');
        }, function (error) {
          alert(error);
        });
    };
  });
