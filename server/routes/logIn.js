var express = require('express');
var router = express.Router();

var userAdapter = require('../adapters/user-adapter');

const User = require('../models/user');

const logInSuccess = 'true';
const logInFail = 'false';

var userInfo = '';
var userId = '';
var userPassword = '';

router.post('/', function (req, res) {
	if(req.body.id == undefined, req.body.password == undefined) {
		return res.json({success : false});
	} else {
console.log(req.body.id);
console.log(req.body.password);

		userId = req.body.id;
		userPassword = req.body.password;

		userAdapter.search(userId, null, function(resultCode, rows) {
			if(resultCode == "Fail") {
				res.json({success: false});
			} else {
				if(rows.length == 0) {
					res.json({success: false});
				} else {
					if(userPassword == rows[0].userPw){
						res.json({success: true});
					} else {
						res.json({success: false});
					}
				}
			}
		});
	}
});

module.exports = router;
