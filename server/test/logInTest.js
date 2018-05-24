var userAdapter = require('../adapters/user-adapter');
const User = require('../models/user');

const ID = 'ksos104';
const PASSWORD = '1234123';

userAdapter.search(ID, null, function(resultCode, rows) {
	if(resultCode == "Fail") {
		console.log('Connection Failed');
	} else {
		if(rows.length == 0) {
			console.log('Wrong ID');
		} else {
			if(PASSWORD == rows[0].userPw) {
				console.log('LogIn Complete');
			} else {
				console.log('Wrong Password');
			}
		}
	}
});
