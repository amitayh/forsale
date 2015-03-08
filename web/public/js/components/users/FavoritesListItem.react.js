var React = require('react');

var Actions = require('../../Actions');

var FavoritesListItem = React.createClass({

  render: function() {
    return <li>{this.props.vendor.name} <button onClick={this.handleRemove}>Remove</button></li>;
  },

  handleRemove: function() {
    Actions.removeFavorite(this.props.vendor.id);
  }

});

module.exports = FavoritesListItem;
