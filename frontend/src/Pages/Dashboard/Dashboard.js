import React from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import {makeStyles} from '@material-ui/core/styles';
import {Typography} from '@material-ui/core';
import Divider from '@material-ui/core/Divider';
import PatientList from '../../Components/PatientList/PatientList';
import Messaging from '../../Components/Messaging/Messaging';
import ActivitySummary from '../../Components/PTActivitySummary/PTActivitySummary';
import PatientDashboardInfo from '../../Components/PatientDashboardInfo/PatientDashboardInfo';
import AssignWorkout from '../../Components/AssignWorkout/AssignWorkout';
import SavedWorkout from '../../Components/SavedWorkout/SavedWorkout';

// TODO Will most likely have to fix paperMessage margins when we implement
// the actual message board.
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    marginTop: 24,
    color: theme.palette.secondary.light,
  },
  paperMessage: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 700,
    width: 350,
    marginTop: 50,
    marginBottom: 139,
  },
  paperPatients: {
    padding: theme.spacing(0),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    height: 650,
    width: 350,
    marginTop: 50,
    marginLeft: 10,
  },
  paperProfile: {
    padding: theme.spacing(2),
    textAlign: 'center',
    backgroundColor: '#f5f5f5',
    height: 700,
    width: 1000,
    marginTop: 50,
    marginBottom: 139,
    marginRight: 15,
  },
  paperSummary: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.secondary.main,
    marginTop: 60,
    width: 350,
    height: 830,
  },
}));

const Dashboard = () => {
  const classes = useStyles();

  return (
    <div
      className={classes.root}
      justify={'space-between'}
      alignItems={'flex-start'}
    >
      <Messaging />
      <Grid
        container
        spacing={3}
        direction="row"
        style={{
          margin: 0,
          width: '100%',
        }}
      >
        <Grid item>
          <Paper
            className={classes.paperPatients}
            style={{overflow: 'auto'}}
            elevation={5}
          >
            <PatientList />
          </Paper>
        </Grid>
        <Grid item>
          <Paper className={classes.paperProfile} elevation={5}>
            {/* <PatientsList/>  TODO need to handle Axios or hooks
                        in order to use */}
            <Typography variant="h5">
              <u>Patient Profile</u>
            </Typography>
            <PatientDashboardInfo />
          </Paper>
        </Grid>
        <Grid item>
          <Grid container spacing={3} direction="column" alignItems={'center'}>
            <Paper className={classes.paperSummary} elevation={5}>
              <Grid item>
                <Typography>
                  <b>
                    <u>Total Activity Summary</u>
                  </b>
                </Typography>
                <ActivitySummary />
              </Grid>
              <Divider />
              <Grid item>
                <SavedWorkout />
              </Grid>
              <Divider />
              <Grid item>
                <AssignWorkout />
              </Grid>
            </Paper>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
};

export default Dashboard;
