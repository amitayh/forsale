var Q = require('q');
var React = require('react');

var ProfileForm = require('../users/ProfileForm.react');

var Register = React.createClass({

  render: function() {
    var profile = this.emptyProfile();

    return (
      <div>
        <h1>Register</h1>
        <ProfileForm profile={profile} ref="profileForm" />
        <p>
          <button className="btn waves-effect waves-light" onClick={this.handleRegister}>Register</button>
        </p>
      </div>
    );
  },

  emptyProfile: function() {
    return Q.fcall(function() {
      return {
        email: '',
        name: '',
        password: '',
        birth: '',
        gender: 'MALE'
      };
    });
  },

  handleRegister: function() {
    console.log(this.refs.profileForm.getProfile());
  }

});

module.exports = Register;
