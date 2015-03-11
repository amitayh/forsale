var assign = require('object-assign');

var BaseStore = require('./Base');
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loading: false,
  selected: {},
  allVendors: []
};

var Favorites = assign({}, BaseStore, {

  isLoading: function() {
    return state.loading;
  },

  getSelected: function() {
    var selected = state.selected;
    return state.allVendors.filter(function(vendor) {
      return !!selected[vendor.id];
    });
  },

  getRemaining: function() {
    var selected = state.selected;
    return state.allVendors.filter(function(vendor) {
      return !selected[vendor.id];
    });
  }

});

function favoritesLoading() {
  state.loading = true;
  Favorites.emitChange();
}

function getIds(vendors) {
  var ids = {};
  vendors.forEach(function(vendor) {
    ids[vendor.id] = true;
  });
  return ids;
}

function add(vendorId) {
  state.selected[vendorId] = true;
  Favorites.emitChange();
}

function remove(vendorId) {
  delete state.selected[vendorId];
  Favorites.emitChange();
}

function favoritesLoaded(favorites, allVendors) {
  state.loading = false;
  state.selected = getIds(favorites);
  state.allVendors = allVendors;
  Favorites.emitChange();
}

Dispatcher.register(function(action) {

  switch (action.actionType) {
    case Constants.FAVORITES_LOADING:
      favoritesLoading();
      break;

    case Constants.FAVORITES_LOADED:
      favoritesLoaded(action.favorites, action.allVendors);
      break;

    case Constants.FAVORITES_ADD:
      add(action.vendorId);
      break;

    case Constants.FAVORITES_REMOVE:
      remove(action.vendorId);
      break;
  }

});

module.exports = Favorites;
