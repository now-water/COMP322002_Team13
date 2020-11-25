const e = require('express');

module.exports = function () {
    var route = e.Router();
    var conn = require('../config/db')();
    var bodyParser = require('body-parser');
    const { response } = require("express");
    route.use(bodyParser.urlencoded({ extended: true }));

    route.get('/', (req, res) => {
        res.render("index");
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
    route.get('/form', (req, res) => {
        res.render("form");
    })

    return route;
}