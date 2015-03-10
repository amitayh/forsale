var React = require('react');
var Router = require('react-router');
var AuthStore = require('../stores/Auth');

var Link = Router.Link;
var RouteHandler = Router.RouteHandler;

var App = React.createClass({

  mixins : [Router.Navigation, Router.State],

  componentWillMount: function() {
    AuthStore.addChangeListener(this.authChanged);
  },

  componentWillUnmount: function() {
    AuthStore.removeChangeListener(this.authChanged);
  },

  render: function() {
    var pathName = this.getPathname();
    var welcomeActive = (pathName === '/');
    var accountActive = (pathName === '/account');
    var salesActive = /^\/sales/.test(pathName);

    return (
      <div>
        <div className="navbar-fixed">
          <nav>
            <div className="nav-wrapper">
              <ul>
                <li className={this.getMenuClass(welcomeActive)}><Link to="app">Welcome</Link></li>
                <li className={this.getMenuClass(salesActive)}><Link to="sales">Sales</Link></li>
                <li className={this.getMenuClass(accountActive)}><Link to="account">Acount</Link></li>
              </ul>
            </div>
          </nav>
        </div>
        <div className="container">
          <RouteHandler />
        </div>
      </div>
    );
  },

  getMenuClass: function(active) {
    return active ? 'active' : '';
  },

  authChanged: function() {
    this.replaceWith(AuthStore.isLoggedIn() ? 'app' : 'login');
  }

});

module.exports = App;
