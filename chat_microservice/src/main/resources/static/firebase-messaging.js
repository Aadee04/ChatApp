// Import Firebase modules (v10+)
import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-app.js";
import { getMessaging, getToken, onMessage } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging.js";

// 🔥 Firebase Config
const firebaseConfig = {
  apiKey: "AIzaSyAkQNl7KALHG_OlDwIqof7EmBVFhwqdHcM",
  authDomain: "chatapp-notifs.firebaseapp.com",
  projectId: "chatapp-notifs",
  storageBucket: "chatapp-notifs.appspot.com",
  messagingSenderId: "1009216658066",
  appId: "1:1009216658066:web:de9dcdeec700230973644b",
  measurementId: "G-JEJTMQF8S7"
};

// 🔹 Initialize Firebase
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

// 🔹 Register Service Worker for FCM
if ("serviceWorker" in navigator) {
  navigator.serviceWorker
    .register("js/firebase-messaging.js")
    .then((registration) => {
      console.log("✅ Service Worker registered:", registration);
    })
    .catch((error) => {
      console.error("❌ Service Worker registration failed:", error);
    });
} else {
  console.error("❌ Service Workers are not supported in this browser.");
}

// ✅ Function to Request Notification Permission
window.requestNotificationPermission = function (userId) {
  Notification.requestPermission().then((permission) => {
    if (permission === "granted") {
      console.log("✅ Notification permission granted.");

      // Get FCM token
      getToken(messaging, {
        vapidKey: "BKswYU3Z4Vsi-ZjkcmoENfhbYTEE4zvS0Cm9QGgEy7Fe7_ZKf_kThkoimFnF4EroX2xYNQijsz5bvSqeKu6nURA"
      }).then((currentToken) => {
        if (currentToken) {
          console.log("🔹 FCM Token:", currentToken);

          // 🔄 Send token to your NOTIFICATIONS microservice backend
          fetch("http://localhost:8082/chat/notifications/register-token", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              userId: userId,
              fcmToken: currentToken,
            }),
          })
            .then((response) => {
              if (response.ok) {
                console.log("✅ Token registered on backend");
              } else {
                console.error("❌ Backend failed to register token");
              }
            })
            .catch((error) => {
              console.error("❌ Error sending token to backend:", error);
            });

        } else {
          console.log("❌ No registration token available.");
        }
      }).catch((err) => {
        console.error("❌ Error getting FCM token:", err);
      });

    } else {
      console.log("❌ Notification permission denied.");
    }
  });
};

// ✅ Handle Foreground Incoming Messages
onMessage(messaging, (payload) => {
  console.log("📩 Message received:", payload);
  new Notification(payload.notification.title, {
    body: payload.notification.body
  });
});
