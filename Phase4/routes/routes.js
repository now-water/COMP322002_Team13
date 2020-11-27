const e = require('express');

module.exports = function () {
    var route = e.Router();
    var conn = require('../config/db')();
    var bodyParser = require('body-parser');
    const {response} = require("express");
    route.use(bodyParser.urlencoded({extended: true}));

    route.get('/', (req, res) => {
        res.redirect('/login');
    })
    route.get('/index', (req, res) => {
        if (!req.session.isLogined) {
            res.redirect('login');
        }

        var sql = 'SELECT * FROM \"knuMovie\".\"MOVIE\" ' +
        'WHERE title NOT IN (SELECT m_title AS title ' +
        'FROM \"knuMovie\".\"RATING\" '+
        'WHERE account_id = \'' + `${req.session.user_id}` + "\') ";
        console.log(sql);

        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            console.log(req.session);
            //console.log(`${req.session.num}`);
            //console.log(`${req.session.user_id}`);
            res.render('index', {
                results: results.rows,
                session: `${req.session.user_id}`
            });
        });
    })
    route.get('/register', (req, res) => {
        res.render('signUp');
    })

    route.post('/signUp', (req, res) => {
        req.body.age = parseInt("2020" - req.body.birth.substr(0, 4));
        req.body.manager = req.body.manager == 'on'?"TRUE":"FALSE";

        var sql = "INSERT INTO \"knuMovie\".\"ACCOUNT\" (acc_id, acc_pw, user_name, phone_num, birth_date, age, gender, address,job ,mem_type, manager) VALUES(" +
            "\'" + req.body.user_id + "\'" + "," +
            "\'" + req.body.pw + "\'" + "," +
            "\'" + req.body.name + "\'" + "," +
            "\'" + req.body.phone_num + "\'" + "," +
            "TO_DATE(" + "\'" + req.body.birth + "\'" + ", \'yyyy-mm-dd\')" + "," +
            req.body.age + "," +
            "\'" + req.body.gender + "\'" + "," +
            "\'" + req.body.address + "\'" + "," +
            "\'" + req.body.job + "\'" + "," +
            "\'" + req.body.mem_type + "\'" + "," +
            req.body.manager + ")";

        conn.query(sql, (err) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            res.redirect('login');
        });
    })

    route.get('/login', (req, res) => {
        // 한 클라이언트는 중복 로그인이 불가능하다.
        if (req.session.isLogined)
            res.redirect('index');

        if (!req.session.num) {
            req.session.num = 1;
        } else {
            req.session.num = req.session.num + 1;
        }
        res.render('login');
    })

    route.post('/login', (req, res) => {
        var user_id = req.body.user_id;
        var user_pw = req.body.user_pw;
        // console.log(user_id + " " + user_pw);
        var sql = "SELECT * " +
            "FROM \"knuMovie\".\"ACCOUNT\" AS A " +
            "WHERE A.acc_id = " + "\'" + user_id + "\' ";
        console.log(sql);

        var success = false;
        // DB 처리
        var canLogin = false;

        //sql결과가 아무것도 안나오면.. 안넘어간다. -> undefined로 비교해줘야하나?
        conn.query(sql)
            .then(queryRes => {
                const rows = queryRes.rows;
                console.log(rows);

                rows.map(row => {
                    // console.log(`Read: ${JSON.stringify(row)}`);
                    if (user_id == row.acc_id) {
                        if (user_pw == row.acc_pw) {
                            // 유저의 모든 정보 저장하기
                            req.session.user_id = row.acc_id;
                            req.session.pw = row.acc_pw;
                            req.session.name = row.user_name;
                            req.session.phone_num = row.phone_num;
                            req.session.birth = row.birth_date;
                            req.session.age = row.age;
                            req.session.gender = row.gender;
                            req.session.address = row.address;
                            req.session.job = row.job;
                            req.session.mem_type = row.mem_type;
                            req.session.manager = row.manager;
                            req.session.isLogined = true;
                            //console.log("Session test : ");
                            //console.log(req.session);

                            res.redirect('index');
                        } else {
                            res.send('<script type="text/javascript">alert("로그인 정보가 없습니다.");' +
                                'window.location.href="http://localhost:3000/login";</script>');
                            console.log("로그인 실패");
                        }
                    } else {
                        res.send('<script type="text/javascript">alert("로그인 정보가 없습니다.");' +
                            'window.location.href="http://localhost:3000/login";</script>');
                        console.log("로그인 실패");
                    }
                })
            })
            .catch(err => {
                console.log(err);
            });
    })

    route.get('/logout', (req, res) => {
        req.session.destroy();
        res.redirect('login');
    })

    route.get('/form', (req, res) => {

        // console.log(`${req.session}`);
        if(!req.session.isLogined){
            res.redirect('/login');
        }
        res.render("form", {
            id: `${req.session.user_id}`,
            pw: `${req.session.pw}`,
            name: `${req.session.name}`,
            phone_num: `${req.session.phone_num}`,
            birth: `${req.session.birth}`,
            age: `${req.session.age}`,
            gender: `${req.session.gender}`,
            address: `${req.session.address}`,
            job: `${req.session.job}`,
            mem_type: `${req.session.mem_type}`,
            manager: `${req.session.manager}`
        });
    })

    return route;
}