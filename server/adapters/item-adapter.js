var pool = require('./mysql-pool');

var adapter = {};
//select * from starbucks where 상품명 like "%(select mid((SELECT SUBSTRING(?,  (SELECT  INSTR(?, '아메')))),1,4))%"
var searchQuery =  'select 상품명,가격 from starbucks where 상품명 like "%?%"';

adapter.search = function(itemName, cols, cb) {
    
        // search
        var resultCode = "Fail";
    
    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            conn.release();
            cb(resultCode, [])
        } else {
            conn.query(searchQuery, [itemName], function(err, rows) {
                if (err) {
                    console.log(err)
                    resultCode = "Fail";
                    conn.release();
                    cb(resultCode, [])
                } else {
                    resultCode = "Ok";
                    conn.release();
                    cb(resultCode, rows);
                } 
            });
        }
    });
}
  
module.exports = adapter;

