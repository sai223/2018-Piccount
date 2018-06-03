const vision = require('@google-cloud/vision');
var express = require('express');
var router = express.Router();
var elasticsearch = require('elasticsearch');
const client = new vision.ImageAnnotatorClient();

var client1 = new elasticsearch.Client({
    host: 'localhost:9200',
    log: 'trace'
  });  

function starbucks(arr,cb){
    console.log(arr);
    item = [];
    price = [];
    totalPrice = 0;
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
                index: 'customer',
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
                item.push(hits.item_name);
                price.push(hits.price);
                //num_item = item.length;
                console.log('item:',item)
                console.log('price:',price)
                for(k = 0; k<price.length;k++){
                    totalPrice += price[k];
                    console.log('totalprice:',totalPrice);
                }
                console.log('shopName:',shopName);
                cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice});
              }, function (err) {
                  console.trace(err.message);
                  return {success:false}
            });
        }  
}}

function gs25(arr,cb){
    item = [];
    price = [];
    item_index = 0;
    price_index = 0;
    
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
    }
    cb({shopName,category,date,item,price,totalPrice});
}
/*
function gs25(arr,cb){
    item = [];
    price = [];
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
    }

    for(k = item_index; k < (price_index - item.length -1); k++){
        item_ko = arr[k+1].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "")
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
            item.push(hits.item_name);
            price.push(hits.price);
            //num_item = item.length;
            console.log('item:',item);
            console.log('price:',price);
            console.log('totalPrice:',totalPrice);
            for(j = 0; j<price.length;j++){
                totalPrice += price[j];
            }
            console.log('K:',k);
            if(k == price_index - item.length - 2)
                cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice});
            else{

            }
          }, function (err) {
              console.trace(err.message);
              return {success:false}
        });
    }
}
*/
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
            shopName = result.shopName;
            category = result.category;
            date = result.date;
            item = result.item;
            price = result.price;
            totalPrice = result.totalPrice;
            cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice });
        });
    }
    else if(arr[0].indexOf('GS25')!= -1 || arr[0].indexOf('가까운')!= -1){
        console.log('gs25!');
        gs25(arr,function(res){
            result = res;
            shopName = result.shopName;
            category = result.category;
            date = result.date;
            item = result.item;
            price = result.price;
            totalPrice = result.totalPrice;
            cb({success: true, shop: shopName, category: category, date: date, item: item, price: price, totalPrice: totalPrice });
        });
    }
}


router.post('/', function (req, res, next) {

    if (req.body.imgFile == undefined) {
        return res.json({ success: false });
    }
    else {
        //console.log(req.body);
    }
    client
        .textDetection(req.body.imgFile)
        .then(results => {
            const detections = results[0].textAnnotations;
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
