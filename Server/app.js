const express = require('express');
const morgan = require('morgan');

const app = express();

app.use(morgan('dev'));
app.use(require('./router'));
app.use('/restaurant',express.static('restaurant'));
app.use('/menu',express.static('menu'));
app.use('/reviewimages',express.static('reviewimages'));

app.listen(8000, () => {
    console.log("Sever listening on port 8000...");
})