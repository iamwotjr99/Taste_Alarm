const express = require('express');
const router = express.Router();
const dbPool = require('./dbconfig');

// 아이디 중복 확인
router.get('/get/join/checkid/:userId', (req, res) => {
    let userId = req.params.id;

    console.log(userId);
})

// 회원 가입
router.post('/post/join', (req, res) => {
    let nickname = req.body.nickname;
    let userId = req.body.userId;
    let userPW = req.body.userPW;

    console.log(nickname, userId, userPW);
})

// 로그인
router.get('/get/login/user', (req, res) => {
    let userId = req.body.userId;
    let userPW = req.body.userPW;
    console.log(userId, userPW);
})

router.get('/taste', (req, res) => {
    res.send("hello world!");
})

module.exports = router;