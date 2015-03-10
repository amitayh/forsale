var name = 'forsale';
var version = '1.0';
var displayName = '4Sale DB';
var estimatedSize = 65536;

var DB = openDatabase(name, version, displayName, estimatedSize);

DB.transaction(function(transaction) {
  // Create our students table if it doesn't exist. IF NOT EXISTS
  //transaction.executeSql("drop table students");
  transaction.executeSql("CREATE TABLE sales");
  console.log("db created");
});

module.exports = DB;
