var React = require('react');
var Router = require('react-router');

var AuthButtons = require('./Buttons.react');
var FormMixin = require('../../FormMixin');
var AuthActions = require('../../actions/Auth');
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
        <AuthButtons onLogin={this.handleLogin} onRegister={this.handleRegister} />
      </div>
    );
  },

  authChanged: function() {
    this.setState({error: AuthStore.getError()});
  },

  handleLogin: function() {
    AuthActions.login(this.getValue('email'), this.getValue('password'));
  },

  handleRegister: function() {
    this.transitionTo('register');
  }

});

module.exports = Login;
