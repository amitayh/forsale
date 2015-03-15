function toFormData(obj) {
  var encode = encodeURIComponent;
  var params = [];
  for (var key in obj) {
    if (obj.hasOwnProperty(key) && obj[key] !== undefined) {
      var encodedKey = encode(key);
      var value = obj[key];
      if (value instanceof Array) {
        for (var i = 0, l = value.length; i < l; i++) {
          params.push(encodedKey + '=' + encode(value[i]));
        }
      } else {
        params.push(encodedKey + '=' + encode(value));
      }
    }
  }
  return params.join('&');
}

function throttle(fn, delay) {
  var timeoutId = null;
  return function() {
    var args = arguments;
    clearTimeout(timeoutId);
    timeoutId = setTimeout(function() {
      fn.apply(fn, args);
    }, delay);
  };
}

var Utils = {
  toFormData: toFormData,
  throttle: throttle
};

module.exports = Utils;
