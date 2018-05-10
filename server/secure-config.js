var config = {};

config.dbConfig = {
    connectionLimit: 10,
    //host: '13.125.192.162',
    host: 'localhost',
    port: '3306',
    user: 'root',
    //password: '1208',
    password:'',
    database: 'piccount_server',
    debug: false
    
}

module.exports = config;
