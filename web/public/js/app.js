var React = require('react');
var Router = require('react-router');

var App = require('./components/App.react');
var Welcome = require('./components/Welcome.react');
var Account = require('./components/Account.react');

var Sales = require('./components/Sales.react');
var SalesShow = require('./components/sales/Show.react');
var SalesRecent = require('./components/sales/Recent.react');
var SalesPopular = require('./components/sales/Popular.react');
var SalesFavorites = require('./components/sales/Favorites.react');
var SalesSearch = require('./components/sales/Search.react');

var AuthLogin = require('./components/auth/Login.react');
var AuthRegister = require('./components/auth/Register.react');

var RouteHandler = Router.RouteHandler;
var DefaultRoute = Router.DefaultRoute;
var Route = Router.Route;

var routes = (
  <Route name="app" path="/" handler={App}>

    <Route name="sales" handler={Sales}>
      <Route name="popular" handler={SalesPopular} />
      <Route name="favorites" handler={SalesFavorites} />
      <Route name="search" handler={SalesSearch} />
      <Route name="show" path="show/:id" handler={SalesShow} />
      <DefaultRoute handler={SalesRecent} />
    </Route>

    <Route name="auth" handler={RouteHandler}>
      <Route name="login" handler={AuthLogin} />
      <Route name="register" handler={AuthRegister} />
    </Route>

    <Route name="account" handler={Account} />

    <DefaultRoute handler={Welcome} />
  </Route>
);

Router.run(routes, function (Handler) {
  React.render(<Handler />, document.getElementById('app'));
});
