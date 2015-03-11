var React = require('react');

var SalesList = require('./SalesList.react');
var SalesActions = require('../../actions/Sales');

var Popular = React.createClass({

  componentWillMount: function() {
    SalesActions.loadPopular();
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
