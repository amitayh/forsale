angular.module('forsale.controllers')

  .controller('UsersProfileCtrl', function ($scope, $state, Users, Auth) {
    $scope.profile = {};

    Users.getProfile().then(function(profile) {
      $scope.profile = profile;
    }, function (error) {
      $scope.profile = {};
      //if (error == Auth.ERROR_SESSION_EXPIRED) {
      //  $state.go('tab.auth-login');
      //}
    });
  });
