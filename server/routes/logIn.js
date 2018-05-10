var express = require('express');
var router = express.Router();

var userAdapter = require('../adapters/user-adapter');

const User = require('../models/user');

/*
var mysql = require('mysql');
var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: '',
	database: 'piccount_server'
});
*/

const logInSuccess = 'true';
const logInFail = 'false';

var userInfo = '';
var userId;
var userPassword;

router.post('/', function (req, res) {

	if(req.body.id == undefined, req.body.password == undefined) {
		return res.json({success : false});
	} else {
		userId = req.body.id;
		userPassword = req.body.password;

		userAdapter.search(userId, null, function(resultCode, rows) {
			if(resultCode == "Fail") {
				res.json({success: false});
			} else {
//				if(rows.length > 0) {
					res.json({success: false});
					resultCode == "Fail"
				} else {
					if(userPassword == rows.body.userPw){
						res.json({success: true});
					} else {
						res.json({success: false});
					}
				}
			}
		}
	}
});


//	req.on('data', function(data) {
//		userInfo = JSON.parse(data);
//
//		userId = userInfo.id;
//		userPassword = userInfo.password;
//	});
//
//	req.on('end', function() {
//		connection.connect(function(err) {
//			if (err) throw err;
//			console.log('DB is not connected.');
//		});
//
//		connection.query('SELECT userPw FROM user_info WHERE userId = ' + userId, function(err, result) {
//	
//			if (err) throw err;
//			
//			var resJson = '';
//
//			if (result == NULL) {
//	
//				resJson = '{"success":"'+logInFail+'"}';
//				console.log('There is no such information!');
//	
//			} else if(result == userPassword) {
//
//				resJson = '{"success":"'+logInSuccess+'"}';	
//				console.log('logIn is completed!');
//	
//			} else {
//				
//				resJson = '{"success":"'+logInFail+'"}';
//				console.log('logIn is wrong information!');
//			}
//
//			res.send(resJson);
//	
//		});
//	});
//});
