var React = require('react');

var SalesList = require('./SalesList.react');
var SalesActions = require('../../actions/Sales');

var Recent = React.createClass({

  componentWillMount: function() {
    SalesActions.loadRecent();
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
