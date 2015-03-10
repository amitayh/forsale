var React = require('react');

var Actions = React.createClass({

  render: function() {
    return (
      <p>
        <button className="btn waves-effect waves-light" onClick={this.props.onLogin}>Login</button>
        {' '}
        <button className="btn waves-effect waves-light" onClick={this.props.onRegister}>Register</button>
      </p>
    );
  }

});

module.exports = Actions;
