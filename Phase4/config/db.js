// const pgp = require('pg-promise')();
// const db = pgp("postgres://postgres:kwon0879@localhost:5432/phase3");
// module.exports = db;
module.exports = function() {
    const {Client} = require('pg');

    const client = new Client({
        user: 'postgres',
        host: 'localhost',
        database: 'phase2',
        password: 'dlwjdduf1!',
        port: 5432,
    });

    client.connect(err => {
        if(err){
            console.error('connection error', err.stack);
        } else{
            console.log('DB Connection Succeed!')
        }
    });
    return client;
}