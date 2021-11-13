const mysql = require('mysql');

const dbInfo = {
    host : 'raotalk.cn4z0cetscin.ap-northeast-2.rds.amazonaws.com',
    user : 'fisixeven',
    password : 'j1y35670',
    port : 3306,
    database : 'TESTDB'
}

const dbPool = mysql.createPool(dbInfo);
module.exports = dbPool;