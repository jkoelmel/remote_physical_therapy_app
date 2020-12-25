import React from 'react';
import axios from 'axios';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import List from '@material-ui/core/List';
import Button from '@material-ui/core/Button';

import {
  Divider,
  ListItem,
  ListItemText,
  ListSubheader,
} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import {makeStyles} from '@material-ui/core/styles';
import Checkbox from '@material-ui/core/Checkbox';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import {connect} from 'react-redux';
import {
  fetchPTsPatients,
  setSelectedWorkouts,
  deleteWorkout
} from '../../Redux/actions/actions-pt';
import DeleteIcon from '@material-ui/icons/Delete';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  popoverDelete: {
    padding: theme.spacing(2),
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    //   border: '2px solid #000',a
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
    outline: 'none',
  },
  sticky: {
    backgroundColor: 'white',
  },
  typography: {
    padding: theme.spacing(2),
    color: 'red'
    
  }
}));

export const SavedWorkout = (props) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [workouts, setWorkouts] = React.useState([]);
  const [exercises, setExercises] = React.useState([]);
  const [openPopOver, setOpenPopOver] = React.useState(false);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [selectedID,setSelectedID] = React.useState('')

  const fetchPTWorkouts = () => {
    axios
      .get('api/pt/workouts', {
        params: {
          pt: props.pt.pt_id,
        },
      })
      .then((response) => {
        setWorkouts(
          response.data.map((w) => {
            console.log(response.data);
            return w;
          }),
        );
      })
      .catch(console.log);
  };

  const fetchWorkoutExercises = (selectedWorkout) => {
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
            setOpen(true);
            return e;
          }),
        );
      })
      .catch(console.log);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleDeleteClose = () => {
    setOpenPopOver(!openPopOver)
    setAnchorEl(null);

  }

  const handleWorkoutClick = (e, selectedWorkout) => {
    fetchWorkoutExercises(selectedWorkout);
  };


  const handleDeleteClick = (e,workout_id) => {
    e.stopPropagation()

    setAnchorEl(e.currentTarget)
    setOpenPopOver(!openPopOver)

    setSelectedID(workout_id)
    // props.deleteWorkout(workout_id)
  };

  const handleDelete = (e,id) => {
    e.stopPropagation()
    props.deleteWorkout(id)
  }
  const handleWorkoutToggle = (value) => () => {
    const currentIndex = props.selectedWorkouts.indexOf(value);
    const newcheckedWorkout = [...props.selectedWorkouts];

    if (currentIndex === -1) {
      newcheckedWorkout.push(value);
    } else {
      newcheckedWorkout.splice(currentIndex, 1);
    }

    props.setSelectedWorkouts(newcheckedWorkout);
  };

  React.useEffect(() => {
    fetchPTWorkouts();
  }, []);

  return (
    <div className={classes.root}>
      <List
        aria-label="workout-list"
        subheader={
          <ListSubheader
            component="div"
            color="inherit"
            className={classes.sticky}
          >
            Saved Workouts
          </ListSubheader>
        }
      >
        {workouts.map((w, k) => (
          <div key={k}>
            <ListItem
              button
              selected={props.selectedWorkouts == w.workout_id}
              onClick={(event) => handleWorkoutClick(event, w.workout_id)}
            >
              <ListItemIcon>
              <DeleteIcon
                color="secondary"
                onClick={(event) => handleDeleteClick(event,w.workout_id)} 
              />
            </ListItemIcon>
              {w.title}
              <ListItemSecondaryAction>
                <Checkbox
                  edge="end"
                  tabIndex={-1}
                  disableRipple
                  onChange={handleWorkoutToggle(w.workout_id)}
                  checked={props.selectedWorkouts.indexOf(w.workout_id) !== -1}
                  inputProps={{
                    'aria-labelledby': `checkbox-list-label-${w.workout_id}`,
                  }}
                />
              </ListItemSecondaryAction>
            </ListItem>
          </div>
        ))}
      </List>
      <Popover
        className={classes.popoverDelete}
        id='delete-popover'
        open={openPopOver}
        anchorEl={anchorEl}
        onClose={handleDeleteClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
      >
        <Typography className={classes.typography}>
          Are you sure you want to delete this?
          </Typography>
          <Button  
          color="secondary" 
          onClick={(e)=> {handleDelete(e,selectedID)}}>
            YES
          </Button>

          <Button  
          color="secondary" 
          onClick={handleDeleteClose}>
            NO
            </Button>
      </Popover>

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
              <ListSubheader component="div" color="inherit">
                Workout Details
              </ListSubheader>
            }
          >
            {exercises.map((e, k) => (
              <div key={k}>
                <ListItem>
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
    pt: state.pt,
    // The state of the pt's patients, defined by reducer-pt
    patients: state.pt.patients,
    selectedWorkouts: state.exercises.selectedWorkouts,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    fetchPTsPatients: (pt_id) => dispatch(fetchPTsPatients(pt_id)),
    setSelectedWorkouts: (selectedWorkouts) =>
      dispatch(setSelectedWorkouts(selectedWorkouts)),
    deleteWorkout: (workout_id) => dispatch(deleteWorkout(workout_id))
  }),
)(SavedWorkout);
