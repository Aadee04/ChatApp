// ✅ Use Firebase v8-compatible service worker
importScripts("https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js");

// 🔥 Firebase Config (must match the config in firebase-messaging.js)
firebase.initializeApp({
  apiKey: "AIzaSyAkQNl7KALHG_OlDwIqof7EmBVFhwqdHcM",
  authDomain: "chatapp-notifs.firebaseapp.com",
  projectId: "chatapp-notifs",
  storageBucket: "chatapp-notifs.firebasestorage.app",
  messagingSenderId: "1009216658066",
  appId: "1:1009216658066:web:de9dcdeec700230973644b",
  measurementId: "G-JEJTMQF8S7"
});

// 🔹 Get messaging instance
const messaging = firebase.messaging();

// ✅ Handle background push notifications
messaging.onBackgroundMessage((payload) => {
  console.log("📩 Received background message:", payload);

  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    icon: "logo/chatapp_logo.png" // Change this if you have a different notification icon
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
