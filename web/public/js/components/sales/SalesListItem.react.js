var React = require('react');
var Router = require('react-router');

var FormattedDate = require('../FormattedDate.react');
var Link = Router.Link;

var SalesListItem = React.createClass({

  render: function() {
    var sale = this.props.sale;

    return (
      <li className="collection-item avatar">
        <i className="mdi-maps-local-offer circle"></i>
        <Link className="title" to="show" params={sale}>{sale.title}</Link>
        <p>
          {sale.vendor}<br />
          <small>Ends on <FormattedDate value={sale.end} /></small>
        </p>
      </li>
    );
  }

});

module.exports = SalesListItem;
