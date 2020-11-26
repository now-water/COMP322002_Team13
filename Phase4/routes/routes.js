const e = require('express');
const session = require('express-session');

module.exports = function () {
    var route = e.Router();
    var conn = require('../config/db')();
    var bodyParser = require('body-parser');
    const { response } = require("express");
    route.use(bodyParser.urlencoded({ extended: true }));
    var user = {

    }
    route.get('/', (req, res) => {
        res.redirect('/login');
    })
    route.get('/index', (req, res) => {
        var sql = 'SELECT * FROM \"knuMovie\".\"MOVIE\"';
        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            res.render('index', { results: results.rows });
        });
    })
    route.get('/register', (req, res) => {
        res.render("signUp");
    })
    route.get('/login', (req, res) => {
        res.render("login");
    })
    route.post('/login', (req, res) => {
        var user_id = req.body.user_id;
        var user_pw = req.body.user_pw;
        console.log(user_id + " " + user_pw);

        // DB 처리
        //req.session.user_id = user_id;
        //console.log(req.session);
        res.render("index");
    })
    route.get('/form', (req, res) => {
        res.render("form");
    })

    return route;
}