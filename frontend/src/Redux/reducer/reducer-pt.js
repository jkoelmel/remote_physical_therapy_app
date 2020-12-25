import {handleActions} from 'redux-actions';
import storage from 'redux-persist/lib/storage';
import {persistReducer} from 'redux-persist';
import * as constants from '../constants/constants-pt';

const initialPTState = {
  pt_id: '',
  user: null,
  user_id: null,
  email: '',
  f_name: '',
  l_name: '',
  company: '',
  password: '',
  description: '',
  patients: [{}],
  selectedPatient: {
    patient_id: null,
    email: '',
    f_name: '',
    l_name: '',
    description: '',
  },
  errorCode: '',
};

const PTReducer = handleActions(
  {
    [constants.GET_PT_PATIENTS]: (state, action) => ({
        ...state,
        patients: action.payload,
      }),

    [constants.CREATE_PT]: (state, action) => {
      const pt = action.payload;
      return {
        email: pt.email,
        password: pt.password,
        f_name: pt.f_name,
        l_name: pt.l_name,
        company: pt.company,
        description: pt.description,
        patients: [],
      };
    },

    [constants.UPDATE_PT]: (state, action) => {
      const p = action.payload;
      return {
        ...state,
        pt_id: p.pt_id,
        user: p.user,
        user_id: p.user_id,
        email: p.email,
        f_name: p.f_name,
        password: p.password,
        l_name: p.l_name,
        company: p.company,
        description: p.description,
      };
    },
    [constants.LOGIN_PT]: (state, action) => {
      const pt = action.payload;
      return {
        email: pt.email,
      };
    },
    [constants.CHECK_LOGIN_ERROR]: (state, action) => {
      const errorCode = action.payload;
      console.log(errorCode);
      return {
        ...state,
        errorCode,
      };
    },
    [constants.SET_SELECTED_PATIENT]: (state, action) => ({
        ...state,
        selectedPatient: action.payload,
      }),
    [constants.LOGOUT_PT]: (state, action) => {
      const pt = action.payload;
      console.log(initialPTState, action.payload);
      return {
        pt,
      };
    },
  },
  initialPTState,
);

const persistConfig = {
  key: 'pt',
  storage,
  whitelist: ['pt_id'],
  blacklist: ['selectedPatient', 'patients', 'errorCode'],
};

export default persistReducer(persistConfig, PTReducer);
