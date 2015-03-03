// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('forsale', ['ionic', 'forsale.controllers', 'forsale.services'])

  .run(function ($ionicPlatform) {
    $ionicPlatform.ready(function () {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      if (window.cordova && window.cordova.plugins.Keyboard) {
        cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      }
      if (window.StatusBar) {
        // org.apache.cordova.statusbar required
        StatusBar.styleDefault();
      }
    });
  })

  .config(function ($stateProvider, $urlRouterProvider) {

    // Ionic uses AngularUI Router which uses the concept of states
    // Learn more here: https://github.com/angular-ui/ui-router
    // Set up the various states which the app can be in.
    // Each state's controller can be found in controllers.js
    $stateProvider

      // Setup an abstract state for the tabs directive

      .state('tab', {
        url: '/tab',
        abstract: true,
        templateUrl: 'templates/tabs.html'
      })

      // Sales

      .state('tab.sales', {
        url: '/sales',
        views: {
          'tab-sales': {
            templateUrl: 'templates/tab-sales.html'
          }
        }
      })
      .state('tab.sales-recent', {
        url: '/sales/recent',
        views: {
          'tab-sales': {
            templateUrl: 'templates/sales/list.html',
            controller: 'SalesRecentCtrl'
          }
        }
      })
      .state('tab.sales-popular', {
        url: '/sales/popular',
        views: {
          'tab-sales': {
            templateUrl: 'templates/sales/list.html',
            controller: 'SalesPopularCtrl'
          }
        }
      })
      .state('tab.sales-favorites', {
        url: '/sales/favorites',
        views: {
          'tab-sales': {
            templateUrl: 'templates/sales/list.html',
            controller: 'SalesFavoritesCtrl'
          }
        }
      })
      .state('tab.sales-show', {
        url: '/sales/show/:saleId',
        views: {
          'tab-sales': {
            templateUrl: 'templates/sales/show.html',
            controller: 'SalesShowCtrl'
          }
        }
      })

      // Auth

      .state('tab.auth-login', {
        url: '/auth/login',
        views: {
          'tab-auth': {
            templateUrl: 'templates/auth/login.html',
            controller: 'AuthLoginCtrl'
          }
        }
      })
      .state('tab.auth-register', {
        url: '/auth/register',
        views: {
          'tab-auth': {
            templateUrl: 'templates/auth/register.html',
            controller: 'AuthRegisterCtrl'
          }
        }
      })

      // Users

      .state('tab.users-profile', {
        url: '/users/profile',
        views: {
          'tab-auth': {
            templateUrl: 'templates/users/profile.html',
            controller: 'UsersProfileCtrl'
          }
        }
      })

      ////////////////////////////////////////////////////////////////////////////////////////////
      // TODO: REMOVE THESE
      ////////////////////////////////////////////////////////////////////////////////////////////

      .state('tab.chats', {
        url: '/chats',
        views: {
          'tab-chats': {
            templateUrl: 'templates/tab-chats.html',
            controller: 'ChatsCtrl'
          }
        }
      })
      .state('tab.chat-detail', {
        url: '/chats/:chatId',
        views: {
          'tab-chats': {
            templateUrl: 'templates/chat-detail.html',
            controller: 'ChatDetailCtrl'
          }
        }
      })

      .state('tab.friends', {
        url: '/friends',
        views: {
          'tab-friends': {
            templateUrl: 'templates/tab-friends.html',
            controller: 'FriendsCtrl'
          }
        }
      })
      .state('tab.friend-detail', {
        url: '/friend/:friendId',
        views: {
          'tab-friends': {
            templateUrl: 'templates/friend-detail.html',
            controller: 'FriendDetailCtrl'
          }
        }
      });

    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/tab/sales');

  });
