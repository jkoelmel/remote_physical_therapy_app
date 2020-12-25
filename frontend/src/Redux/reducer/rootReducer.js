import {combineReducers} from 'redux';
import PatientsReducer from './reducer-patients';
import PTReducer from './reducer-pt';
import WorkoutReducer from './reducer-workouts';

const rootReducer = combineReducers({
  patients: PatientsReducer,
  pt: PTReducer,
  exercises: WorkoutReducer,
});

export default rootReducer;
