var React = require('react');
var Router = require('react-router');

var Actions = require('../../Actions');
var AuthStore = require('../../stores/Auth');

var Login = React.createClass({

  mixins : [Router.Navigation, Router.State],

  getInitialState: function() {
    return {
      email: '',
      password: '',
      error: null
    };
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
        {error}
        <p><input type="email" placeholder="Email" value={state.email} onChange={this.emailChanged} /></p>
        <p><input type="password" placeholder="Password" value={state.password} onChange={this.passwordChanged} /></p>
        <p>
          <button onClick={this.handleLogin}>Login</button>
          <button onClick={this.handleRegister}>Register</button>
        </p>
      </div>
    );
  },

  authChanged: function() {
    this.setState({error: AuthStore.getError()});
  },

  emailChanged: function(event) {
    this.setState({email: event.target.value});
  },

  passwordChanged: function(event) {
    this.setState({password: event.target.value});
  },

  handleLogin: function() {
    var state = this.state;
    var nextPath = this.getQuery().nextPath;
    Actions.login(state.email, state.password, nextPath);
  },

  handleRegister: function() {
    this.transitionTo('register');
  }

});

module.exports = Login;
