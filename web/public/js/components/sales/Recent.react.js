var React = require('react');

var SalesList = require('./SalesList.react');
var API = require('../../API');

var Recent = React.createClass({

  componentWillMount: function() {
    this.sales = API.getRecentSales();
  },

  render: function() {
    return (
      <div>
        <h2>Recent</h2>
        <SalesList sales={this.sales} />
      </div>
    );
  }

});

module.exports = Recent;
