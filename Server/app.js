const express = require('express');
const morgan = require('morgan');

const app = express();

app.use(morgan('dev'));
app.use(require('./retrofit'));

app.listen(3000, () => {
    console.log("Sever listening on port 3000...");
})