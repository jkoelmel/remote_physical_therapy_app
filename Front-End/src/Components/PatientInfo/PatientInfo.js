import React from 'react';
import axios from 'axios';
import List from '@material-ui/core/List';
import {
  Divider,
  ListItem,
  ListItemText,
} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import {connect} from 'react-redux';
import Avatar from '@material-ui/core/Avatar';
import Image from '../../Assets/Images/Paul.jpg';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  large: {
    width: 100,
    height: 100,
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
}));

export const PatientInfo = (props) => {
  const classes = useStyles();
  const [info, setInfo] = React.useState([]);
 

  const fetchPatientInfo = () => {
    axios
      .get('api/patient/id', {
        params: {
          patient_id: props.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        console.log(response);
        console.log(response.data.patient);

        setInfo(response.data);
      })
      .catch(console.log);
  };

  React.useEffect(() => {

    // will load patients activities when the page loads
    if (props.selectedPatient != '') fetchPatientInfo();
  }, [props.selectedPatient]);

  return (
    <div>
      <List component="nav" aria-label="patient-list" style={{maxHeight: 300}}>
        <div>
          <ListItem>
            <Avatar
              alt="user-profile images"
              src={Image}
              className={classes.large}
            />
          </ListItem>
          <ListItem>
            <ListItemText
              primary={`Patient`}
              secondary={`${info.f_name  } ${  info.l_name}`}
            />
          </ListItem>
          <ListItem>
            <ListItemText primary={`Email`} secondary={info.email} />
          </ListItem>
          <ListItem>
            <ListItemText primary={`Company`} secondary={info.company} />
          </ListItem>
          <ListItem>
            <ListItemText primary={`Injured Area`} secondary={info.injury} />
          </ListItem>
          <Divider />
        </div>
      </List>
    </div>
  );
};

export default connect(
  (state) => ({
    pt: state.pt,
    patients: state.pt.patients,
    selectedPatient: state.pt.selectedPatient,
  }),
  (dispatch) => ({}),
)(PatientInfo);
