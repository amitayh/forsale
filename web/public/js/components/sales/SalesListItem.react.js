var React = require('react');
var Router = require('react-router');

var Link = Router.Link;

var SalesListItem = React.createClass({

  render: function() {
    var sale = this.props.sale;

    return (
      <li>
        <h3><Link to="show" params={sale}>{sale.title}</Link></h3>
        <p>{sale.vendor}</p>
      </li>
    );
  }

});

module.exports = SalesListItem;
