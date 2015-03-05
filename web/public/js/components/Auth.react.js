var React = require('react');
var Router = require('react-router');

var RouteHandler = Router.RouteHandler;

var Auth = React.createClass({

  render: function() {
    return (
      <div>
        <h1>Auth</h1>
        <RouteHandler />
      </div>
    );
  }

});

module.exports = Auth;
