var React = require('react');

var ProfileForm = React.createClass({

  getInitialState: function() {
    return {
      loading: true,
      profile: {}
    };
  },

  componentDidMount: function() {
    var that = this;
    this.props.profile.then(function(profile) {
      that.setState({
        loading: false,
        profile: profile
      });
    });
  },

  render: function() {
    var profile = this.state.profile;

    return (
      <div>
        <p><input type="email" placeholder="Email" ref="email" value={profile.email} /></p>
        <p><input type="text" placeholder="Name" ref="name" value={profile.name} /></p>
        <p><input type="password" placeholder="Password" ref="password" value={profile.password} /></p>
        <p><input type="date" placeholder="Birth date" ref="birth" value={profile.birth} /></p>
        <p>
          <label><input type="radio" name="gender" value="MALE" defaultChecked /> Male</label>
          <label><input type="radio" name="gender" value="FEMALE" /> Female</label>
        </p>
      </div>
    );
  },

  getProfile: function() {
    return this.state.profile;
  }

});

module.exports = ProfileForm;
