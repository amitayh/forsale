var React = require('react');

var Loading = React.createClass({

  render: function() {
    return (
      <div className="progress">
        <div className="indeterminate"></div>
      </div>
    );
  }

});

module.exports = Loading;
