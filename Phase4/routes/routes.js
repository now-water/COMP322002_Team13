const e = require('express');
const url = require('url');
const conn = require('../config/db')();
const pgp = require('pg-promise')();
const db = pgp("postgres://postgres:kwon0879@localhost:5432/phase3");
module.exports = () => {
    var route = e.Router();
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

        var sql = 'SELECT * FROM \"knuMovie\".\"MOVIE\" ';

        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            res.render('index', {
                results: results.rows,
                session: `${req.session.user_id}`,
                ismanager: `${req.session.manager}`,
                isNewB: `${req.session.isNewB}`
            });
        });
    })
    route.get('/search', (req, res) => {
        if (!req.session.isLogined) {
            res.redirect('login');
        }
        var sql = 'SELECT * FROM \"knuMovie\".\"MOVIE\" ' +
            'WHERE title NOT IN (SELECT m_title AS title ' +
            'FROM \"knuMovie\".\"RATING\" ' +
            'WHERE account_id = \'' + req.session.user_id + "\') ";

        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            console.log(req.session);
            res.render('search', {
                results: results.rows,
                session: `${req.session.user_id}`,
                ismanager: `${req.session.manager}`,
                isNewB: `${req.session.isNewB}`
            });
        });
    });

    route.post('/search', (req, res) => {
        if (!req.session.isLogined) {
            res.redirect('login');
        }
        var sql = "SELECT * FROM \"knuMovie\".\"MOVIE\" AS M WHERE title IN (SELECT title FROM \"knuMovie\".\"MOVIE\" " +
            "WHERE title NOT IN (SELECT m_title AS title " +
            "FROM \"knuMovie\".\"RATING\" " +
            "WHERE account_id = \'" + req.session.user_id + "\')) ";
        if (req.body.title !== "")
            sql += "AND M.title = \'" + req.body.title + "\' ";

        if (req.body.type !== "")
            sql += "AND M.type = \'" + req.body.type + "\' ";

        if (req.body.runtime !== "") {
            sql += "AND M.runtime >= " + (req.body.runtime - 5) + " ";
            sql += "AND M.runtime <= " + parseInt(parseInt(req.body.runtime) + 5) + " ";
        }
        if (req.body.start_year !== "")
            sql += "AND extract(YEAR FROM start_year) = " + req.body.start_year + " ";

        if (req.body.genre !== "")
            sql += "AND genre = " + req.body.genre + " ";

        if (req.body.rating !== "")
            sql += "AND rating = " + req.body.rating + " ";

        if (req.body.viewing_class !== "")
            sql += "AND viewing_class = \'" + req.body.viewing_class + "\' ";

        if (req.body.uploader !== "")
            sql += "AND account_id = \'" + req.body.uploader + "\' ";
        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            console.log(req.session);
            res.render('search', {
                results: results.rows,
                session: `${req.session.user_id}`,
                ismanager: `${req.session.manager}`,
                isNewB: `${req.session.isNewB}`
            });
        })
    });

    route.post('/registerMovie', (req, res) => {
        //account_id는 고정된 값으로 있는 것을 읽어오기.
        let sql = "INSERT INTO \"knuMovie\".\"MOVIE\" (title, type, runtime, start_year, genre, rating, viewing_class, account_id) VALUES($1, $2, $3, $4, $5, $6, $7, $8) RETURNING title";
        let params = [req.body.title, req.body.type, req.body.runtime, req.body.start_year, req.body.genre, req.body.rating, req.body.viewing_class, req.session.user_id];
        db.tx(async t =>{ // automatic BEGIN
            // creating a sequence of transaction queries:
            await t.one(sql, params);

            // returning a promise that determines a successful transaction:
            //return t.batch([qeury1]);
        })
            .then(data => {
                console.log("Insertion COMMIT Complete !!");
                res.redirect('admin');
            })
            .catch(err => {
                console.log(err);
                console.log("Error happened. ROLLBACK execute");
                res.redirect('admin');
            })
    });

    route.get('/register', (req, res) => {
        res.render('signUp');
    })

    route.post('/modify', (req, res) => {
        req.session.title = req.body.title;
        console.log("REDIRECT TO MODIFY /GET");
        res.redirect('/modify');
    });

    route.get('/modify', (req, res) => {
        var sql = "SELECT * FROM \"knuMovie\".\"MOVIE\" WHERE title = " + "\'" + req.session.title + "\'";

        console.log(sql);
        conn.query(sql)
            .then(queryRes => {
                const rows = queryRes.rows;
                console.log(rows);
                if(rows.length === 0) {
                    res.send('<script type="text/javascript">window.location.href="http://localhost:3000/index";</script>');
                }
                rows.map(row => {
                    // console.log(`Read: ${JSON.stringify(row)}`);
                    if (row.account_id == req.session.user_id) {
                        res.render('modify', {
                            title: row.title,
                            type: row.type,
                            runtime: row.runtime,
                            start_year: row.start_year,
                            genre: row.genre,
                            rating: row.rating,
                            viewing_class: row.viewing_class,
                            isNewB: `${req.session.isNewB}`,
                            id: `${req.session.user_id}`,
                            ismanager: `${req.session.manager}`
                        });
                    } else {
                        res.send('<script type="text/javascript">alert("내가 등록한 영화정보가 아닙니다.");' +
                            'window.location.href="http://localhost:3000/admin";</script>');
                    }
                })
            })
            .catch(err => {
                console.log(err);
            });
    });

    route.post('/updateRegister', (req, res) => {
        var sql = "UPDATE \"knuMovie\".\"MOVIE\" " +
            "SET ";

        if (req.body.type.length != 0)
            sql += "type = " + "\'" + req.body.type + "\'" + " , ";

        if (req.body.runtime.length != 0)
            sql += "runtime = " + req.body.runtime + " , ";

        if (req.body.start_year.length != 0)
            sql += "start_year = " + "TO_DATE(" + "\'" + req.body.start_year + "\',\'" + "yyyy-mm-dd" + "\') , ";

        if (req.body.genre.length != 0)
            sql += "genre = " + req.body.genre + " , ";

        if (req.body.rating.length != 0)
            sql += "rating = " + req.body.rating;
        else
            sql += "rating = " + 0.0;

        if (req.body.viewing_class.length != 0)
            sql += " , viewing_class = " + req.body.viewing_class;

        sql += " WHERE title = " + "\'" + req.session.title + "\' " +
            "AND account_id = " + "\'" + req.session.user_id + "\'";

        console.log(sql);

        db.tx(async t =>{ // automatic BEGIN
            // creating a sequence of transaction queries:
            await t.none(sql);

            // returning a promise that determines a successful transaction:
        })
            .then(data => {
                console.log("Update COMMIT Complete !!");
                res.redirect('admin')
            })
            .catch(err => {
                console.log(err);
                console.log("Error happened. ROLLBACK execute");
                res.redirect('admin')
            })
    });

    route.post('/signUp', (req, res) => {
        req.body.age = parseInt("2020" - req.body.birth.substr(0, 4));
        req.body.manager = req.body.manager == 'on' ? "TRUE" : "FALSE";

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

        db.tx(async t =>{ // automatic BEGIN
            // creating a sequence of transaction queries:
            await t.none(sql);
            // returning a promise that determines a successful transaction:
        })
            .then(data => {
                console.log("Insertion COMMIT Complete !!");
                res.redirect('login');
            })
            .catch(err => {
                console.log(err);
                console.log("Error happened. ROLLBACK execute");
                res.redirect('login');
            })
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
        var sql = "SELECT * " +
            "FROM \"knuMovie\".\"ACCOUNT\" AS A " +
            "WHERE A.acc_id = " + "\'" + user_id + "\' ";
        console.log(sql);

        var success = false;
        // DB 처리
        var canLogin = false;

        conn.query(sql)
            .then(queryRes => {
                const rows = queryRes.rows;
                console.log(rows);
                if(rows.length === 0){
                    res.send('<script type="text/javascript">alert("로그인 정보가 없습니다.");' +
                        'window.location.href="http://localhost:3000/login";</script>');
                    console.log("로그인 실패");
                    res.reditect('login');
                }
                rows.map(row => {
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

                            // 신입 고객인지 확인
                            var sql = "SELECT COUNT(account_id) FROM \"knuMovie\".\"RATING\" WHERE account_id = " + "\'" + user_id + "\'";

                            console.log(sql);
                            conn.query(sql, (err, results) => {
                                if (err) {
                                    console.log(err);
                                    res.status(500).send("DB Error");
                                }
                                else{
                                    if(parseInt(results.rows[0].count) == 0) req.session.isNewB = true;
                                    else req.session.isNewB = false;
                                    res.redirect('index');
                                }
                            });
                            while(req.session.isNewB === "undefined");
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

    route.post('/withdrawal', (req, res) => {
        var id = req.body.id;
        var sql = "DELETE FROM \"knuMovie\".\"ACCOUNT\" WHERE acc_id = " + "\'" + id + "\'";

        db.tx(async t =>{ // automatic BEGIN
            // creating a sequence of transaction queries:
            await t.none(sql);

            // returning a promise that determines a successful transaction:
        })
            .then(data => {
                console.log("Delete COMMIT Complete !!");
                req.session.destroy();
                res.redirect('login');
            })
            .catch(err => {
                console.log(err);
                console.log("Error happened. ROLLBACK execute");
                res.redirect('login');
            })
    });

    route.get('/admin', (req, res) => {
        if(!req.session.isLogined) res.redirect("/login");
        res.render('admin', {
            id: `${req.session.user_id}`,
            manager: `${req.session.manager}`,
            isNewB: `${req.session.isNewB}`
        });
    });

    route.get('/form', (req, res) => {
        if (!req.session.isLogined) {
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
            manager: `${req.session.manager}`,
            isNewB: `${req.session.isNewB}`
        });
    })

    route.post('/updateForm', (req, res) => {
        var sql = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
            "SET phone_num = " + "\'" + req.body.phone_num + "\' , " +
            "address = " + "\'" + req.body.address + "\' , " +
            "job = " + "\'" + req.body.job + "\'" +
            "WHERE phone_num = " + "\'" + req.session.phone_num + "\' " +
            " OR address = " + "\'" + req.session.address + "\' " +
            " OR job = " + "\'" + req.session.job + "\'";

        db.tx(async t =>{ // automatic BEGIN
            // creating a sequence of transaction queries:
            await t.none(sql);

            // returning a promise that determines a successful transaction:
        })
            .then(data => {
                console.log("Update COMMIT Complete !!");
                req.session.phone_num = req.body.phone_num;
                req.session.address = req.body.address;
                req.session.job = req.body.job;
            })
            .catch(err => {
                console.log(err);
                console.log("Error happened. ROLLBACK execute");
            })
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
            manager: `${req.session.manager}`,
            isNewB: `${req.session.isNewB}`
        });
    })

    route.post('/rate', (req, res) => {
        var score = req.body.score;
        var title = req.body.title.replace(/"/gi, "");

        console.log(score);
        console.log(title);

        const id = req.session.user_id;
        // MAX num_vote
        const getMaxVoteQuery = "SELECT MAX(R.num_vote) AS num_vote FROM \"knuMovie\".\"RATING\" AS R " +
            "WHERE R.m_title = \'" + title + "\'";

        conn.query(getMaxVoteQuery, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            //console.log("totalScore 원래타입 : " + typeof results.rows[0].num_vote);
            //console.log("num_vote : " + results.rows[0].num_vote);
            var tatalScore = 0;
            var total_num_vote = 0;
            var updatedRating = 0;

            // rating score of a movie
            let getRatingScoreQuery = "SELECT m.rating AS rating FROM \"knuMovie\".\"MOVIE\" AS m " +
                "WHERE m.title = \'" + title + "\'";
            console.log(getRatingScoreQuery);
            conn.query(getRatingScoreQuery)
                .then(queryRes => {
                    const rows = queryRes.rows;
                    rows.map(row => {
                        console.log("row : " + row);
                        console.log("results : " + results);
                        tatalScore = results.rows[0].num_vote * row.rating;
                        var updatedRating = 10 * parseInt((parseFloat(results.rows[0].num_vote * row.rating) + parseInt(score)) / (parseInt(results.rows[0].num_vote) + parseInt(1))) / 10;
                        var updateRatingQuery = "UPDATE \"knuMovie\".\"MOVIE\" " +
                            "SET RATING = " + updatedRating +
                            " WHERE title = \'" + title + "\'";

                        db.tx(async t =>{ // automatic BEGIN
                            // creating a sequence of transaction queries:
                            await t.none(updateRatingQuery);

                            // returning a promise that determines a successful transaction:
                        })
                            .then(data => {
                                console.log("Update COMMIT Complete !!");
                            })
                            .catch(err => {
                                console.log(err);
                                console.log("Error happened. ROLLBACK execute");
                            })

                        var insertQuery = "INSERT INTO \"knuMovie\".\"RATING\" " +
                            "VALUES(\'" + title + "\', \'" + id + "\', " + parseInt(parseInt(results.rows[0].num_vote) + parseInt(1)) + ") ";
                        console.log(insertQuery);
                        db.tx(async t =>{ // automatic BEGIN
                            // creating a sequence of transaction queries:
                            await t.none(insertQuery);

                            // returning a promise that determines a successful transaction:
                        })
                            .then(data => {
                                console.log("Insertion COMMIT Complete !!");
                                req.session.isNewB = false;
                                //res.send('<script>window.location.reload();</script>');
                                res.redirect('rate');
                            })
                            .catch(err => {
                                console.log(err);
                                console.log("Error happened. ROLLBACK execute");
                                //res.send('<script>window.location.reload();</script>');
                                res.redirect('rate');
                            })
                    });
                })
                .catch(err => {
                    console.log(err);
                })
        });
    });

    route.get('/rated', (req, res) => {
        if (!req.session.isLogined) {
            res.redirect('/login');
        }
        var sql = "SELECT * FROM \"knuMovie\".\"MOVIE\" M " +
            "WHERE M.title IN (SELECT m_title FROM \"knuMovie\".\"RATING\" " +
            "WHERE account_id = \'" + req.session.user_id + "\') ";

        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            res.render('rated', {
                results: results.rows,
                session: `${req.session.user_id}`,
                ismanager: `${req.session.manager}`,
                isNewB: `${req.session.isNewB}`
            });
        });
    });

    route.get('/recommend', (req, res) => {
        if(!req.session.isLogined) {
            res.redirect('http://localhost:3000/login');
        }
        var urlParse = url.parse(req.url, true);
        var queryString = urlParse.query;
        let genreNumber = queryString.genre;
        let genreName="";
        if(genreNumber == '1') genreName = "Action";
        else if(genreNumber == '2') genreName = "Sf";
        else if(genreNumber == '3') genreName = "Comedy";
        else if(genreNumber == '4') genreName = "Thriller";
        else if(genreNumber == '5') genreName = "Romance";
        let sql = "select * from \"knuMovie\".\"MOVIE\" where genre = " + genreNumber + " order by rating DESC ";

        conn.query(sql, (err, results) => {
            if (err) {
                console.log(err);
                res.status(500).send("DB Error");
            }
            res.render('recommend', {
                results: results.rows,
                session: `${req.session.user_id}`,
                ismanager: `${req.session.manager}`,
                isNewB: `${req.session.isNewB}`,
                genre: genreName
            });
        });
    });

    return route;
}
