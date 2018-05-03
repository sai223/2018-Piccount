var express = require('express');
var router = express.Router();

var mysql = require('mysql');
var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: '',
	database: 'piccount_server'
});

const logInSuccess = true;
const logInFail = false;

var userInfo = '';
var userId;
var userPassword;

router.post('/logIn', function (req, res) {

	req.on('data', function(data) {
		userInfo = JSON.parse(data);

		userId = userInfo.id;
		userPassword = userInfo.password;
	});

	req.on('end', function() {
		connection.connect(function(err) {
			if (err) throw err;
			console.log('DB is not connected.');
		});

		connection.query('SELECT userPw FROM user_info WHERE userId = ' + userId, function(err, result) {
	
			if (err) throw err;
			
			var resJson = '';

			if (result == NULL) {
	
				resJson = '{"success":"'+logInFail+'"}';
				console.log('There is no such information!');
	
			} else if(result == userPassword) {

				resJson = '{"success":"'+logInSuccess+'"}';	
				console.log('logIn is completed!');
	
			} else {
				
				resJson = '{"success":"'+logInFail+'"}';
				console.log('logIn is wrong information!');
			}

			res.send(resJson);
	
		});
	});
});
