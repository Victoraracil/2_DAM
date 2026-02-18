const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({

    login: {
        type: String,
        required: true,
        unique: true,
        trim: true
    },

    password: {
        type: String,
        required: true
    }

}, {
    versionKey: false
});

module.exports = mongoose.model('User', userSchema);
