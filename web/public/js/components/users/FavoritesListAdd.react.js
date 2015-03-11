var React = require('react');

var FavoritesStore = require('../../stores/Favorites');
var UsersActions = require('../../actions/Users');

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
        <div>
          <select className="browser-default" value={this.state.value} onChange={this.handleChange}>{this.getOptions()}</select>
          <button className="waves-effect waves-light btn" onClick={this.handleAdd}>
            <i className="mdi-content-add-circle left"></i>
            Add
          </button>
        </div>
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
    UsersActions.addFavorite(this.state.value);
  },

  favoritesChanged: function() {
    this.setState(getState());
  }

});

module.exports = FavoritesListAdd;
