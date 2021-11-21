const express = require('express');
const router = express.Router();
const dbPool = require('./dbconfig');
const fs = require('fs');

// let resData = [];

// fs.readdir('./restaurant', (err, data) => {
//     for(let i = 0; i < data.length; i++) {
//         resData[i] = "http://ec2-15-164-230-128.ap-northeast-2.compute.amazonaws.com:8000/restaurant/" + data[i];
//     }
//     //console.log(resData);
// })

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

// 맛집 리스트 가져오기
router.get('/get/restaurantList/:resList', (req, res) => {
    console.log(req.params.resList);

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }

        let sql = 'SELECT * FROM famous_restaurant'
        connection.query(sql, (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            res.send(result);
            connection.release();
            console.log("Get ResList Success!");
        })
    })
})

// 맛집에 맞는 메뉴 가져오기
router.get('/get/restaurantMenu/:id', (req, res) => {
    let resID = req.params.id;
    console.log(resID);

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }

        let sql = 'SELECT * FROM restaurant_menu WHERE id=?'
        connection.query(sql, resID, (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            res.send(result);
            connection.release();
            console.log("GET MenuList Success!");
        })
    })
})

router.get('/taste', (req, res) => {
    res.send("hello world!");
})

module.exports = router;
