import {createStore, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import {composeWithDevTools} from 'redux-devtools-extension';
import {persistStore, persistReducer} from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import autoMergeLevel2 from 'redux-persist/lib/stateReconciler/autoMergeLevel2';
import rootReducer from '../reducer/rootReducer';

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['pt'],
  blacklist: ['exercises', 'patients'],
  stateReconciler: autoMergeLevel2,
};
const initialState = {};

// redux-thunk middleware is required for actions to work as async
const middleware = [thunk];

const persistentReducer = persistReducer(persistConfig, rootReducer);

// Where all the data (or states) is gonna be stored
export const store = createStore(
  persistentReducer,
  initialState,
  composeWithDevTools(applyMiddleware(...middleware)),
);

export const persistor = persistStore(store);
