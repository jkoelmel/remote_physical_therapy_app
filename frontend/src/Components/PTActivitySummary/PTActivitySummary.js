// Test component to see if a summation of all activity
// is worthwhile to the application

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
import {connect} from 'react-redux';
import Button from '@material-ui/core/Button';
import {
  fetchPTsPatients,
  setSelectedPatient,
} from '../../Redux/actions/actions-pt';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    //   border: '2px solid #000',a
    outline: 'none',
  },
  sticky: {
    backgroundColor: 'white',
  },
}));

export const ActivitySummary = (props) => {
  const classes = useStyles();
  const [activity, setActivity] = React.useState([]);
  const [subheader, setSubheader] = React.useState('');

  const fetchSummaryInfo = () => {
    axios
      .get('api/pt/summary', {
        params: {
          pt: props.pt.pt_id,
        },
      })
      .then((response) => {
        setActivity(
          response.data.map((a) => {
            console.log(response.data);
            return a;
          }),
        );
      })
      .catch(console.log);
  };

  const fetchPatPTSummary = () => {
    axios
      .get('api/pt/patient-activity', {
        params: {
          pt: props.pt.pt_id,
          patient: props.pt.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        setActivity(
          response.data.map((a) => a),
        );
      });
  };
  const showButton = () => {
    if (props.pt.selectedPatient.patient_id == null) {
      return null;
    }

    return (
      <Button variant="contained" color="inherit" onClick={handleClick}>
        Show Summary Info
      </Button>
    );
  };

  const handleClick = () => {
    props.setSelectedPatient({});
  };

  React.useEffect(() => {
    if (props.pt.selectedPatient.patient_id == null) {
      fetchSummaryInfo();
      setSubheader('For All Patients');
    } else {
      fetchPatPTSummary(props.pt.selectedPatient.patient_id);
      setSubheader(
        `For ${ 
          props.pt.selectedPatient.f_name 
          } ${ 
          props.pt.selectedPatient.l_name}`,
      );
    }
  }, [props.pt.selectedPatient]);

  return (
    <List
      className={classes.paper}
      aria-label="activity-list"
      style={{maxHeight: 300}}
    >
      <ListItem color="inherit" className={classes.modal}>
        <b>{subheader}</b>
      </ListItem>
      {/* <ListItem className={classes.modal}> */}
      {/*  <u> */}
      {/*    <b>Activity : Minutes</b> */}
      {/*  </u> */}
      {/* </ListItem> */}
      {activity.map((a) => (
        <div>
          <ListItem>
            {`${a.type_activity  } : ${  a.duration  } minutes`}
          </ListItem>
        </div>
      ))}
      {showButton()}
    </List>
  );
};

export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    pt: state.pt,
    // The state of the pt's patients, defined by reducer-pt
    patients: state.pt.patients,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    fetchPTsPatients: (pt_id) => dispatch(fetchPTsPatients(pt_id)),
    setSelectedPatient: (patient) => dispatch(setSelectedPatient(patient)),
  }),
)(ActivitySummary);
