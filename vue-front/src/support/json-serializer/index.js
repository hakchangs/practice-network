export default {
  serialize: function (message) {
    return JSON.stringify(message);
  },
  deserialize: function (message) {
    return JSON.parse(message);
  },
};
