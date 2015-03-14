var React = require('react');

var SalesActions = require('../../actions/Sales');
var Constants = require('../../Constants');

var OrderSelector = React.createClass({

  getInitialState: function() {
    return {active: Constants.DEFAULT_ORDERING};
  },

  render: function() {
    return (
      <p>
        Order by:{' '}
        {this.getItem('title')},{' '}
        {this.getItem('vendor')},{' '}
        {this.getItem('end', 'end date')}
      </p>
    );
  },

  getItem: function(field, displayName) {
    displayName = displayName || field;
    if (this.state.active === field) {
      return <span>{displayName}</span>;
    } else {
      return <a onClick={this.orderByField(field)}>{displayName}</a>;
    }
  },

  orderByField: function(field) {
    var that = this;
    return function() {
      that.setState({active: field});
      SalesActions.orderBy(field);
    };
  }

});

module.exports = OrderSelector;
