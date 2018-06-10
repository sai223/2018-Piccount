const vision = require('@google-cloud/vision');
var express = require('express');
var router = express.Router();
var elasticsearch = require('elasticsearch');
var async = require('async');
var exec = require('child_process').exec, child;
const base64 = require('base-64');
const fs = require('fs');

var fileName = '';
var file_path = '/home/ubuntu/server/receipts/';

const client = new vision.ImageAnnotatorClient();

var client1 = new elasticsearch.Client({
    host: '52.14.15.140:9200',
    log: 'trace'
});

function starbucks(arr, cb) {
    async.waterfall([
        function (callback) {

            item_ = [];
            price = [];
            item_ko = [];
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
                        for (k = 0; k < data.length; k++)
                            if (data[k].indexOf('2018-') != -1)
                                //console.log(data[k]);
                                date = data[k];
                    }
                }
                else if (arr[i].indexOf('G)') != -1 || arr[i].indexOf('샌드') != -1 || arr[i].indexOf('SW') != -1 || arr[i].indexOf('F') != -1 || arr[i].indexOf('-T)') != -1) {

                    item_ko.push(arr[i].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, ""));
                }
            }
            for (a = 0; a < item_ko.length; a++) {
                client1.search({
                    index: 'starbucks',
                    type: 'external',
                    body: {
                        query: {
                            match: {
                                item_name: { "query": item_ko[a], "fuzziness": "AUTO" }
                            }
                        }
                    }
                }).then(function (resp) {
                    console.log('start!!')
                    if (resp.hits.hits[0] != null) {
                        var hits = resp.hits.hits[0]._source;
                        item_.push({ "item": hits.item_name, "price": hits.price });
                        price.push(hits.price);
                        if (item_ko.length == item_.length) {
                            console.log('info:', item_);
                            callback(shopName, category, date, item_, price);
                        }
                    }
                    else {
                        item_.push({ "item": '', "price": 0 });
                        price.push(0);
                    }

                }, function (err) {
                    console.trace(err.message);
                    return { success: false }
                });
            }
        }
    ], function (shopName, category, date, item_, price) {
        console.log('get item_', item_);
        console.log('get price:', price);
        for (n = 0; n < price.length; n++) {
            totalPrice += price[n];
        }
        cb({ success: true, shop: shopName, category: category, date: date, info: item_, totalPrice: totalPrice })
    });
}

function gs25(arr, cb) {
    async.waterfall([
        function (callback) {
            item = [];
            price = [];
            item_ = [];
            totalPrice = 0;
            item_ko = [];
            for (i = 0; i < arr.length; i++) {
                if (arr[i].indexOf('GS25') != -1) {
                    if (arr[i].indexOf('"') != -1) {
                        shop = arr[i].split('"');
                        shopName = shop[1];
                        category = '소매';
                    }
                } else if (arr[i].indexOf('2018/') != -1) {
                    data = arr[i].split(/(\s+)/);
                    for (j = 0; j < data.length; j++)
                        if (data[j].indexOf('2018') != -1)
                            date = data[j];
                }
                else if (arr[i].indexOf('데자') != -1 || arr[i].indexOf('바나나') != -1 || arr[i].indexOf('샘물') != -1) {
                    item_ko.push(arr[i].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, ""));
                }
            }
            for (b = 0; b < item_ko.length; b++) {
                client1.search({
                    index: 'gs',
                    type: 'external',
                    body: {
                        query: {
                            match: {
                                item_name: { "query": item_ko[b], "fuzziness": "AUTO" }
                            }
                        }
                    }
                }).then(function (resp) {
                    console.log('start!!')
                    if (resp.hits.hits[0] != null) {
                        var hits = resp.hits.hits[0]._source;
                        item_.push({ "item": hits.item_name, "price": hits.price });
                        price.push(hits.price);
                        if (item_ko.length == item_.length) {
                            console.log('info:', item_);
                            callback(shopName, category, date, item_, price);
                        }
                    }
                    else {
                        item_.push({ "item": '', "price": 0 });
                        price.push(0);
                    }
                }, function (err) {
                    console.trace(err.message);
                    return { success: false }
                });
            }
        }
    ], function (shopName, category, date, item_, price) {
        console.log('get item_', item_);
        console.log('get price:', price);
        for (n = 0; n < price.length; n++) {
            totalPrice += price[n];
        }
        cb({ success: true, shop: shopName, category: category, date: date, info: item_, totalPrice: totalPrice })
    });
}

function normal(arr, cb) {
    async.waterfall([
        function (callback) {
            item = [];
            price = [];
            item_ = [];
            totalPrice = 0;
            item_ko = [];
            item_index = 0;
            price_index = 0;
            item_num = 0;

            if (arr[0].indexOf)
                s = arr[0].split('"');
            sh = s[1];
            shop = sh.split(/(\s+)/);
            shopName = shop[0];

            for (i = 0; i < arr.length; i++) {
                if (arr[i].indexOf('이마트') != -1 || arr[i].indexOf('emar') != -1 || arr[i].indexOf('홈플') != -1 || arr[i].indexOf('homeplu') != -1) {
                    category = '대형마트';
                }
                else if (arr[i].indexOf('201') != -1) {
                    data = arr[i].split(/(\s+)/);
                    for (j = 0; j < data.length; j++)
                        if (data[j].indexOf('201') != -1)
                            date = data[j];
                }
                else if (arr[i].indexOf('수량') != -1) {
                    item_index = i;
                }
                /*
                else if (arr[i].indexOf('금액') !=-1 ){
                    price_index = i;
                }
                */
                else if (arr[i].indexOf('과세 물품') != -1) {
                    item_num = i;
                }
            }
            console.log('item_index:', item_index);
            console.log('item_num', item_num);
            for (k = item_index + 1; k < item_num; k++) {
                han = arr[k].replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g, "");
                console.log('han:', han);
                if (han != '')
                    item_ko.push(han);
                console.log('item_ko:', item_ko);
            }
            for (b = 0; b < item_ko.length; b++) {
                client1.search({
                    index: 'mart',
                    type: 'external',
                    body: {
                        query: {
                            match: {
                                item_name: { "query": item_ko[b], "fuzziness": "AUTO" }
                            }
                        }
                    }
                }).then(function (resp) {
                    console.log('start!!')
                    if (resp.hits.hits[0] != null) {
                        var hits = resp.hits.hits[0]._source;
                        item_.push({ "item": hits.item_name, "price": hits.price });
                        price.push(hits.price);
                        if (item_ko.length == item_.length) {
                            console.log('info:', item_);
                            callback(shopName, category, date, item_, price);
                        }
                    }
                    else {
                        item_.push({ "item": '', "price": 0 });
                        price.push(0);
                    }
                }, function (err) {
                    console.trace(err.message);
                    return { success: false }
                });
            }
        }
    ], function (shopName, category, date, item_, price) {
        console.log('get item_', item_);
        console.log('get price:', price);
        for (n = 0; n < price.length; n++) {
            totalPrice += price[n];
        }
        cb({ success: true, shop: shopName, category: category, date: date, info: item_, totalPrice: totalPrice })
    });
}

function split(info, cb) {
    d = info.description;
    data = JSON.stringify(d); //object to string
    arr = new Array();
    arr = data.split('\\n');
    console.log(arr);
    result = null;
    if (arr[0].indexOf('STARBUCKS') != -1) {
        console.log('starbucks!');
        starbucks(arr, function (res) {
            result = res;
            console.log('result:', result);
            shopName = result.shop;
            category = result.category;
            date = result.date;
            info = result.info;
            //price = result.price;
            totalPrice = result.totalPrice;
            cb({ success: true, shop: shopName, category: category, date: date, info: info, totalPrice: totalPrice });
        });
    }
    else if (arr[0].indexOf('GS25') != -1 || arr[0].indexOf('가까운') != -1) {
        console.log('gs25!');
        gs25(arr, function (res) {
            result = res;
            shopName = result.shop;
            category = result.category;
            date = result.date;
            info = result.info;
            totalPrice = result.totalPrice;
            console.log('res', res);
            cb({ success: true, shop: shopName, category: category, date: date, info: info, totalPrice: totalPrice });
        });
    }
    else {
        console.log('일반 상호');
        normal(arr, function (res) {
            result = res;
            console.log('result:', result);
            shopName = result.shop;
            category = result.category;
            date = result.date;
            info = result.info;
            //price = result.price;
            totalPrice = result.totalPrice;
            cb({ success: true, shop: shopName, category: category, date: date, info: info, totalPrice: totalPrice });
        });
    }
}

router.post('/', function (req, res, next) {

    if (req.body.imgFile == undefined) {
        return res.json({ success: false });
    }
    else {
        fileName = req.body.fileName;
        file_path = '/home/ubuntu/server/receipts/';
        var decodedData = base64.decode(req.body.imgFile);
        file_path += fileName;
        fs.writeFile(file_path, decodedData, "binary", (err) => {
            if (err) throw err;
        });
    }
console.log("file_path: " + file_path);
    client
        .textDetection(file_path)
        .then(results => {
            const detections = results[0].textAnnotations;
            if (detections[0] == null)
                return res.json({ success: false });
            else {
                info = detections[0];
                //console.log('part1',info);
                split(info, function (a) {
                    return res.json(a);
                });
                //return res.json(a);
            }
            child = exec('rm /home/ubuntu/server/receipts/*', function (err, stdout, stderr) {
console.log("rm receipts/*");
                if (err !== null) {
                    console.log('exec error: ' + err);
                }
            });
        })
        .catch(err => {
            console.error('ERROR:', err);
            return res.json({ success: false });
        });

});

module.exports = router;
