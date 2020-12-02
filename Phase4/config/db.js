module.exports = function() {
    const {Client} = require('pg');

    const client = new Client({
        user: 'postgres',
        host: 'localhost',
        database: 'phase3',
        password: 'kwon0879',
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