var React = require('react');

var SalesList = require('./SalesList.react');
var Actions = require('../../Actions');
var API = require('../../API');

var Popular = React.createClass({

  componentWillMount: function() {
    Actions.loadSales(API.getPopularSales());
  },

  render: function() {
    return (
      <div>
        <h4>Popular</h4>
        <SalesList />
      </div>
    );
  }

});

module.exports = Popular;
