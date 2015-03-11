var React = require('react');

var MONTHS = [
  'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
  'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
];

var FormattedDate = React.createClass({

  render: function() {
    var date = this.props.value;
    var month = MONTHS[date.getMonth()];
    var day = date.getDate();
    var year = date.getFullYear();
    return <span>{month} {day}, {year}</span>;
  }

});

module.exports = FormattedDate;
