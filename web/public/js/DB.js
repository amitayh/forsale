var name = 'forsale';
var version = '1.0';
var displayName = '4Sale DB';
var estimatedSize = 65536;

var DB = openDatabase(name, version, displayName, estimatedSize);

DB.transaction(function(transaction) {
  transaction.executeSql("DROP TABLE sales;");
  transaction.executeSql("CREATE TABLE sales (\n  id INTEGER PRIMARY KEY ASC,\n  title TEXT,\n  start TEXT,\n  end TEXT,\n  vendor TEXT\n);");
});

module.exports = DB;
