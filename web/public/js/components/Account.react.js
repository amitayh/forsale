var React = require('react');

var ProfileForm = require('./users/ProfileForm');
var API = require('../API');

var Account = React.createClass({

  componentWillMount: function() {
    this.profile = API.getProfile();
  },

  render: function() {
    return (
      <div>
        <h1>Account</h1>
        <h2>Profile</h2>
        <ProfileForm profile={this.profile} ref="profileForm" />
        <h2>Favorite Vendors</h2>
        <p>
          <button onClick={this.handleUpdate}>Update</button>
          <button onClick={this.handleLogout}>Logout</button>
        </p>
      </div>
    );
  },

  handleUpdate: function() {
    console.log(this.refs.profileForm.getProfile());
  },

  handleLogout: function() {
  }

});

module.exports = Account;
