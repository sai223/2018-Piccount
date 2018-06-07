var pool = require('./mysql-pool');

var adapter = {};
//select * from starbucks where 상품명 like "%(select mid((SELECT SUBSTRING(?,  (SELECT  INSTR(?, '아메')))),1,4))%"
var searchQuery = "select 상품명,가격 from starbucks where 상품명 like ?";

adapter.search = function(itemName, cols, cb) {
    
        // search
        var resultCode = "Fail";

//console.log("itemName in item-pool : " + itemName);
    
    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            conn.release();
            cb(resultCode, [])
        } else {
//	    var query = conn.query(searchQuery, function(err, rows) {
            var query = conn.query(searchQuery, "%"+itemName+"%", function(err, rows) {

console.log(query.sql);
//console.log("itemName in conn.query : "+itemName);

                if (err) {
                    console.log(err)
                    resultCode = "Fail";
                    conn.release();
                    cb(resultCode, [])
                } else {
                   resultCode = "Ok";
                    conn.release();

console.log("resultCode in mySQL : " + resultCode);
console.log("rows[0].상품명 : " + rows[0].상품명);

                    cb(resultCode, rows);
		    return rows;
                } 
            });
        }
    });
}
 
module.exports = adapter;

