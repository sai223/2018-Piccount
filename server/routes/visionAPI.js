const vision = require('@google-cloud/vision');
var express = require('express');
var router = express.Router();
const client = new vision.ImageAnnotatorClient();
//const fileName;

router.post('/', function(req, res, next) {
  
    if(req.imgFile == undefined){
        return res.json({ success : false});  
    }
    else {
        
    }   
    client
        .textDetection(req.imgFile)
        .then(results => {
            const detections = results[0].textAnnotations;
            console.log('Text:');
            detections.forEach(text => console.log(text));
            /*
            list = text[textAnnotations]
            
            data = JSON.stringify(text)

            */
            return res.json({success:true, shop:shop na me, category:sorted category, date:date, item:item, price:price, totalPrice:price});
        })
        .catch(err => {
            console.error('ERROR:', err);
            return res.json({success:false});
        });  
});

module.exports = router;