var React = require('react');
var Router = require('react-router');
var AuthStore = require('../stores/Auth');
var Constants = require('../Constants');

var Link = Router.Link;
var RouteHandler = Router.RouteHandler;

var App = React.createClass({

  mixins : [Router.Navigation],

  getInitialState: function() {
    return {loggedIn: AuthStore.isLoggedIn()};
  },

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
          <p>{this.state.loggedIn ? 'Logged in' : 'Logged out'}</p>
          <ul>
            <li><Link to="app">Welcome</Link></li>
            <li><Link to="sales">Sales</Link></li>
            <li><Link to="inbox">Inbox</Link></li>
            <li><Link to="calendar">Calendar</Link></li>
          </ul>
        </header>
        <RouteHandler />
      </div>
    );
  },

  authChanged: function() {
    var loggedIn = AuthStore.isLoggedIn();
    this.replaceWith(loggedIn ? 'app' : 'login');
    this.setState({loggedIn: loggedIn});
  }

});

module.exports = App;
