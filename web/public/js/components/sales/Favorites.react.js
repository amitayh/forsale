var React = require('react');

var SalesList = require('./SalesList.react');
var API = require('../../API');

var Favorites = React.createClass({

  componentWillMount: function() {
    this.sales = API.getFavoritesSales();
  },

  render: function() {
    return (
      <div>
        <h2>Favorites</h2>
        <SalesList sales={this.sales} />
      </div>
    );
  }

});

module.exports = Favorites;
