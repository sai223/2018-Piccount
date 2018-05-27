const vision = require('@google-cloud/vision');
var express = require('express');
var router = express.Router();
const client = new vision.ImageAnnotatorClient();
//const fileName;

function split(info) {

    d = info.description;
    data = JSON.stringify(d); //object to string
    arr = new Array();
    arr= data.split('\\n');

    for(i=0;i<arr.length;i++){

        if(arr[i]=='STARBUCKS'){
            shopName = arr[i];
            category = '카페';
        }
        else if(arr[i]=='031-215-4516')
            date = arr[i-1];
        else if(arr[i] == 'T)자바칩F')
            item = arr[i];
        else if(arr[i] == '합계')
            totalPrice = arr[i+2];
        else if(arr[i] == '결제금액')
            price = arr[i+2];
            //return res.json({success:true, shop:shopName, category:category, date:date, item:item, price:price, totalPrice:totalPrice});
    }
    if(price != undefined)
        return {success:true, shop:shopName, category:category, date:date, item:item, price:price, totalPrice:totalPrice};
}


router.post('/', function(req, res, next) {
  
    if(req.body.imgFile == undefined){
        return res.json({ success : false});  
    }
    else {
        console.log(req.body);
    }   
    client
        .textDetection(req.body.imgFile)
        .then(results => {
            const detections = results[0].textAnnotations;
            if(detections[0] == null)
                return res.json({success:false});
            else{
                info = detections[0];
                console.log('part1',info);
                a = split(info);
                return res.json(a);
            }    
        })
        .catch(err => {
            console.error('ERROR:', err);
            return res.json({success:false});
        });  
    
});

module.exports = router;
