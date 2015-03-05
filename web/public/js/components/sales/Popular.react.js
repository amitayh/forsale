var React = require('react');

var SalesList = require('./SalesList.react');
var API = require('../../API');

var Popular = React.createClass({

  componentWillMount: function() {
    this.sales = API.getPopularSales();
  },

  render: function() {
    return (
      <div>
        <h2>Popular</h2>
        <SalesList sales={this.sales} />
      </div>
    );
  }

});

module.exports = Popular;
