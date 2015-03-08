var FormMixin = {

  getValue: function(ref) {
    return this.refs[ref].getDOMNode().value;
  },

  createChangeHandler: function(field) {
    var that = this;
    return function(event) {
      var state = {};
      state[field] = event.target.value;
      that.setState(state);
    }
  }

};

module.exports = FormMixin;
