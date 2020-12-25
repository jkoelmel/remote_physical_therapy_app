import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import axios from 'axios';
import {PersistGate} from 'redux-persist/lib/integration/react';

import {Provider} from 'react-redux';
import * as serviceWorker from './serviceWorker';
import App from './App';
import {store, persistor} from './Redux/store/store';

axios.defaults.baseURL = 'https://api.pthealth.club/';
axios.defaults.headers = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Methods': 'OPTIONS, GET, POST, PUT, PATCH, DELETE',
  'Access-Control-Allow-Headers': 'Content-Type, Authorization',
};
// Provider makes the store available to every component under App
ReactDOM.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <App />
    </PersistGate>
  </Provider>,
  document.getElementById('root'),
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
