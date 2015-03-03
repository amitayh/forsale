angular.module('forsale.controllers')

  .controller('UsersProfileCtrl', function ($scope, $state, Users, Auth) {
    $scope.profile = {};

    $scope.updateProfile = function () {
      console.log('update', $scope.profile);
    };

    $scope.logout = function () {
      Auth.logout().then(function () {
        $state.go('tab.sales');
      });
    };

    Users.getProfile().then(function(profile) {
      $scope.profile = profile;
    }, function (error) {
      $scope.profile = {};
      //if (error == Auth.ERROR_SESSION_EXPIRED) {
      //  $state.go('tab.auth-login');
      //}
    });
  });
