angular.module('forsale.services')
  .factory('Users', function (API) {

    function getFavorites() {
      return API.get('/users/favorites');
    }

    function setFavorites(favorites) {
      return API.post('/users/favorites', favorites);
    }

    function getProfile() {
      return API.get('/users/profile');
    }

    function setProfile(user) {
      return API.post('/users/profile', user);
    }

    return {
      getFavorites: getFavorites,
      setFavorites: setFavorites,
      getProfile: getProfile,
      setProfile: setProfile
    };
  });