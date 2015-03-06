var FormMixin = {

  getValue: function(ref) {
    return this.refs[ref].getDOMNode().value;
  }

};

module.exports = FormMixin;
