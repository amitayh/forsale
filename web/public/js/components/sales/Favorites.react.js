var React = require('react');

var SalesList = require('./SalesList.react');
var SalesActions = require('../../actions/Sales');

var Favorites = React.createClass({

  componentWillMount: function() {
    SalesActions.loadFavorites();
  },

  render: function() {
    return (
      <div>
        <h4>Favorites</h4>
        <SalesList />
      </div>
    );
  }

});

module.exports = Favorites;
