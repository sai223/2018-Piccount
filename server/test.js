const vision = require('@google-cloud/vision');
const client = new vision.ImageAnnotatorClient();

const fs = require('fs');
var base64 = require('base-64');

var fileName = "qwer#123";
var file_path = "./" + fileName;

fs.readFile('1.txt', function(err,data) {
	if(err) {
		return console.log(err);
	}

	var decodedData= base64.decode(data);

	fs.writeFile(file_path, decodedData, 'binary', (err) => {
		if(err) throw err;
		else {
			client
				.textDetection(fileName)
				.then(results => {
					const detections = results[0].textAnnotations;
				            if(detections[0] == null)
				                return res.json({success:false});
				            else{
				                info = detections[0];
				                console.log('part1',info);
				                
				                return res.json(a);
				            }    
				        })
				        .catch(err => {
				            console.error('ERROR:', err);
				            return res.json({success:false});
				        });
		}  		
	});
});	
