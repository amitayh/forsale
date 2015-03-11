var React = require('react');

var UsersActions = require('../../actions/Users');

var FavoritesListItem = React.createClass({

  render: function() {
    return (
      <li className="collection-item">
        {this.props.vendor.name}
        <span className="secondary-content" onClick={this.handleRemove}>
          <i className="mdi-action-highlight-remove"></i>
        </span>
      </li>
    );
  },

  handleRemove: function() {
    UsersActions.removeFavorite(this.props.vendor.id);
  }

});

module.exports = FavoritesListItem;
