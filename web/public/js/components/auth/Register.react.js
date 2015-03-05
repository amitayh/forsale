var React = require('react');

var Register = React.createClass({

  render: function() {
    return (
      <div>
        <p><input type="email" placeholder="Email" /></p>
        <p><input type="text" placeholder="Name" /></p>
        <p><input type="password" placeholder="Password" /></p>
        <p><input type="date" placeholder="Birth date" /></p>
        <p>
          <label><input type="radio" name="gender" value="MALE" defaultChecked /> Male</label>
          <label><input type="radio" name="gender" value="FEMALE" /> Female</label>
        </p>
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
