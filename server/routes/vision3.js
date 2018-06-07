const vision = require('@google-cloud/vision');
var express = require('express');
var base64 = require('base-64');
var router = express.Router();
var elasticsearch = require('elasticsearch');
const client = new vision.ImageAnnotatorClient();
const fs = require('fs');

var client1 = new elasticsearch.Client({
    host: '52.14.15.140:9200',
    log: 'trace'
  });  

var fileName = '';
var file_path = '/home/ubuntu/server/receipts/';

function starbucks(arr,cb){
    console.log(arr);
    item_ = [];
    price = [];
    totalPrice = 0;
    for (i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('STARBUCKS') != -1) {
            
            if (arr[i].indexOf('"') != -1) {
                shop = arr[i].split('"');
                shopName = shop[1];
                category = '음식';
            }
            else {
                shopName = arr[i];
                category = '카페';
            }
        } else if (arr[i].indexOf('POS') != -1) {
            if (arr[i].length == 17) {  //전자영수증일때
                data = arr[i + 3].split(/(\s+)/);
                date = data[0];
            }
            else {
                data = arr[i].split(/(\s+)/);  //일반 영수증
                for(k=0;k<data.length;k++)
                    if(data[k].indexOf('2018-')!=-1)
                    //console.log(data[k]);
                        date = data[k];
            }
        }
        else if (arr[i].indexOf('G)') != -1 || arr[i].indexOf('샌드') != -1 || arr[i].indexOf('SW') != -1 || arr[i].indexOf('F') != -1 || arr[i].indexOf('-T)') != -1) {
            item_ko = arr[i].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "")
            client1.search({
                index: 'starbucks',
                type: 'external',
                body: {
                  query: {
                    match: {
                      item_name : {"query":item_ko,"fuzziness":"AUTO"}
                    }
                  }
                }
              }).then(function (resp) {
                //shopName,category,date = split1(arr);
                console.log('start!!')
                var hits = resp.hits.hits[0]._source;
                item_.push({"item":hits.item_name,"price":hits.price});
                price.push(hits.price);
                //num_item = item.length;
                //console.log('item:',item)
                //console.log('price:',price)
                for(k = 0; k<price.length;k++){
                    totalPrice += price[k];
                    console.log('totalprice:',totalPrice);
                }
                console.log('shopName:',shopName);
                //cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice});
                cb({success: true, shop: shopName, category: category, date: date, info: item_, totalPrice: totalPrice});
              }, function (err) {
                  console.trace(err.message);
                  return {success:false}
            });
        }  
}}
/*
function gs25(arr,cb){
    item = [];
    price = [];
    item_index = 0;
    price_index = 0;
    item_ = [];
    
    for (i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('가까운') != -1) {
            if (arr[i].indexOf('"') != -1) {
                shop = arr[i].split('"');
                shopName = shop[1];
                category = '소매';
            }
        } else if (arr[i].indexOf('2018/') != -1) {
            data = arr[i].split(/(\s+)/);
            for(j=0;j<data.length;j++)
                if(data[j].indexOf('2018')!=-1)
                    date = data[j];
            }
        else if (arr[i].indexOf('합계수량') != -1) {
            price_index = i
        }
        else if (arr[i].indexOf('과세 매출')!= -1){
            totalPrice = arr[i-1];
        }
        else if (arr[i].indexOf('카드와') != -1) {
            item_index = i;
            //console.log('item_index:',item_index)
            //console.log('item_',arr[item_index])
        }
    }
    console.log('item_index:',item_index);
    console.log('price_index:',price_index);

    for(k = item_index+1; k < price_index; k++){
        if(arr[k].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "") != "")
            item.push(arr[k]);
        else
            price.push(arr[k]);
            console.log(price);
    }
    for(j = 0; j<item.length;j++){
        item_.push({"item":item[j],"price":price[j]});
    }
    cb({shopName,category,date,item_,totalPrice});
}
*/

function gs25(arr,cb){
    item = [];
    price = [];
    item_ = [];
    totalPrice = 0;
    item_index = 0;
    price_index = 0;
    
    for (i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('GS25') != -1) {
            if (arr[i].indexOf('"') != -1) {
                shop = arr[i].split('"');
                shopName = shop[1];
                category = '소매';
            }
        } else if (arr[i].indexOf('2018/') != -1) {
            data = arr[i].split(/(\s+)/);
            for(j=0;j<data.length;j++)
                if(data[j].indexOf('2018')!=-1)
                    date = data[j];
            }
        else if (arr[i].indexOf('카드와') != -1) {
                item_index = i
                //console.log('item_index:',item_index)
                //console.log('item_',arr[item_index])
            }

        else if (arr[i].indexOf('합계수량') != -1) {
            price_index = i
        }
        else if (arr[i].indexOf('데자') != -1 || arr[i].indexOf('바나나') != -1 || arr[i].indexOf('샘물') != -1 ){
            item_ko = arr[i].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "")
            client1.search({
                index: 'gs',
                type: 'external',
                body: {
                  query: {
                    match: {
                      item_name : {"query":item_ko,"fuzziness":"AUTO"}
                    }
                  }
                }
              }).then(function (resp) {
                //shopName,category,date = split1(arr);
                console.log('start!!')
                var hits = resp.hits.hits[0]._source;
                item_.push({"item":hits.item_name,"price":hits.price});
                price.push(hits.price);
                //num_item = item.length;
                console.log('item:',hits.item_name);
                console.log('info:',item_);
                for(k = 0; k<price.length;k++){
                    totalPrice += price[k];
                    console.log('totalprice:',totalPrice);
                }
                console.log('shopName:',shopName);
                //cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice});
                cb({success: true, shop: shopName, category: category, date: date, info: item_, totalPrice: totalPrice});
              }, function (err) {
                  console.trace(err.message);
                  return {success:false}
            });

        }

    }
}

function split(info,cb) {
    d = info.description;
    data = JSON.stringify(d); //object to string
    arr = new Array();
    arr = data.split('\\n');
    console.log(arr);
    result = null;
    if(arr[0].indexOf('STARBUCKS')!=-1){
        console.log('starbucks!');
        starbucks(arr,function(res){
            result = res;
            console.log('result:',result);
            shopName = result.shop;
            category = result.category;
            date = result.date;
            info = result.info;
            //price = result.price;
            totalPrice = result.totalPrice;
            cb({success: true, shop: shopName, category: category, date: date, info: info, totalPrice: totalPrice });
        });
    }
    else if(arr[0].indexOf('GS25')!= -1 || arr[0].indexOf('가까운')!= -1){
        console.log('gs25!');
        gs25(arr,function(res){
            result = res;
            shopName = result.shop;
            category = result.category;
            date = result.date;
            info = result.info;
            totalPrice = result.totalPrice;
            cb({success: true, shop: shopName, category: category, date: date, info: info, totalPrice: totalPrice });
        });
    }
}


router.post('/', function (req, res, next) {

    if (req.body.imgFile == undefined) {
        return res.json({ success: false });
    }
    else {
	fileName = req.body.fileName;
        var decodedData = base64.decode(req.body.imgFile);
        file_path += fileName;
        fs.writeFile(file_path, decodedData, "binary", (err) => {
                if(err) throw err;
        });
        //console.log(req.body);
    }
    client
        .textDetection(file_path)
        .then(results => {
            const detections = results[0].textAnnotations;
	    file_path = '/home/ubuntu/server/receipts/';
            if (detections[0] == null)
                return res.json({ success: false });
            else {
                info = detections[0];
                //console.log('part1',info);
                split(info,function(a){
                    return res.json(a);
                });
                //return res.json(a);
            }
        })
        .catch(err => {
            console.error('ERROR:', err);
            return res.json({ success: false });
        });

});

module.exports = router;
