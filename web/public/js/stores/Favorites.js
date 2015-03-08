var assign = require('object-assign');
var EventEmitter = require('events').EventEmitter;
var Dispatcher = require('../Dispatcher');
var Constants = require('../Constants');

var state = {
  loading: false,
  selected: {},
  allVendors: []
};

var Favorites = assign({}, EventEmitter.prototype, {

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
  },

  addChangeListener: function(callback) {
    this.on(Constants.CHANGE_EVENT, callback);
  },

  removeChangeListener: function(callback) {
    this.removeListener(Constants.CHANGE_EVENT, callback);
  },

  emitChange: function() {
    this.emit(Constants.CHANGE_EVENT);
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
