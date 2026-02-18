const mongoose = require('mongoose');

const taskSchema = new mongoose.Schema({

    description: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },

    type: {
        type: String,
        enum: ['home', 'work', 'family', 'sport', 'undefined'],
        required: true
    },

    priority: {
        type: Number,
        required: true,
        min: 1,
        max: 5
    },

    done: {
        type: Boolean,
        default: false
    },

    difficulty: {
        type: Number,
        required: true,
        min: 0,
        max: 10
    },

    limitDate: {
        type: Date,
        required: false
    }

}, {
});

module.exports = mongoose.model('Task', taskSchema);
