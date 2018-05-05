var userAdapter = require('../adapters/user-adapter');
const User = require('../models/user');
var newUser = new User('heeeee123','password','HeeJin','1995.09.28','01040');

userAdapter.write(newUser, function(resultCode, ret){
    if (resultCode == "Ok") {
        console.log('write success'+JSON.stringify(ret));
        process.exit(0)
    }
    else {
        console.log('db error userId');
        process.exit(-22)
    }
});

userAdapter.search('heeeee123', [], function(resultCode, rows) {
    for (i in rows) {
        console.log(rows[i]);
    }
    console.log('hello');
});
