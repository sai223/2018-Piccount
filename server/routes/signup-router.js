var router = require('express').Router();
var userAdapter = require('../adapters/user-adapter');

const User = require('../models/user');

router.post('/', function(req, res){

    if (req.body.id == undefined, req.body.password == undefined, req.body.name == undefined, req.body.birthday == undefined, req.body.phoneNumber == undefined){
            return res.json({ success : false});  
    }
    else {
console.log(req.body.id);
console.log(req.body.password);
console.log(req.body.name);
console.log(req.body.birthday);
console.log(req.body.phoneNumber);
    }
    
    userAdapter.search(req.body.id, null, function(resultCode, rows) {
        if (resultCode == "Fail") {
            res.json({ success: false });
        }
        else {
            if(rows.length > 0) {
                res.json({success: false});
                resultCode == "Fail"
            } else {
                var user = new User(req.body.id, req.body.password, req.body.name, req.body.birthday,req.body.phoneNumber);
                userAdapter.write(user, function (resultCode) {
                    if (resultCode == "Fail") {
                        res.json({ success: false });
                    } else {
                        res.json({ success: true });
                    }
                });
            }
        }
    });
});

module.exports = router;
