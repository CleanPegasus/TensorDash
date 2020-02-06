const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// Listens for new messages added to messages/:pushId
exports.pushNotification = functions.database.ref('/notification/{pushId}').onWrite( (change, context) => {

    console.log('Push notification event triggered');

    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = change.after.val();

    console.log(valueObject.Key);
    console.log(valueObject.Status);

  // Create a notification
    const payload = {
        notification: {
            title:"Model Status",
            body: "Your Model is " + valueObject.Status,
            sound: "default"
        },
    };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };

    var topic = valueObject.Key;
    // topic = String(topic);
    console.log(topic);

    return admin.messaging().sendToTopic(topic, payload, options);
});


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
