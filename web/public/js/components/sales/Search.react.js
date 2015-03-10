var Q = require('q');
var React = require('react');

var SalesList = require('./SalesList.react');
var Utils = require('../../Utils');
var Actions = require('../../Actions');
var API = require('../../API');

function emptyList() {
  return Q([]);
}

var Search = React.createClass({

  getInitialState: function() {
    return {query: ''};
  },

  componentDidMount: function() {
    Actions.loadSales(emptyList());

    this.search = Utils.throttle(function(query) {
      var promise = (query === '') ? emptyList() : API.search(query);
      Actions.loadSales(promise);
    }, 350);
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
    this.search(query);
  }

});

module.exports = Search;
