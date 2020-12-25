import {handleActions} from 'redux-actions';
import * as constants from '../constants/constants-patient';

const initialPatientState = {
  patients: [
    {
      patient_id: 1,
      user: 14,
      pt: 2,
      prospective_pt: 2,
      user_id: 14,
      email: 'test@mail.com',
      f_name: 'jane',
      l_name: 'doe',
      company: 'the NY co',
      injury: '',
      entry_id: '',
      comment: '',
      patient_video_id: '',

    },
  ],
};

const PatientsReducer = handleActions(
  {
    // TODO: check that this actually works
    [constants.CREATE_PATIENT]: (state, action) => {
      const {patient} = action.payload;
      const newPatients = state.patients.slice();
      newPatients.push(patient);

      return {
        patients: newPatients,
      };
    },

    // TODO: check that this actually works
    [constants.UPDATE_PATIENT_PTS]: (state, action) => {
      const newPatients = state.patients.slice();
      const patient = newPatients.find(action.payload.patient);
      patient.pt_id = action.payload.pt;
      patient.prospective_pt = action.payload.prospective_pt;

      return {
        patients: newPatients,
      };
    },

    [constants.GET_PATIENTS]: (state, action) => ({
        patients: action.payload.patients,
      }),
  },
  initialPatientState,
);

export default PatientsReducer;
