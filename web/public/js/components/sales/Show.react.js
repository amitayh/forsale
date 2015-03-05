var React = require('react');
var Router = require('react-router');

var API = require('../../API');
var Loading = require('../Loading.react');

var Show = React.createClass({

  mixins: [Router.State],

  getInitialState: function() {
    return {
      loading: true,
      sale: {}
    };
  },

  componentWillMount: function() {
    var that = this;
    this.sale = API.getSale(this.getParams().id)
      .then(function(sale) {
        that.setState({
          loading: false,
          sale: sale
        });
      });
  },

  render: function() {
    if (this.state.loading) {
      return <Loading />;
    } else {
      return this.renderSale();
    }
  },

  renderSale: function() {
    var sale = this.state.sale;

    return (
      <div>
        <h2>{sale.title}</h2>
        <p>{sale.vendor}</p>
      </div>
    );
  }

});

module.exports = Show;
