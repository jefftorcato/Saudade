  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyDjLQZNX4ZIRKKW2doEEihSNOeWYuCI5Pk",
    authDomain: "saudade-ab518.firebaseapp.com",
    databaseURL: "https://saudade-ab518.firebaseio.com",
    projectId: "saudade-ab518",
    storageBucket: "saudade-ab518.appspot.com",
    messagingSenderId: "501133640393",
    appId: "1:501133640393:web:dc36ce92ad78a238ca4318",
    measurementId: "G-7C8CMWSV9T"
  };

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();
  var db = firebase.firestore();
  var storageRef = firebase.storage().ref();