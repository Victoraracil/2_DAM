const mongoose = require('mongoose');
const express = require('express');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

mongoose.connect('mongodb://127.0.0.1:27017/contacts');

let app = express();

app.get('/test', (req, res) => {
  res.send('Hello World');
});

let contactSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    minlength: 1,
    trim: true
  },
  telephone: {
    type: String,
    required: true,
    trim: true,
    match: /^\d{9}$/
  },
  age: {
    type: Number,
    min: 18,
    max: 120
  }
});

let Contact = mongoose.model('contacts', contactSchema);

let contact1 = new Contact({
    name: 'Nacho',
    telephone: '123456789',
    age: '30'
});
contact1.save()
  .then((result) => {
    console.log('Contact saved', result);
  })
  .catch((err) => {
    console.log('Error saving contact', err);
  });

  app.listen(8080);