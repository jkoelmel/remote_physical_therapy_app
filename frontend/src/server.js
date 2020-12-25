const express = require('express');
const cors = require('cors');

const app = express();

// var whitelist = []; TODO test specific routes later to avoid /* CORS

const corsOptions = {
  origin: true,
  credentials: true,
};

app.use(cors(corsOptions));

app.use(express.static('C:/Users/jaret/CSC648-Team-2/frontend/build'));

app.get('/', (req, res) => {
  res.sendFile('C:/Users/jaret/CSC648-Team-2/frontend/build/index.html');
});

app.get('/api/*');
app.listen(process.env.PORT || 3000);
