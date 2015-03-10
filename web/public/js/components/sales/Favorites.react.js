var React = require('react');

var SalesList = require('./SalesList.react');
var Actions = require('../../Actions');
var API = require('../../API');

var Favorites = React.createClass({

  componentWillMount: function() {
    Actions.loadSales(API.getFavoritesSales());
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
