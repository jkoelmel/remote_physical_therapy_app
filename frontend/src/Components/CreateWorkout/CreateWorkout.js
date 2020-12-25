import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import {Divider, ListItem, ListItemText, Button} from '@material-ui/core';
import ListItemIcon from '@material-ui/core/ListItemIcon';

import {connect} from 'react-redux';

import TextField from '@material-ui/core/TextField';
import {Create} from '@material-ui/icons';
import ListSubheader from '@material-ui/core/ListSubheader';
import {
  createWorkout,
  fetchExerciseVideos,
  selectedExercises,
} from '../../Redux/actions/actions-pt';

const useStyles = makeStyles((theme) => ({
  root: {
    '& .MuiTextField-root': {
      //   margin: theme.spacing(1),
      width: '25ch',
    },
  },
  sticky: {
    color: 'secondary',
    fontWeight: 'bold',
  },
}));

export const CreateWorkout = (props) => {
  const classes = useStyles();
  const [description, setDescription] = React.useState('');
  const [videoDescriptions, setVideoDescriptions] = React.useState([]);
  const [workoutTitle, setWorkoutTitle] = React.useState('');

  const submitDescription = (desc, index) => {
    setDescription(desc);

    const instructions = [...videoDescriptions];
    instructions[index] = description;
    setVideoDescriptions([...instructions]);
  };

  // console.log(props.selectedVideos);
  // console.log(workoutTitle);
  // console.log(videoDescriptions);

  const submitWorkout = () => {
    props.createWorkout(
      props.pt,
      workoutTitle,
      props.selectedVideos,
      videoDescriptions,
    );
  };

  return (
    <div className={classes.root}>
      <Grid container direction="column" spacing={3}>
        <Grid item>
          <TextField
            color="secondary"
            id="standard-multiline-flexible"
            label="Workout Title"
            multiline
            value={workoutTitle}
            required={true}
            onChange={(e) => {
              setWorkoutTitle(e.target.value);
            }}
            variant="outlined"
            rowsMax={4}
          />
        </Grid>

        <Grid item>
          <List
            subheader={
              <ListSubheader className={classes.sticky}>
                Exercises
              </ListSubheader>
            }
          >
            {props.selectedVideos.map((ev, k) => (
              <React.Fragment key={k}>
                <Divider />
                <ListItem>
                  <ListItemText>
                    {props.exercises[props.selectedVideos[k] - 1].title}
                  </ListItemText>
                  <TextField
                    key={`description-${props.exercises[k].exercise_id}`}
                    placeholder="Exercise Instructions"
                    label="Description"
                    variant="outlined"
                    color="secondary"
                    // value = {description}
                    required={true}
                    onChange={(e) => {
                      setDescription(e.target.value);
                    }}
                    onBlur={(e) => {
                      submitDescription(e.target.value, k);
                    }}
                    multiline
                    rows={4}
                  />
                  <ListItemIcon></ListItemIcon>
                  <Divider />
                </ListItem>
                <Divider />
              </React.Fragment>
            ))}
          </List>
          <Button onClick={submitWorkout} variant="outlined">
            Submit
          </Button>
        </Grid>
      </Grid>
    </div>
  );
};

export default connect(
  (state) => ({
    pt: state.pt,
    // The state of exercise, as defined by RootReducer
    exercises: state.exercises.exercises,
    selectedVideos: state.exercises.selectedVideos,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    fetchExerciseVideos: () => dispatch(fetchExerciseVideos()),
    selectedExercises: (selectedVideos) =>
      dispatch(selectedExercises(selectedVideos)),
    createWorkout: (pt, title, exercises, descriptions) =>
      dispatch(createWorkout(pt, title, exercises, descriptions)),
  }),
)(CreateWorkout);
