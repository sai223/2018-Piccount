var express = require('express');
var router = express.Router();

var mysql = require('mysql');
var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: '',
	database: 'piccount_server'
});

const logInSuccess = 1;
const logInFail = 0;

router.get('/logIn', function (req, res) {

	connection.connect(function(err) {
		if (err) throw err;
		console.log('DB is not connected.');
	});

	var userId = req.param('id');
	var userPassword = req.param('password');

	connection.query('SELECT userPw FROM user_info WHERE userId = ' + userId, function(err, result) {

		if (err) throw err;

		if (result == NULL) {

			res.send(logInFail);
			console.log('There is no such information!');

		} else if(result == userPassword) {

			res.send(logInSuccess);
			console.log('logIn is completed!');

		} else {
			
			res.send(logInFail);
			console.log('logIn is wrong information!');
		}

	});
});
