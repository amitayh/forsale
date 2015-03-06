var React = require('react');
var Router = require('react-router');
var AuthStore = require('../stores/Auth');
var Constants = require('../Constants');

var Link = Router.Link;
var RouteHandler = Router.RouteHandler;

var App = React.createClass({

  mixins : [Router.Navigation],

  componentWillMount: function() {
    AuthStore.addChangeListener(this.authChanged);
  },

  componentWillUnmount: function() {
    AuthStore.removeChangeListener(this.authChanged);
  },

  render: function() {
    return (
      <div>
        <header>
          <ul>
            <li><Link to="app">Welcome</Link></li>
            <li><Link to="sales">Sales</Link></li>
            <li><Link to="account">Acount</Link></li>
          </ul>
        </header>
        <RouteHandler />
      </div>
    );
  },

  authChanged: function() {
    var loggedIn = AuthStore.isLoggedIn();
    this.replaceWith(loggedIn ? 'app' : 'login');
  }

});

module.exports = App;
