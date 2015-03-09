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
      <div>
        {this.getList()}
        <FavoritesListAdd />
      </div>
    );
  },

  getList: function() {
    var selected = this.state.selected;
    if (selected.length) {
      var items = selected.map(function(vendor) {
        return <FavoritesListItem key={vendor.id} vendor={vendor} />;
      });
      return <ul className="collection">{items}</ul>;
    } else {
      return null;
    }
  },

  favoritesChanged: function() {
    this.setState(getState());
  }

});

module.exports = FavoritesList;
