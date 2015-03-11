var React = require('react');
var Router = require('react-router');

var AuthButtons = require('./Buttons.react');
var ProfileForm = require('../users/ProfileForm.react');
var Utils = require('../../Utils');
var AuthActions = require('../../actions/Auth');
var UsersActions = require('../../actions/Users');

var Register = React.createClass({

  mixins : [Router.Navigation],

  componentDidMount: function() {
    UsersActions.loadProfile({
      email: '',
      name: '',
      password: '',
      birth: '',
      gender: 'MALE'
    });
  },

  render: function() {
    return (
      <div>
        <h3>Register</h3>
        <ProfileForm ref="profileForm" />
        <AuthButtons onLogin={this.handleLogin} onRegister={this.handleRegister} />
      </div>
    );
  },

  handleLogin: function() {
    this.transitionTo('login');
  },

  handleRegister: function() {
    var profile = this.refs.profileForm.getProfile();
    AuthActions.register(profile);
  }

});

module.exports = Register;
