var React = require('react');

var SalesList = require('./SalesList.react');
var Actions = require('../../Actions');
var API = require('../../API');

var Recent = React.createClass({

  componentWillMount: function() {
    Actions.loadSales(API.getRecentSales());
  },

  render: function() {
    return (
      <div>
        <h4>Recent</h4>
        <SalesList />
      </div>
    );
  }

});

module.exports = Recent;
