const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.plants = functions.https.onRequest(async (req, res) => {
    let body = req.body;
    if (!body['long'] || !body['lat']) {
        res.status(400).json({message: "Specifiy lat and long"}).end();
        return;
    }
    let x = body['long'] ? parseFloat(body['long']) : 0.1;
    let y = body['lat'] ? parseFloat(body['lat']) : 0.1;
    let data = await admin.database().ref().once('value');
    data = data.val();
    let nearbyPlants = [];
    if (data) {
        Object.keys(data).forEach((plant) => {
            console.log(plant);
            let firstL = {};
            firstL.lat = 110.1;
            firstL.long = 110.1;
            let secondL = {};
            secondL.lat = 110.1;
            secondL.long = 110.1;
            let locations = data[plant]['locatii'];
            Object.values(locations).forEach((locatie) => {
                if (((parseFloat(locatie.lat) - y) * (parseFloat(locatie.lat) - y) + (parseFloat(locatie.long) - x) * (parseFloat(locatie.long) - x)) < ((parseFloat(firstL.lat) - y) * (parseFloat(firstL.lat) - y) + (parseFloat(firstL.long) - x) * (parseFloat(firstL.long) - x))) {
                    firstL.lat = parseFloat(locatie.lat);
                    firstL.long = parseFloat(locatie.long);
                } else if (((parseFloat(locatie.lat) - y) * (parseFloat(locatie.lat) - y) + (parseFloat(locatie.long) - x) * (parseFloat(locatie.long) - x)) < ((parseFloat(secondL.lat) - y) * (parseFloat(secondL.lat) - y) + (parseFloat(secondL.long) - x) * (parseFloat(secondL.long) - x))) {
                    secondL.lat = parseFloat(locatie.lat);
                    secondL.long = parseFloat(locatie.long);
                }
            });
            console.log(`FirstL : ${firstL.lat} ${firstL.long}`);
            console.log(`SecondL : ${secondL.lat} ${secondL.long}`);
            console.log(Math.abs((parseFloat(secondL.lat)
                - parseFloat(firstL.lat))
                * x - (parseFloat(secondL.long)
                    - parseFloat(firstL.long))
                * y + parseFloat(secondL.long)
                * parseFloat(firstL.lat) - parseFloat(secondL.lat)
                * parseFloat(firstL.long)));
            console.log(Math.sqrt((parseFloat(secondL.lat) - parseFloat(firstL.lat)) * (parseFloat(secondL.lat) - parseFloat(firstL.lat)) + (parseFloat(secondL.long) - parseFloat(firstL.long)) * (parseFloat(secondL.long) - parseFloat(firstL.long))))
            if (Math.abs((parseFloat(secondL.lat) - parseFloat(firstL.lat)) * x - (parseFloat(secondL.long) - parseFloat(firstL.long)) * y + parseFloat(secondL.long) * parseFloat(firstL.lat) - parseFloat(secondL.lat) * parseFloat(firstL.long)) / Math.sqrt((parseFloat(secondL.lat) - parseFloat(firstL.lat)) * (parseFloat(secondL.lat) - parseFloat(firstL.lat)) + (parseFloat(secondL.long) - parseFloat(firstL.long)) * (parseFloat(secondL.long) - parseFloat(firstL.long))) < (parseFloat(data[plant]['range']) * 0.0000089)) {
                nearbyPlants.push(plant);
                console.log("First shit");
            } else if ((((parseFloat(Object.values(locations)[0].lat) - y) * (parseFloat(Object.values(locations)[0].lat) - y) + (parseFloat(Object.values(locations)[0].long) - x) * (parseFloat(Object.values(locations)[0].long) - x))) < ((parseFloat(firstL.lat) - y) * (parseFloat(firstL.lat) - y + (parseFloat(firstL.long) - x) * (parseFloat(firstL.long) - x)))) {
                nearbyPlants.push(plant);
                console.log("Second shit");
            }
        });
    }
    res.send(nearbyPlants)
});
