var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.set('views', './views');
app.set('view engine', 'ejs');
app.use(express.static(__dirname));

var movie_main = require('./routes/routes')();


app.use(movie_main);

app.listen(3000, function () {
    console.log('Connected 3000 ! ')
});
