const express = require('express');
const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

const Task = require('./models/task');
const User = require('./models/user');

const app = express();
const SECRET_KEY = "superSecretWord";

app.use(express.json());


//DATABASE CONNECTION


mongoose.connect('mongodb://127.0.0.1:27017/todolist')
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.error('MongoDB connection error:', err));



//TOKEN MIDDLEWARE


const checkToken = (req, res, next) => {
    let token = req.headers['authorization'];

    if (token && token.startsWith('Bearer ')) {
        token = token.slice(7);

        jwt.verify(token, SECRET_KEY, (err, decoded) => {
            if (err) {
                return res.status(401).json({
                    ok: false,
                    error: "Invalid or expired token"
                });
            }
            req.user = decoded;
            next();
        });

    } else {
        return res.status(401).json({
            ok: false,
            error: "Missing token"
        });
    }
};


// USER SERVICES


// REGISTER
app.post('/users', async (req, res) => {
    try {
        const { login, password } = req.body;

        if (!login || !password) {
            return res.status(400).json({
                ok: false,
                error: "Login and password are required"
            });
        }

        const existingUser = await User.findOne({ login });
        if (existingUser) {
            return res.status(400).json({
                ok: false,
                error: "User already exists"
            });
        }

        const hashedPassword = bcrypt.hashSync(password, 10);

        const newUser = new User({
            login,
            password: hashedPassword
        });

        const result = await newUser.save();

        res.status(201).json({
            ok: true,
            result
        });

    } catch (err) {
        res.status(500).json({
            ok: false,
            error: "Server error"
        });
    }
});


// LOGIN
app.post('/login', async (req, res) => {
    try {
        const { login, password } = req.body;

        const user = await User.findOne({ login });

        if (!user || !bcrypt.compareSync(password, user.password)) {
            return res.status(401).json({
                ok: false,
                error: "Invalid credentials"
            });
        }

        const token = jwt.sign(
            { id: user._id, login: user.login },
            SECRET_KEY,
            { expiresIn: "2h" }
        );

        res.status(200).json({
            ok: true,
            result: token
        });

    } catch (err) {
        res.status(500).json({
            ok: false,
            error: "Server error"
        });
    }
});


// TASK SERVICES (PROTECTED)
 
// GET ALL TASKS
app.get('/tasks', checkToken, async (req, res) => {
    try {
        const tasks = await Task.find();
        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(500).json({ ok: false, error: "Server error" });
    }
});

// GET BY TYPE
app.get('/tasks/type/:type', checkToken, async (req, res) => {
    try {
        const tasks = await Task.find({ type: req.params.type });
        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid type" });
    }
});

// GET BY PRIORITY
app.get('/tasks/priority/:priority', checkToken, async (req, res) => {
    try {
        const tasks = await Task.find({ priority: req.params.priority });
        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid priority" });
    }
});

// GET BY DONE
app.get('/tasks/done/:done', checkToken, async (req, res) => {
    try {
        const doneValue = req.params.done === "true";
        const tasks = await Task.find({ done: doneValue });
        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid done value" });
    }
});

// GET BY DIFFICULTY (min included, descending)
app.get('/tasks/difficulty/:difficulty', checkToken, async (req, res) => {
    try {
        const tasks = await Task.find({
            difficulty: { $gte: req.params.difficulty }
        }).sort({ difficulty: -1 });

        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid difficulty" });
    }
});

// GET BY DATE (limitDate improvement)
app.get('/tasks/date', checkToken, async (req, res) => {
    try {
        const today = new Date();

        const tasks = await Task.find({
            $or: [
                { limitDate: null },
                { limitDate: { $gte: today } }
            ]
        }).sort({ limitDate: 1 });

        res.status(200).json({ ok: true, result: tasks });
    } catch (err) {
        res.status(500).json({ ok: false, error: "Server error" });
    }
});


// CREATE TASK
app.post('/tasks', checkToken, async (req, res) => {
    try {
        const newTask = new Task(req.body);
        const result = await newTask.save();

        res.status(201).json({ ok: true, result });
    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid task data" });
    }
});


// UPDATE TASK
app.put('/tasks/:id', checkToken, async (req, res) => {
    try {
        const updated = await Task.findByIdAndUpdate(
            req.params.id,
            req.body,
            { new: true }
        );

        if (!updated) {
            return res.status(404).json({
                ok: false,
                error: "Task not found"
            });
        }

        res.status(200).json({ ok: true, result: updated });

    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid ID" });
    }
});


// DELETE TASK
app.delete('/tasks/:id', checkToken, async (req, res) => {
    try {
        const deleted = await Task.findByIdAndDelete(req.params.id);

        if (!deleted) {
            return res.status(404).json({
                ok: false,
                error: "Task not found"
            });
        }

        res.status(200).json({ ok: true, result: deleted });

    } catch (err) {
        res.status(400).json({ ok: false, error: "Invalid ID" });
    }
});


app.listen(3000, () => {
    console.log('Server running on port 3000');
});
