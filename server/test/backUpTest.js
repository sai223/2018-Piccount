var userAdapter = require('../adapters/user-adapter');
const User = require('../models/user');

userAdapter.backWrite('heeeee123','12323', function(resultCode, ret){
    if (resultCode == "Ok") {
        console.log('write success'+JSON.stringify(ret));
        process.exit(0)
    }
    else {
        console.log('db error userId');
        process.exit(-22)
    }
});

userAdapter.backSearch('heeeee123', [], function(resultCode, rows) {
    for (i in rows) {
        console.log(rows[i]);
    }
    console.log('hello');
});
