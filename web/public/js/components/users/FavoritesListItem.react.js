var React = require('react');

var Actions = require('../../Actions');

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
    Actions.removeFavorite(this.props.vendor.id);
  }

});

module.exports = FavoritesListItem;
