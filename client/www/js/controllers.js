angular.module('starter.controllers', [])

  .controller('RecentCtrl', function ($scope, Sales) {
    $scope.sales = [];
    Sales.recent().then(function (sales) {
      $scope.sales = sales;
    }, function () {
      $scope.sales = [];
    });
  })
  .controller('SaleDetailCtrl', function ($scope, $stateParams, Sales) {
    $scope.sale = null;
    Sales.detail($stateParams.saleId).then(function (sale) {
      $scope.sale = sale;
    }, function () {
      $scope.sale = null;
    });
  })

  ////////////////////////////////////////////////////////////////////////////////////////////
  // TODO: REMOVE THESE
  ////////////////////////////////////////////////////////////////////////////////////////////

  .controller('DashCtrl', function ($scope) {
  })

  .controller('ChatsCtrl', function ($scope, Chats) {
    $scope.chats = Chats.all();
    $scope.remove = function (chat) {
      Chats.remove(chat);
    }
  })

  .controller('ChatDetailCtrl', function ($scope, $stateParams, Chats) {
    $scope.chat = Chats.get($stateParams.chatId);
  })

  .controller('FriendsCtrl', function ($scope, Friends) {
    $scope.friends = Friends.all();
  })

  .controller('FriendDetailCtrl', function ($scope, $stateParams, Friends) {
    $scope.friend = Friends.get($stateParams.friendId);
  })

  .controller('AccountCtrl', function ($scope, Auth) {
    function resetCredentials() {
      $scope.credentials = {email: '', password: ''};
    }

    resetCredentials();

    $scope.login = function() {
      Auth.login($scope.credentials)
        .then(function () {
          console.log('success');
          resetCredentials();
        }, function (error) {
          alert(error);
        });
    }
  });
