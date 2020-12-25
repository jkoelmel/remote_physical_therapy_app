import React from 'react';
import {connect} from 'react-redux';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListSubheader from '@material-ui/core/ListSubheader';
import Grid from '@material-ui/core/Grid';
import {ListItem, ListItemText, Typography} from '@material-ui/core';
import Avatar from '@material-ui/core/Avatar';
import axios from 'axios';
import {makeStyles} from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';

import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import {fetchPatientExerciseVideos} from '../../Redux/actions/actions-patients';
import {
  createNewPT,
  fetchPTsPatients,
  setSelectedPatient,
  updatePT,
} from '../../Redux/actions/actions-pt';
import SearchReport from '../SearchReport/SearchReport';
import PatientInfo from '../PatientInfo/PatientInfo';
import PatientVideos from '../PatientVideos/PatientVideo';
import PatientVideo from '../PatientVideos/PatientVideo';
import CurrentWorkout from '../PatientWorkout/CurrentWorkout';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    '& > *': {
      margin: theme.spacing(1),
    },
  },
  small: {
    width: theme.spacing(3),
    height: theme.spacing(3),
  },
  large: {
    width: theme.spacing(7),
    height: theme.spacing(7),
  },
  gridList: {
    flexWrap: 'nowrap',
    // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
    transform: 'translateZ(0)',
  },
  title: {
    color: theme.palette.primary.light,
  },
  titleBar: {
    background:
      'linear-gradient(to top, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.3) 70%, rgba(0,0,0,0) 100%)',
  },
  info: {
    width: 200,
    height: 410,
  },
  workout: {
    marginTop: 10,
    width: 200,
    height: 100,
  },
  report: {
    width: 350,
    height: 600,
  },
  video: {
    width: 300,
    height: 600,
  },
}));

export const PatientDashboardInfo = (props) => {
  const classes = useStyles();
  const [userImage, setUserImage] = React.useState('');
  const [videos, setVideos] = React.useState([]);

  const fetchPatientImg = () => {
    // TODO hard-coded need to add support to various patients in DB
    axios.get('https://randomuser.me/api/?gender=male').then((response) => {
      setUserImage(response.data.results[0].picture.large);
    });
  };

  React.useEffect(() => {
    // will load patients activities when the page loads
    fetchPatientImg();
  }, []);

  return (
    <div>
      {props.selectedPatient.patient_id ? (
        <Grid
          container
          spacing={3}
          direction="row"
          justify="space-between"
          alignItems="flex-start"
        >
          <Grid item>
            <Typography variant="h6">Info</Typography>
            <Paper className={classes.info}>
              <PatientInfo />
            </Paper>
            <Paper className={classes.workout}>
              <CurrentWorkout />
            </Paper>
          </Grid>
          <Grid item>
            <Typography variant="h6">Progress Log</Typography>
            <Paper className={classes.report}>
              <SearchReport />
            </Paper>
          </Grid>
          <Grid item>
            <Typography variant="h6">Patient Videos</Typography>
            <Paper className={classes.video}>
              <PatientVideo />
            </Paper>
          </Grid>
        </Grid>
      ) : (
        <p></p>
      )}
    </div>
  );
};

export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    pt: state.pt,
    // The state of the pt's patients, defined by reducer-pt
    patients: state.pt.patients,
    selectedPatient: state.pt.selectedPatient,
    PatientExerciseVideos: state.exercises.patientExerciseVideos,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    fetchPTsPatients: (pt_id) => dispatch(fetchPTsPatients(pt_id)),
    createNewPT: (pt) => dispatch(createNewPT(pt)),
    setSelectedPatient: (patient) => dispatch(setSelectedPatient(patient)),
    updatePT: (pt) => dispatch(updatePT(pt)),
    fetchPatientExerciseVideos: (selectedPatient) =>
      dispatch(fetchPatientExerciseVideos(selectedPatient)),
  }),
)(PatientDashboardInfo);
