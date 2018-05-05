var mysql = require('mysql');
var fs = require('fs');
var dbConfig;
var pool;

try {
    dbConfig = require('../secure-config').dbConfig;
    pool = mysql.createPool(dbConfig);
} catch (exception) {
    throw exception;
}

module.exports = pool;