const express = require('express');
const router = express.Router();
const dbPool = require('./dbconfig');

// 아이디 중복 확인
router.get('/get/join/checkid/:userId', (req, res) => {
    let userId = req.params.userId;

    console.log(userId);

    dbPool.getConnection((err, connection) =>{
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }
        let sql = 'SELECT userID FROM user WHERE userID=? '

        connection.query(sql, userId, (err, result) => {
            if(err) {
                err.code = 500;
                console.log("error");
                conn.release();
                return err;
            }

            connection.release();
            console.log(result[0]);
            res.send(result[0]);
            console.log('Check!');
        })
    })
})

// 회원 가입
router.post('/post/join/:nickname/:userId/:userPW', (req, res) => {
    let nickname = req.params.nickname;
    let userId = req.params.userId;
    let userPW = req.params.userPW;

    console.log(nickname, userId, userPW);

    dbPool.getConnection((err, connection) => {

        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }
        let sql = 'INSERT INTO user(nickname, userID, password) VALUES (?, ?, ?)'

        connection.query(sql, [nickname, userId, userPW], (err, result) =>{
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }
            console.log(result);
            connection.release();
            console.log("User join Success!");
        })
    })
})

// 로그인
router.get('/get/login/:userId/:userPW', (req, res) => {
    let userId = req.params.userId;
    let userPW = req.params.userPW;

    console.log(userId, userPW);

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }

        let sql = 'SELECT * FROM user WHERE userID = ? AND password = ?'
        connection.query(sql, [userId, userPW], (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result[0]);
            res.send(result[0]);
            console.log("User Login Success!");
        })
    })
})

router.get('/taste', (req, res) => {
    res.send("hello world!");
})

module.exports = router;
