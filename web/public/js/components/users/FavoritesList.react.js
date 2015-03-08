var Q = require('q');
var React = require('react');

var FavoritesStore = require('../../stores/Favorites');
var Loading = require('../Loading.react');
var FavoritesListItem = require('./FavoritesListItem.react');
var FavoritesListAdd = require('./FavoritesListAdd.react');

function getState() {
  return {
    loading: FavoritesStore.isLoading(),
    selected: FavoritesStore.getSelected()
  };
}

var FavoritesList = React.createClass({

  getInitialState: function() {
    return getState();
  },

  componentWillMount: function() {
    FavoritesStore.addChangeListener(this.favoritesChanged);
  },

  componentWillUnmount: function() {
    FavoritesStore.removeChangeListener(this.favoritesChanged);
  },

  render: function() {
    if (this.state.loading) {
      return <Loading />;
    } else {
      return this.renderFavorites();
    }
  },

  renderFavorites: function() {
    return (
      <ul>
        {this.getItems()}
        <FavoritesListAdd key="add" />
      </ul>
    );
  },

  getItems: function() {
    return this.state.selected.map(function(vendor) {
      return <FavoritesListItem key={vendor.id} vendor={vendor} />;
    });
  },

  favoritesChanged: function() {
    this.setState(getState());
  }

});

module.exports = FavoritesList;
