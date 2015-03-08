var React = require('react');

var ProfileForm = require('./users/ProfileForm.react');
var FavoritesList = require('./users/FavoritesList.react');
var FavoritesStore = require('../stores/Favorites');
var Actions = require('../Actions');
var API = require('../API');

var Account = React.createClass({

  componentWillMount: function() {
    this.profile = API.getProfile();
    Actions.loadFavorites();
  },

  render: function() {
    return (
      <div>
        <h1>Account</h1>
        <h2>Profile</h2>
        <ProfileForm profile={this.profile} ref="profileForm" />
        <h2>Favorite Vendors</h2>
        <FavoritesList />
        <p>
          <button onClick={this.handleUpdate}>Update</button>
          <button onClick={this.handleLogout}>Logout</button>
        </p>
      </div>
    );
  },

  handleUpdate: function() {
    var profile = this.refs.profileForm.getProfile();
    var favorites = FavoritesStore.getSelected();
    API.updateAccount(profile, favorites)
      .then(function() {
        alert('OK');
      }, function() {
        alert('NOT OK');
      });
  },

  handleLogout: function() {
  }

});

module.exports = Account;
