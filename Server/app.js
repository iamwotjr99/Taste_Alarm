const express = require('express');
const morgan = require('morgan');

const app = express();

app.use(morgan('dev'));
app.use(require('./router'));

app.listen(8000, () => {
    console.log("Sever listening on port 3000...");
})