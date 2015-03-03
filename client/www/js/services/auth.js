angular.module('forsale.services')
  .factory('Auth', function ($q, $filter, API) {
    var errorSessionExpired = 'Session expired';
    var loggedIn = false;

    function formatDate(date) {
      return $filter('date')(date, 'yyyy-MM-dd');
    }

    function register(user) {
      // email, password, name, gender, birth
      var userCopy = angular.copy(user);
      userCopy.birth = formatDate(userCopy.birth);
      return API.post('/auth/register', userCopy)
        .then(function (data) {
          loggedIn = true;
          return data;
        });
    }

    function login(credentials) {
      // email, password
      return API.post('/auth/login', credentials)
        .then(function (data) {
          loggedIn = true;
          return data;
        }, function (error) {
          loggedIn = false;
          return $q.reject(error);
        });
    }

    function logout() {
      return API.get('/auth/logout').then(function (data) {
        loggedIn = false;
        return data;
      });
    }

    function isLoggedIn() {
      return loggedIn;
    }

    return {
      ERROR_SESSION_EXPIRED: errorSessionExpired,
      register: register,
      login: login,
      logout: logout,
      isLoggedIn: isLoggedIn
    };
  });