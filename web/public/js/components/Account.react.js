var React = require('react');
var Router = require('react-router');

var ProfileForm = require('./users/ProfileForm.react');
var FavoritesList = require('./users/FavoritesList.react');
var FavoritesStore = require('../stores/Favorites');
var Actions = require('../Actions');
var API = require('../API');

var Account = React.createClass({

  mixins : [Router.Navigation],

  componentWillMount: function() {
    Actions.loadProfileFromServer();
    Actions.loadFavorites();
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
    API.updateAccount(profile, favorites)
      .then(function() {
        alert('Account updated');
      }, function(error) {
        alert(error);
      });
  },

  handleLogout: function() {
    var that = this;
    API.logout().then(function() {
      that.transitionTo('login');
    });
  }

});

module.exports = Account;
