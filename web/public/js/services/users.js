angular.module('forsale.services')
  .factory('Users', function (Utils, API) {

    function getFavorites() {
      return API.get('/users/favorites');
    }

    function setFavorites(favorites) {
      return API.post('/users/favorites', favorites);
    }

    function getProfile() {
      return API.get('/users/profile').then(function (profile) {
        profile.birth = new Date(profile.birth);
        return profile;
      });
    }

    function setProfile(user) {
      var userCopy = angular.copy(user);
      userCopy.birth = Utils.formatDate(userCopy.birth);
      return API.post('/users/profile', userCopy);
    }

    return {
      getFavorites: getFavorites,
      setFavorites: setFavorites,
      getProfile: getProfile,
      setProfile: setProfile
    };
  });