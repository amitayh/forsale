var React = require('react');
var Router = require('react-router');

var Link = Router.Link;
var RouteHandler = Router.RouteHandler;

var Sales = React.createClass({

  render: function() {
    return (
      <div>
        <h1>Sales</h1>
        <ul>
          <li><Link to="sales">Recent</Link></li>
          <li><Link to="popular">Popular</Link></li>
          <li><Link to="favorites">Favorites</Link></li>
          <li><Link to="search">Search</Link></li>
        </ul>
        <RouteHandler />
      </div>
    );
  }

});

module.exports = Sales;
