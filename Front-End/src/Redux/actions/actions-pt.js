import * as constants from '../constants/constants-pt';
import * as constantsWorkout from '../constants/constants-workouts';
import {getAuth, postAuth,putAuth} from './actions-auth';

export const createNewPT = (pt) => {
  const params = new URLSearchParams();
  params.append('email', pt.email);
  params.append('f_name', pt.f_name);
  params.append('l_name', pt.l_name);
  params.append('password', pt.password);
  params.append('company', pt.company);
  params.append('description', pt.description);
  return (dispatch) => {
    postAuth('api/pt/register', params)
      .then(() => {
        dispatch(createPT(pt));
        dispatch(getPTByEmail(pt.email));
        window.alert('Create New PT: success');
        window.location.href = '/';
      })
      .catch((err) => console.log('Error creating pt:', err));
  };
};
// TODO SEND MESSAGE IF ERROR
export const loginPTError = (err) => ({
    type: constants.CHECK_LOGIN_ERROR,
    payload: err,
  });

export const loginPT = (pt) => {
  const params = new URLSearchParams();
  params.append('email', pt.email);
  params.append('password', pt.password);
  console.log('params: ', params);

  return (dispatch) => {
    postAuth('/api/pt/login', params)
      .then((res) => {
        console.log('login status: ', res.data);
        if (res.data == 200) {
          dispatch(getPTByEmail(pt.email));
        } else {
          console.log(res.data.payload.message);
          // dispatch(loginPTError(res.data))
        }
      })
      .catch((err) => {
        dispatch(loginPTError('username or password is invalid.'));
        console.log(err);
      });
  };
};

export const logoutPT = (pt) => {
  if (pt.pt_id == null) {
    return null;
  }

  return {
    type: constants.LOGOUT_PT,
    payload: {},
  };
};
export const deleteWorkout = (workout_id) => {
  const params = new URLSearchParams();
  params.append('workout_id', workout_id);
 
  
  return () => {
    putAuth('/api/patient/workout/remove', params)
      .then((res) => {
        if (res.data == 200) {
          console.log(res.data);
          window.alert('deleted workout successfully');
          window.location.href = '/';
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
export const assignWorkout = (pt, checked, selectedWorkout) => {
  const params = new URLSearchParams();
  params.append('pt', pt.pt_id);
  for (let i = 0; i < checked.length; i++) {
    params.append('patient', checked[i]);
  }
  for (let j = 0; j < selectedWorkout.length; j++) {
    params.append('workout', selectedWorkout[j]);
  }
  return () => {
    postAuth('/api/pt/assign', params)
      .then((response) => {
        if (response.data == 200) {
          console.log('Message success');
          window.alert('Assignments complete');
          window.location.href = '/';
        }
      })
      .catch(console.log);
  };
};

export const createWorkout = (pt, title, exercises, descriptions) => {
  const params = new URLSearchParams();
  params.append('pt', pt.pt_id);
  params.append('title', title);

  for (let i = 0; i < exercises.length; i++) {
    params.append('exercise_id', exercises[i]);
    params.append('description', descriptions[i]);
  }
  return () => {
    postAuth('/api/pt/create', params)
      .then((res) => {
        if (res.data == 200) {
          console.log(res.data);
          window.alert('Workout creation: success');
          window.location.href = '/';
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};
export const createExercise = (title, exercise_url, tags) => {
  const params = new URLSearchParams();
  params.append('title', title);
  params.append('exercise_url', exercise_url);
  params.append('tags', tags);
  
  return () => {
    postAuth('/api/exercise/register', params)
      .then((res) => {
        if (res.data == 200) {
          console.log(res.data);
          window.alert('exercise video upload: success');
          window.location.href = '/';
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};


export const createPT = (pt) => ({
    type: constants.CREATE_PT,
    payload: pt,
  });

export const getPTByEmail = (email) => (dispatch) => {
    getAuth('api/pt/email', {email})
      .then((response) => {
        dispatch(updatePT(response.data));
        dispatch(fetchPTsPatients(response.data.pt_id));
      })
      .catch((err) =>
        console.log(`Error fetching PT with email ${email}:`, err),
      );
  };

export const updatePT = (pt) => ({
    type: constants.UPDATE_PT,
    payload: pt,

  });

  export const EditPT = (pt) => {return (dispatch) => {

    const params = new URLSearchParams();
    params.append('email', pt.email);
    params.append('pt_id', pt.pt_id);
    params.append('password', pt.password);
    params.append('f_name', pt.f_name);
    params.append('l_name', pt.l_name);
    params.append('description', pt.description);
    params.append('company', pt.company);

    putAuth('api/pt/update', params)
      .then(dispatch(updatePT(pt))).then(()=> {
        window.alert('UpdatePT creation: success');
        window.location.href = '/';
      }
      )
      .catch((err) =>
        console.log(
          `Error Editing PT information:`,
          err,
        ),
      );
  };}

export const fetchPTs = () => (dispatch) => {
    getAuth('/api/pt/all')
      .then((response) => dispatch(loadPTs(response.data)))
      .catch((err) => console.log('Error fetching all patients:', err));
  };

export const loadPTs = (pts) => ({
    type: constants.GET_ALL_PTS,
    payload: pts,
  });

export const fetchPTsPatients = (pt) => (dispatch) => {
    getAuth('/api/pt/patients', {pt_id: pt})
      .then((response) => dispatch(loadPTsPatients(response.data)))
      .catch((err) => console.log(`Error fetching patients for PT ${pt}`, err));
  };

export const loadPTsPatients = (patients) => ({
    type: constants.GET_PT_PATIENTS,
    payload: patients,
  });

export const fetchExerciseVideos = () => {
  console.log('im here');
  return (dispatch) => {
    getAuth('/api/exercise/all')
      .then((response) => dispatch(loadExerciseVideos(response.data)))
      .catch((err) => console.log("FAILED"));
  };
};

export const loadExerciseVideos = (exercises) => ({
    type: constantsWorkout.GET_EXERCISE_VIDEOS,
    payload: exercises, 
  });
export const selectedExercises = (selectedVideos) => ({
    type: constantsWorkout.GET_SELECTED_VIDEOS,
    payload: selectedVideos,
  });

export const setSelectedWorkouts = (selectedWorkouts) => ({
    type: constantsWorkout.GET_SELECTED_WORKOUTS,
    payload: selectedWorkouts,
  });

export const setSelectedPatient = (patient) => ({
    type: constants.SET_SELECTED_PATIENT,
    payload: patient,
  });

  export const filterExercises = (exercises, searchKey) => ({
    type: constantsWorkout.SEARCH_EXERCISES,
    payload: { searchKey,
      exercises:exercises.filter((e)=> e.tags.toLowerCase().indexOf(searchKey.toLowerCase()) !== -1)
  }
  });
