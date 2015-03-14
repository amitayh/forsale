var React = require('react');

var SalesStore = require('../../stores/Sales');
var SalesListItem = require('./SalesListItem.react');
var Loading = require('../utils/Loading.react');

//function getState() {
//  return {
//    loading: SalesStore.isLoading(),
//    sales: []
//  };
//}

var SalesList = React.createClass({

  getInitialState: function() {
    return {loading: true};
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
      var items = [], sale;
      for (var i = 0, l = sales.length; i < l; i++) {
        sale = sales.item(i);
        items.push(<SalesListItem key={sale.id} sale={sale} />);
      }
      return <ul className="collection">{items}</ul>;
    } else {
      return null;
    }
  },

  salesChanged: function() {
    if (SalesStore.isLoading()) {
      this.setState({loading: true});
    } else {
      var that = this;
      SalesStore.getSales().then(function(sales) {
        that.setState({loading: false, sales: sales});
      });
    }
  }

});

module.exports = SalesList;
