var React = require('react');
var Router = require('react-router');

var SalesActions = require('../../actions/Sales');
var SalesStore = require('../../stores/Sales');
var Loading = require('../utils/Loading.react');

function getState() {
  return {
    loading: SalesStore.isLoading(),
    sales: SalesStore.getSales()
  };
}

var Show = React.createClass({

  mixins: [Router.State],

  getInitialState: function() {
    return getState();
  },

  componentWillMount: function() {
    SalesStore.addChangeListener(this.salesChanged);
    SalesActions.show(this.getParams().id);
  },

  componentWillUnmount: function() {
    SalesStore.removeChangeListener(this.salesChanged);
  },

  render: function() {
    if (this.state.loading) {
      return <Loading />;
    } else {
      return this.renderSale();
    }
  },

  renderSale: function() {
    var sale = this.state.sales[0];

    return (
      <div className="row">
        <div className="card blue-grey darken-1">
          <div className="card-content white-text">
            <span className="card-title">{sale.title}</span>
            <p>{sale.vendor}</p>
          </div>
        </div>
      </div>
    );
  },

  salesChanged: function() {
    this.setState(getState());
  }

});

module.exports = Show;
