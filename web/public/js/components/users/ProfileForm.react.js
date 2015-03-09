var React = require('react');
var RadioGroup = require('react-radio-group');

var Loading = require('../Loading.react');
var FormMixin = require('../../FormMixin');

var ProfileForm = React.createClass({

  mixins: [FormMixin],

  getInitialState: function() {
    return {};
  },

  componentDidMount: function() {
    var that = this;
    this.props.profile.then(function(profile) {
      that.setState(profile);
    });
  },

  render: function() {
    if (this.isLoading()) {
      return <Loading />;
    } else {
      var profile = this.getProfile();
      return (
        <div>
          <p>{this.getEmailInput()}</p>
          <p><input type="text" placeholder="Name" ref="name" value={profile.name} onChange={this.createChangeHandler('name')} /></p>
          <p><input type="password" placeholder="Password" ref="password" value={profile.password} onChange={this.createChangeHandler('password')} /></p>
          <p><input type="date" placeholder="Birth date" ref="birth" value={profile.birth} onChange={this.createChangeHandler('birth')} /></p>
          <p>
            <RadioGroup name="gender" value={profile.gender} onChange={this.createChangeHandler('gender')}>
              <label><input type="radio" value="MALE" /> Male</label>
              <label><input type="radio" value="FEMALE" /> Female</label>
            </RadioGroup>
          </p>
        </div>
      );
    }
  },

  getEmailInput: function() {
    var value = this.getProfile().email;
    if (this.props.edit) {
      return <input type="email" value={value} readOnly="readonly" disabled="disabled" />
    } else {
      return <input type="email" placeholder="Email" ref="email" value={value} onChange={this.createChangeHandler('email')} />
    }
  },

  isLoading: function() {
    return (this.state.email === undefined);
  },

  getProfile: function() {
    return this.state;
  }

});

module.exports = ProfileForm;
