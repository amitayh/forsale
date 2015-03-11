var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loading: false,
  profile: {}
};

var Profile = assign({}, BaseStore, {

  isLoading: function() {
    return state.loading;
  },

  getProfile: function() {
    return state.profile;
  }

});

function profileLoading() {
  state.loading = true;
  state.profile = {};
  Profile.emitChange();
}

function profileLoaded(profile) {
  state.loading = false;
  state.profile = profile;
  Profile.emitChange();
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.PROFILE_LOADING:
      profileLoading();
      break;

    case Constants.PROFILE_LOADED:
      profileLoaded(action.profile);
      break;
  }

});

module.exports = Profile;
