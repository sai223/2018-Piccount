const User = require('../models/user');
var pool = require('./mysql-pool');

var adapter = {};

var writeQuery = 'insert into user (id, password, name, birthday, phoneNumber) values(?,?,?,?,?);';
var searchQuery = 'select * from user where id=?;';

adapter.write = function(user, cb) {

    // 들어온 유저에 대한 디비 저장 후 컬백 함수 실행 

    var resultCode = "Fail";

    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            conn.release();
            cb(resultCode);
        } else {
            conn.query(writeQuery, [user.id, user.password, user.name, user.birthday, user.phonNumber], function(err) {
                if (err) {
                    console.log(err)
                    resultCode = "Fail";
                    conn.release();
                    cb(resultCode); 
                } else { 
                    resultCode = "Ok";
                    conn.release();
                    cb(resultCode)
                    
                }
            });
        }
    });
}

adapter.search = function(userId, cols, cb) {
    
        // search
        var resultCode = "Fail";
    
        pool.getConnection(function(err, conn) {
            if (err) {
                console.log(err)
                resultCode = "Fail";
                conn.release();
                cb(resultCode, [])
            } else {
                conn.query(searchQuery, [userId], function(err, rows) {
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
