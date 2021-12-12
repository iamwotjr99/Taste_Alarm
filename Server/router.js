const express = require('express');
const router = express.Router();
const dbPool = require('./dbconfig');
const fs = require('fs');
const multer = require('multer');
const path = require('path');

let i = 0;
let j = 0;
const upload = multer({
    storage: multer.diskStorage({
      destination: function (req, file, cb) {
        cb(null, 'SERVER/reviewimages/');
      },
      filename: function (req, file, cb) {
        cb(null, ++i+"_"+file.originalname);
      }
    }),
  });

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

// 리뷰 이미지와 함께 리뷰 저장
router.post('/post/uploadImage/:userID/res/:resName/:content/:nickname', upload.single('file'), (req, res) => {
    let userID = req.params.userID;
    let resName = req.params.resName;
    let content = req.params.content;
    let nickname = req.params.nickname;
    let url = ""

    let file = req.file;
    let originalName = "";
    let fileName = "";
    let mimeType = "";
    let size = 0;
    
    if(file) {
        originalName = file.originalname;
        fileName = file.filename;
        mimeType = file.mimetype;
        size = file.size;
        console.log("execute" + fileName);
    } else {
        console.log("Request is null");
    }

    url = "http://ec2-15-164-230-128.ap-northeast-2.compute.amazonaws.com:8000/reviewimages/" + ++j+"_"+file.originalname;
    console.log(req.file);
    console.log(req.body);
    console.log(url);

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }

        let sql = "INSERT INTO review (user_id, userName, title, content, picture) VALUES(?, ?, ?, ?, ?);"
        connection.query(sql, [userID, nickname, resName, content, url], (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            console.log("Post Image review success!");
            connection.release();
        })
    })
})

// 이미지 없는 리뷰 추가하기
router.post('/post/uploadReview/:userID/res/:resName/:content/:nickname', (req, res) => {
    let userID = req.params.userID;
    let resName = req.params.resName;
    let content = req.params.content;
    let nickname = req.params.nickname;

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log('error');
            return err;
        }

        let sql = "INSERT INTO review (user_id, userName, title, content) VALUES(?, ?, ?, ?);"
        connection.query(sql, [userID, nickname, resName, content], (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            console.log("Post No Image review success!");
            connection.release();
        })
    })
})

// 리뷰 가져오기
router.get('/get/reviewList/:reviewList', (req, res) => {
    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log("error");
            return err;
        }

        let sql = "SELECT * FROM review order by id desc"

        connection.query(sql, (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            console.log("Get review Success!");
            res.send(result);
            connection.release();
        })
    })
})

// 유저 id에 맞는 리뷰 가져오기
router.get('/get/userInfo/reviews/:userID', (req,res) => {
    let userID = req.params.userID;
    console.log(userID);

    dbPool.getConnection((err, connection) => {
        if(err) {
            err.code = 500;
            console.log('error');
            return err;
        }

        let sql = "SELECT * from review WHERE user_id = ?"
        connection.query(sql, userID, (err, result) => {
            if(err) {
                err.code = 500;
                connection.release();
                return err;
            }

            console.log(result);
            res.send(result);
            console.log("Get userID review success!");
            connection.release();
        })
    })
})

router.get('/taste', (req, res) => {
    res.send("hello world!");
})

module.exports = router;
