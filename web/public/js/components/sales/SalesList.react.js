var React = require('react');

var SalesListItem = require('./SalesListItem.react');
var Loading = require('../Loading.react');

var SalesList = React.createClass({

  getInitialState: function() {
    return {
      loading: true,
      sales: []
    };
  },

  componentDidMount: function() {
    var that = this;
    this.props.sales.then(function(sales) {
      that.setState({
        loading: false,
        sales: sales
      });
    });
  },

  render: function() {
    if (this.state.loading) {
      return <Loading />;
    } else {
      return this.renderSales();
    }
  },

  renderSales: function() {
    var sales = this.state.sales.map(function(sale) {
      return <SalesListItem key={sale.id} sale={sale} />
    });

    return <ul className="collection">{sales}</ul>;
  }

});

module.exports = SalesList;
