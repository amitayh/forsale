var Q = require('q');

var MessagesActions = require('./Messages');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');
var API = require('../API');

function getId(vendor) {
  return vendor.id;
}

var Users = {

  loadProfile: function(profile) {
    Dispatcher.dispatch({
      actionType: Constants.PROFILE_LOADED,
      profile: profile
    });
  },

  loadProfileFromServer: function() {
    Dispatcher.dispatch({actionType: Constants.PROFILE_LOADING});
    API.doGet('/users/profile').then(Users.loadProfile);
  },

  updateAccount: function(profile, favorites) {
    var favoritesIds = favorites.map(getId);
    var updateProfile = API.doPost('/users/profile', profile);
    var updateFavorites = API.doPost('/users/favorites', {vendor_id: favoritesIds});

    Q.all([updateProfile, updateFavorites])
      .then(function() {
        MessagesActions.notify('Account updated');
      }, function(error) {
        MessagesActions.error(error);
      });
  },

  loadFavorites: function() {
    Dispatcher.dispatch({actionType: Constants.FAVORITES_LOADING});

    var favorites = API.doGet('/users/favorites');
    var vendors = API.doGet('/vendors/list');

    Q.all([favorites, vendors])
      .then(function(result) {
        Dispatcher.dispatch({
          actionType: Constants.FAVORITES_LOADED,
          favorites: result[0],
          allVendors: result[1]
        });
      });
  },

  removeFavorite: function(vendorId) {
    Dispatcher.dispatch({
      actionType: Constants.FAVORITES_REMOVE,
      vendorId: vendorId
    });
  },

  addFavorite: function(vendorId) {
    Dispatcher.dispatch({
      actionType: Constants.FAVORITES_ADD,
      vendorId: vendorId
    });
  }

};

module.exports = Users;
