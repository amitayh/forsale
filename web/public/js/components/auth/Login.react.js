var React = require('react');
var Router = require('react-router');

var AuthActions = require('./Actions.react');
var FormMixin = require('../../FormMixin');
var Actions = require('../../Actions');
var AuthStore = require('../../stores/Auth');

var Login = React.createClass({

  mixins : [Router.Navigation, FormMixin],

  getInitialState: function() {
    return {error: null};
  },

  componentWillMount: function() {
    AuthStore.addChangeListener(this.authChanged);
  },

  componentWillUnmount: function() {
    AuthStore.removeChangeListener(this.authChanged);
  },

  render: function() {
    var state = this.state;

    var error = state.error ? <p>{state.error}</p> : null;

    return (
      <div>
        <h3>Login</h3>
        {error}
        <p><input type="email" placeholder="Email" ref="email" /></p>
        <p><input type="password" placeholder="Password" ref="password" /></p>
        <AuthActions onLogin={this.handleLogin} onRegister={this.handleRegister} />
      </div>
    );
  },

  authChanged: function() {
    this.setState({error: AuthStore.getError()});
  },

  handleLogin: function() {
    Actions.login(this.getValue('email'), this.getValue('password'));
  },

  handleRegister: function() {
    this.transitionTo('register');
  }

});

module.exports = Login;
