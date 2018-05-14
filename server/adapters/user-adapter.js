const User = require('../models/user');
var pool = require('./mysql-pool');

var adapter = {};

var writeQuery = 'insert into user_info (userId, userPw, userName, userBirth, userPhone) values(?,?,?,?,?);';
var searchQuery = 'select * from user_info where userId=?;';

var backWriteQuery = 'update user_info set backLink = ? where userId=?;';
var backSearchQuery = 'select backLink from user_info where userId=?;';

adapter.write = function(user, cb) {

    // 들어온 유저에 대한 디비 저장 후 컬백 함수 실행 

    var resultCode = "Fail";

    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            cb(resultCode);
        } else {
            conn.query(writeQuery, [user.id, user.password, user.name, user.birthday, user.phoneNumber], function(err) {
                if (err) {
                    console.log(err)
                    resultCode = "Fail";
                    cb(resultCode); 
                } else { 
                    resultCode = "Ok";
                    cb(resultCode)
                }
            });
            conn.release();
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
  
adapter.backWrite = function(userId,userBackLink, cb) {
    
    // 들어온 유저에 대한 디비 저장 후 컬백 함수 실행 
    
    var resultCode = "Fail";
    
    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            cb(resultCode);
        } else {
            conn.query(backWriteQuery, [userBackLink, userId], function(err) {
                if (err) {
                    console.log(err)
                    resultCode = "Fail";
                    cb(resultCode); 
                } else { 
                    resultCode = "Ok";
                    cb(resultCode)
                }
            });
            conn.release();
        }
    });
}

adapter.backSearch = function(userId, cols, cb) {
    
    // search
    var resultCode = "Fail";
    
    pool.getConnection(function(err, conn) {
        if (err) {
            console.log(err)
            resultCode = "Fail";
            conn.release();
            cb(resultCode, [])
        } else {
            conn.query(backSearchQuery, [userId], function(err, rows) {
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
