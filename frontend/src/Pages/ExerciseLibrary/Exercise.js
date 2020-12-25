import React from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import {makeStyles} from '@material-ui/core/styles';
import {Typography} from '@material-ui/core';
import Library from '../../Components/ExerciseLibrary/Library';
import SavedWorkout from '../../Components/SavedWorkout/SavedWorkout';
import AssignWorkout from '../../Components/AssignWorkout/AssignWorkout';
import CreateWorkout from '../../Components/CreateWorkout/CreateWorkout';

const useStyles = makeStyles((theme) => ({
  root: {
    minHeight: '95vh',
    flexGrow: 1,
    paddingTop: 100,
    background: theme.palette.background.default,
    overflow: 'hidden',
  },
  paperLibrary: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 750,
    width: 400,
    marginTop: 10,
    marginBottom: 139,
    marginLeft: 30,
  },
  paperWorkouts: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 700,
    width: 500,
    marginTop: 10,
    marginLeft: 25,
    overflowY: 'scroll',
  },
  paperAssign: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 400,
    width: 300,
    marginTop: 10,
    marginLeft: 50,
    overflowY: 'scroll',
  },
  paperPatients: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 375,
    width: 300,
    marginTop: 10,
    marginLeft: 50,
  },
}));

const Exercise = () => {
  const classes = useStyles();
  // const [selectedWorkout, setSelectedWorkout] = React.useState([]);
  const [checkedWorkout, setCheckedWorkout] = React.useState([]);
  const [checked, setChecked] = React.useState([]);

  return (
    <div className={classes.root}>
      <Grid container spacing={3} direction="row" justify={'space-between'}>
        <Grid item md={3}>
          <Paper
            className={classes.paperLibrary}
            elevation={5}
            style={{maxHeight: 700, overflowY: 'scroll', paddingTop: '0px'}}
          >
            {/* <Typography>Exercise Library</Typography> */}
            <Library checked={checked} setChecked={setChecked} />
          </Paper>
        </Grid>
        <Grid item xs={3}>
          <Paper className={classes.paperWorkouts} elevation={5}>
            <Typography>Create Workout</Typography>
            <CreateWorkout checked={checked} setChecked={setChecked} />
          </Paper>
        </Grid>
        <Grid item xs={3}>
          <Paper className={classes.paperAssign} elevation={5}>
            <SavedWorkout
              checkedWorkout={checkedWorkout}
              setCheckedWorkout={{setCheckedWorkout}}
            />
          </Paper>
          <Paper className={classes.paperPatients} elevation={5}>
            <AssignWorkout checkedWorkout={checkedWorkout} />
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
};
export default Exercise;
