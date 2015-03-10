var Q = require('q');
var React = require('react');
var Router = require('react-router');

var AuthActions = require('./Actions.react');
var ProfileForm = require('../users/ProfileForm.react');
var Utils = require('../../Utils');
var Actions = require('../../Actions');

function emptyProfile() {
  return Q({
    email: '',
    name: '',
    password: '',
    birth: '',
    gender: 'MALE'
  });
}

var Register = React.createClass({

  mixins : [Router.Navigation],

  render: function() {
    var profile = emptyProfile();

    return (
      <div>
        <h3>Register</h3>
        <ProfileForm profile={profile} ref="profileForm" />
        <AuthActions onLogin={this.handleLogin} onRegister={this.handleRegister} />
      </div>
    );
  },

  handleLogin: function() {
    this.transitionTo('login');
  },

  handleRegister: function() {
    var profile = this.refs.profileForm.getProfile();
    Actions.register(profile);
  }

});

module.exports = Register;
