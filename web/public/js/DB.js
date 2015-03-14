var Q = require('q');

var name = 'forsale';
var version = '1.0';
var displayName = '4Sale DB';
var estimatedSize = 65536;

var connection = openDatabase(name, version, displayName, estimatedSize);

function noop() {}

function executeSql(sql, params) {
  var deferred = Q.defer();

  params = params || [];
  connection.transaction(function(tx) {
    tx.executeSql(sql, params, function(tx, resultSet) {
      deferred.resolve(resultSet);
    }, deferred.reject);
  }, deferred.reject);

  return deferred.promise;
}

function executeMulti(sql, paramsList) {
  var deferred = Q.defer();

  connection.transaction(function(tx) {
    paramsList.forEach(function(params) {
      tx.executeSql(sql, params, noop, deferred.reject);
    });
    deferred.resolve();
  }, deferred.reject);

  return deferred.promise;
}

function executeSqls(sqls) {
  var deferred = Q.defer();

  connection.transaction(function(tx) {
    sqls.forEach(function(sql) {
      tx.executeSql(sql, [], noop, deferred.reject);
    });
    deferred.resolve();
  }, deferred.reject);

  return deferred.promise;
}

function toArray(rows) {
  var items = [];
  for (var i = 0, l = rows.length; i < l; i++) {
    items.push(rows.item(i));
  }
  return items;
}

function buildDb() {
  return executeSqls([
    'DROP TABLE IF EXISTS sales',
    'CREATE TABLE sales (id INTEGER PRIMARY KEY ASC, title TEXT, start TEXT, end TEXT, vendor TEXT)'
  ]);
}

buildDb();

var DB = {
  executeSql: executeSql,
  executeMulti: executeMulti,
  executeSqls: executeSqls,
  toArray: toArray
};

module.exports = DB;
