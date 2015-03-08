var React = require('react');

var FavoritesStore = require('../../stores/Favorites');
var Actions = require('../../Actions');

function getState() {
  var remaining = FavoritesStore.getRemaining();
  var state = {remaining: remaining};
  if (remaining.length) {
    state.value = remaining[0].id;
  }
  return state;
}

var FavoritesListAdd = React.createClass({

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
    if (this.state.remaining.length) {
      return (
        <li>
          <select value={this.state.value} onChange={this.handleChange}>{this.getOptions()}</select>
          <button onClick={this.handleAdd}>Add</button>
        </li>
      );
    } else {
      return null;
    }
  },

  getOptions: function() {
    return this.state.remaining.map(function(vendor) {
      return <option key={vendor.id} value={vendor.id}>{vendor.name}</option>;
    });
  },

  handleChange: function(event) {
    this.setState({value: event.target.value});
  },

  handleAdd: function() {
    Actions.addFavorite(this.state.value);
  },

  favoritesChanged: function() {
    this.setState(getState());
  }

});

module.exports = FavoritesListAdd;
