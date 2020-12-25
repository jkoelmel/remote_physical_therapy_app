import * as constants from '../constants/constants-patient';
import * as constantsWorkout from '../constants/constants-workouts';
import {getAuth, postAuth, putAuth} from './actions-auth';

export const createNewPatient = (patient) => {
  const data = {
    email: patient.email,
    f_name: patient.f_name,
    l_name: patient.l_name,
    company: patient.compact,
  };
  return (dispatch) => {
    postAuth('/api/patient/register', data)
      .then(dispatch(createPatient(patient)))
      .catch((err) => console.log('Error creating patient:', err));
  };
};

export const createPatient = (patient) => ({
    type: constants.CREATE_PATIENT,
    payload: patient,
  });

export const fetchPatients = () => (dispatch) => {
    getAuth('/api/patient/all')
      .then((response) => dispatch(loadPatients(response.data.patients)))
      .catch((err) => console.log('Error fetching all patients:', err));
  };

export const loadPatients = (patients) => ({
    type: constants.GET_PATIENTS,
    payload: patients,
  });

export const updatePatientPT = (patient, pt, prospective_pt) => {
  const data = {patient_id: patient, pt, prospective_pt};
  return (dispatch) => {
    putAuth('api/patient/update-pt', data)
      .then(dispatch(submitUpdatePatientPT(patient, pt, prospective_pt)))
      .catch((err) =>
        console.log(
          `Error updating patient PT's to ${pt} and/or ${prospective_pt}:`,
          err,
        ),
      );
  };
};

export const submitUpdatePatientPT = (patient, pt, prospective_pt) => ({
    type: constants.UPDATE_PATIENT_PTS,
    payload: {
      patient,
      pt,
      prospective_pt,
    },
  });

  export const putEntryComment = (entry_id, comment) => {
    const params = new URLSearchParams();
    params.append('entry_id', entry_id);
    params.append('comment', comment);
    
    return () => {
      putAuth('/api/patient/entry/comment', params)
        .then((res) => {
          if (res.data == 200) {
            console.log(res.data);
            window.alert('comment post: success');
            window.location.href = '/';
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };
  };

  export const putVideoComment = ( patient_video_id, comment) => {
    const params = new URLSearchParams();
    params.append('patient_video_id',  patient_video_id);
    params.append('comment', comment);
    
    return () => {
      putAuth('/api/patient/video/comment', params)
        .then((res) => {
          if (res.data == 200) {
            console.log(res.data);
            window.alert('patient video comment post: success');
            window.location.href = '/';
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };
  };
  // export const submitComment = (entry_id,comment) => ({
  //   type: constants.UPDATE_COMMENT,
  //   payload: {
  //     patient,
  //     pt,
  //     prospective_pt,
  //   },
  // });

export const fetchPatientExerciseVideos = (selectedPatient) => {
  const params = new URLSearchParams();
  params.append('patient', selectedPatient);
  console.log('made it to fetch patients videos!!');
  return (dispatch) => {
    getAuth('/api/patient/video/id', params)
      .then((response) => dispatch(loadPatientExerciseVideos(response.data)))
      .catch((err) => console.log(err));
  };
};

export const loadPatientExerciseVideos = (patientExerciseVideos) => ({
    type: constantsWorkout.GET_PATIENT_EXERCISE_VIDEOS,
    payload: patientExerciseVideos,
  });
