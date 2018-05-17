const vision = require('@google-cloud/vision');

//Creates a client
const client = new vision.ImageAnnotatorClient();

//*TODO(developer): Uncomment the following line before running the sample.

const fileName = '/home/miin/DreamStart/2018-Piccount/server/test/sample.jpg';

// Performs text detection on the local file
client
	.textDetection(fileName)
	.then(results => {
		const detections = results[0].textAnnotations;
		console.log('Text:');
		detections.forEach(text => console.log(text));
	})
	.catch(err => {
		console.error('ERROR:', err);
});
