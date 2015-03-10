var React = require('react');

var SalesStore = require('../../stores/Sales');
var SalesListItem = require('./SalesListItem.react');
var Loading = require('../Loading.react');

function getState() {
  return {
    loading: SalesStore.isLoading(),
    sales: SalesStore.getSales()
  };
}

var SalesList = React.createClass({

  getInitialState: function() {
    return getState();
  },

  componentWillMount: function() {
    SalesStore.addChangeListener(this.salesChanged);
  },

  componentWillUnmount: function() {
    SalesStore.removeChangeListener(this.salesChanged);
  },

  render: function() {
    if (this.state.loading) {
      return <Loading />;
    } else {
      return this.renderSales();
    }
  },

  renderSales: function() {
    var sales = this.state.sales;
    if (sales.length) {
      var items = sales.map(function(sale) {
        return <SalesListItem key={sale.id} sale={sale} />
      });
      return <ul className="collection">{items}</ul>;
    } else {
      return null;
    }
  },

  salesChanged: function() {
    this.setState(getState());
  }

});

module.exports = SalesList;
