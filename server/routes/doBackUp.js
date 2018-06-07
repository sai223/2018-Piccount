var router = require('express').Router();
var userAdapter = require('../adapters/user-adapter');

const User = require('../models/user');

router.post('/', function(req, res){
    if(req.id==undefined){
        res.json({success:false})
    }
    else{

    }   
    userAdapter.search(req.id,null,function(resultCode,rows){
        if(resultCode == "Fail"){
            res.json({success:false});
        }
        else{
            if(rows.length>0){
                userAdapter.backSearch(req.body.id, null, function (resultCode, rows) {
                    if (resultCode == "Fail") {
                        res.json({ success: false });
                    }
                    else {
                        if(rows.length > 0){
                            bak_file = rows[0].backLink 
                            res.json({success: true,bakupFile:bak_file});
                        }
                        else{
                            res.json({success:false});
                        }
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
