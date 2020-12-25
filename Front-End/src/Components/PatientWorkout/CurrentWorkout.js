import React from 'react';
import axios from 'axios';
import List from '@material-ui/core/List';
import {
  Divider,
  ListItem,
  ListItemText,
  ListSubheader,
} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import Modal from '@material-ui/core/Modal';
import {connect} from 'react-redux';
import {setSelectedPatient} from '../../Redux/actions/actions-pt';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: 'white',
    //   border: '2px solid #000',a
    outline: 'none',
  },
  sticky: {
    backgroundColor: 'white',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

const CurrentWorkout = (props) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [workout, setWorkout] = React.useState([]);
  const [selectedWorkout, setSelectedWorkout] = React.useState('');
  const [exercises, setExercises] = React.useState([]);

  const fetchWorkoutInfo = () => {
    axios
      .get('api/patient/workout/id', {
        params: {
          patient: props.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        setWorkout(
          response.data.map((w) => {
            console.log(response.data);
            setSelectedWorkout(response.data[0].workout);
            return w;
          }),
        );
      })
      .catch(console.log);
  };

  const fetchWorkoutExercises = () => {
    axios
      .get('api/pt/exercises', {
        params: {
          workout: selectedWorkout,
        },
      })
      .then((response) => {
        setExercises(
          response.data.map((e) => {
            console.log(response.data);
            return e;
          }),
        );
      })
      .catch(console.log);
  };

  const handleWorkoutClick = (e, selectedWorkout) => {
    // double check proper setting
    console.log(`Selected Workout: ${  selectedWorkout}`);
    setSelectedWorkout(selectedWorkout);
    fetchWorkoutExercises();
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  React.useEffect(() => {
    // will load workout info when the page loads
    if (props.selectedPatient != '') fetchWorkoutInfo();
  }, [props.selectedPatient]);

  return (
    <div className={classes.root}>
      <List
        className={classes.paper}
        aria-label="workout-list"
        style={{maxHeight: 300}}
      >
        <ListItem className={classes.modal}>
          <u>
            <b>Current Assignment</b>
          </u>
        </ListItem>
        {workout.map((w) => (
          <div>
            <ListItem
              key={w.workout}
              button
              selected={selectedWorkout == w.workout}
              onClick={(event) => handleWorkoutClick(event, w.workout)}
            >
              {w.title}
            </ListItem>
          </div>
        ))}
      </List>

      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <List
            style={{
              maxHeight: 400,
              overflowY: 'scroll',
              backgroundColor: 'white',
            }}
            subheader={
              <ListSubheader
                component="div"
                color="inherit"
                classes={'patient-list'}
              >
                Workout Details
              </ListSubheader>
            }
          >
            {exercises.map((e) => (
              <div>
                <ListItem key={e.exercise_id}>
                  <ListItemText
                    primary={`Exercise Title`}
                    secondary={e.title}
                  />
                </ListItem>
                <ListItem key={e.exercise_id}>
                  <ListItemText
                    primary={`Description`}
                    secondary={e.description}
                  />
                </ListItem>
                <ListItem>
                  <a href={e.exercise_url} target="_blank">
                    <img
                      src={
                        `https://img.youtube.com/vi/${ 
                        e.exercise_url.split('=')[1] 
                        }/0.jpg`
                      }
                    />
                  </a>
                </ListItem>
                <Divider />
              </div>
            ))}
          </List>
        </Fade>
      </Modal>
    </div>
  );
};

export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    selectedPatient: state.pt.selectedPatient,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    setSelectedPatient: (patient) => dispatch(setSelectedPatient(patient)),
  }),
)(CurrentWorkout);
