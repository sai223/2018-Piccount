var router = require('express').Router();
var userAdapter = require('../adapters/user-adapter');

const User = require('../models/user');

router.post('/', function(req, res){
    
    userAdapter.backSearch(req.body.id, null, function (resultCode, rows) {
        if (resultCode == "Fail") {
            res.json({ success: false });
        }
        else {
            if(rows.length > 0){
                id = req.body.id
                backLink = req.body.backLink
                userAdapter.backWrite(id,backLink,function(resultCode){
                    if(resultCode == "Fail"){
                        res.json({success: false});
                    }
                    else{
                        res.json({success: true});
                    }
                });
            }
            else{
                res.json({success:false});
            }
        }
    });
});

module.exports = router;
