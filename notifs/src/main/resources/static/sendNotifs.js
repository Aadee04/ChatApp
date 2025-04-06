const admin = require("firebase-admin");

// Load service account
const serviceAccount = require("/json/service-account-file.json"); // Go one level up

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const message = {
  notification: {
    title: "Hello!",
    body: "This is a test notification",
  },
  token: "diYRnn_3VCifiJucwPt5ha:APA91bFx5FSvwshkXRgxlfsfQwgKPp6Kj08Hm7Sb2CUJd3td95x5TPZYFwhMzXhRygq3wN6nBdP1AFblZNXtEUgvcoDWsB0M41qFudMLhb1TCj0bXotBdF8", // Replace with actual FCM token
};

admin.messaging().send(message)
  .then(response => {
    console.log("Successfully sent message:", response);
  })
  .catch(error => {
    console.error("Error sending message:", error);
  });
