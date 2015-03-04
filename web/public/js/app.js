var React = require('react');
var Router = require('react-router');

var App = require('./components/App.react');
var Inbox = require('./components/Inbox.react');
var Calendar = require('./components/Calendar.react');
var Dashboard = require('./components/Dashboard.react');

var DefaultRoute = Router.DefaultRoute;
var Route = Router.Route;

var routes = (
  <Route name="app" path="/" handler={App}>
    <Route name="inbox" handler={Inbox} />
    <Route name="calendar" handler={Calendar} />
    <DefaultRoute handler={Dashboard} />
  </Route>
);

Router.run(routes, function (Handler) {
  React.render(<Handler />, document.getElementById('app'));
});
