var React = require('react');

var ProfileForm = require('../users/ProfileForm');

var Register = React.createClass({

  render: function() {
    return (
      <div>
        <h1>Register</h1>
        <ProfileForm />
        <p>
          <button onClick={this.handleRegister}>Register</button>
        </p>
      </div>
    );
  },

  handleRegister: function() {
  }

});

module.exports = Register;
