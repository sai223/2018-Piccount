const vision = require('@google-cloud/vision');
var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
const client = new vision.ImageAnnotatorClient();
//const fileName;

var urlParser = bodyParser.urlencoded({extended:true});

router.post('/',urlParser,function(req, res, next) {
  
//console.log(req.body);
//console.log(req.files);

    if(req.body.imgFile == undefined){
	console.log('req.imgFile == undefined');
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
            return res.json({success:true, shop:shop_name, category:sorted_category, date:date, item:item, price:price, totalPrice:price});
        })
        .catch(err => {
            console.error('ERROR:', err);
            return res.json({success:false});
        });  
});

module.exports = router;
