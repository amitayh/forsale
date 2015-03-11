var React = require('react');

var Buttons = React.createClass({

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

module.exports = Buttons;
