var config = {};
var fs = require('fs');
var path = require('path')

config.dbConfig = 
{
    connectionLimit: 10,
    host: 'piccount.cia0ntd0ooze.ap-northeast-2.rds.amazonaws.com',
    //host: 'localhost',
    port: '3306',
    user: 'root',
    password: 'piccount2018',
    //password:'',
    database: 'piccount',
    debug: false
    
}

module.exports = config;