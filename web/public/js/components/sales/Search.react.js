var React = require('react');

var SalesList = require('./SalesList.react');
var SalesActions = require('../../actions/Sales');

var Search = React.createClass({

  getInitialState: function() {
    return {query: ''};
  },

  componentDidMount: function() {
    SalesActions.load([]);
  },

  render: function() {
    return (
      <div>
        <h4>Search</h4>
        <input type="search" placeholder="Search..." value={this.state.query} onChange={this.handleChange} />
        <SalesList />
      </div>
    );
  },

  handleChange: function(event) {
    var query = event.target.value;
    this.setState({query: query});
    SalesActions.search(query);
  }

});

module.exports = Search;
