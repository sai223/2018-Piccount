const vision = require('@google-cloud/vision');
const fs = require('fs');
var express = require('express');
var base64 = require('base-64');
var router = express.Router();
const client = new vision.ImageAnnotatorClient();
var itemAdapter = require('../adapters/item-adapter.js');

var fileName = '';
var file_path = './receipts/';

function split(info) {
    item = [];
    price = [];
    extra = [];
    item_index = [];
    num_extra = 0;
    num_item = 0;
    d = info.description;
    data = JSON.stringify(d); //object to string
    arr = new Array();
    arr = data.split('\\n');
    console.log(arr);
    for (i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('STARBUCKS') != -1) {
            if (arr[i].indexOf('"') != -1) {
                shop = arr[i].split('"');
                shopName = shop[1];
                category = '카페'; 
                
            }
            else {
                shopName = arr[i];
                category = '카페';
            }
        } else if (arr[i].indexOf('POS') != -1) {
//            console.log(arr[i].length);
            if (arr[i].length == 17) {  //전자영수증일때
                data = arr[i + 3].split(/(\s+)/);
                date = data[0];
            }
            else {
                data = arr[i].split(/(\s+)/);  //일반 영수증
                for(k=0;k<data.length;k++)
                    if(data[k].indexOf('2018-')!=-1)
                //console.log(d[8]);
                        date = data[k];
            }
        }
        else if (arr[i].indexOf('결제금액') != -1) {
            totalPrice = arr[i + 2];
        }
        else if (arr[i].indexOf('G)') != -1 || arr[i].indexOf('샌드') != -1 || arr[i].indexOf('SW') != -1 || arr[i].indexOf('F') != -1 || arr[i].indexOf('1-T') != -1) {
	    var item_ko = arr[i].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "")
            
	    item_ko = "Ame";

	    console.log("arr[i] : " + arr[i]);
	    console.log("item_ko : " + item_ko);

            itemAdapter.search(item_ko, null, function(resultCode, rows) {
                if (resultCode == "Fail") {
                    return  false;
                }
                else {
		    console.log("resultCode = " + resultCode);		    
		    console.log("rows.상품명 : " + rows.상품명);
                    item.push(rows.상품명);
                }
            });

//            item.push(arr[i]);
//            console.log(item.length);
            item_index.push(i);
            num_item = item.length;
        }
        else if (arr[i].indexOf('L') != -1) {
            extra.push(arr[i]);
            num_extra = extra.length;
            //price.push(arr[i + num_item + num_extra]);            
        }
    }
        for(j=0;j<item_index.length;j++)
            price.push(arr[item_index[j] + num_item + num_extra]);     
            
            
        
        /*
        else if (arr[i].indexOf('SW') != -1){
            item.push(arr[i]);
            item.push(arr[i+1]);
        }
        else if (arr[i].indexOf('합계') != -1){
            price = arr[i-2];
        }
        */

        /*
       
        else if (arr[i].indexOf('POS') != -1) {
            data = arr[i].split(/(\s+)/);
            //console.log(d[8]);
            date = data[8];
        }
        else if (arr[i].indexOf('주문') != -1) {
            item = arr[i + 3];
            price = arr[i + 1];
        }
        else if (arr[i] == '결제금액')
            totalPrice = arr[i + 2];
            */
        //return res.json({success:true, shop:shopName, category:category, date:date, item:item, price:price, totalPrice:totalPrice});
    

    if (price != undefined)
        return { success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice };
}


fileName = 'sample.jpg';

client
    .textDetection(fileName)
    .then(results => {
        const detections = results[0].textAnnotations;
        if (detections[0] == null)
            return false;
        else {
            info = detections[0];
            //console.log('part1',info);
            a = split(info);
            console.log(a);
	    return true;
        }
    })
    .catch(err => {
        console.error('ERROR:', err);
        return false;
    });

module.exports = router;
