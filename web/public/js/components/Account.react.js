var React = require('react');

var ProfileForm = require('./users/ProfileForm.react');
var FavoritesList = require('./users/FavoritesList.react');
var FavoritesStore = require('../stores/Favorites');
var UsersActions = require('../actions/Users');
var AuthActions = require('../actions/Auth');

var Account = React.createClass({

  componentWillMount: function() {
    UsersActions.loadProfileFromServer();
    UsersActions.loadFavorites();
  },

  render: function() {
    return (
      <div>
        <h3>Account</h3>
        <h4>Profile</h4>
        <ProfileForm ref="profileForm" edit="true" />
        <h4>Favorite Vendors</h4>
        <FavoritesList />
        <p>
          <button className="waves-effect waves-light btn" onClick={this.handleUpdate}>Update</button>
          {' '}
          <button className="waves-effect waves-light btn" onClick={this.handleLogout}>Logout</button>
        </p>
      </div>
    );
  },

  handleUpdate: function() {
    var profile = this.refs.profileForm.getProfile();
    var favorites = FavoritesStore.getSelected();
    UsersActions.updateAccount(profile, favorites);
  },

  handleLogout: function() {
    AuthActions.logout();
  }

});

module.exports = Account;
