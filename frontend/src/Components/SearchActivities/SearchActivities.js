import React from 'react';
import axios from 'axios';
import Button from '@material-ui/core/Button';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import List from '@material-ui/core/List';
import {
  Divider,
  ListItem,
  ListItemText,
  ListSubheader,
} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import {makeStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles((theme) => ({
  modal: {
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
}));

const SearchActivities = ({selectedPatient, setSelectedPatient}) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [activities, setActivities] = React.useState([]);

  console.log(`patient id: ${selectedPatient}`);

  const fetchPatientActivity = () => {
    axios
      .get('api/activity/id', {
        params: {
          patient: selectedPatient,
          pt: 1,
        },
      })
      .then((response) => {
        console.log(response);
        console.log(response.data.patient);

        setActivities(
          response.data.map((pa) => {
            console.log(response.data);
            return pa;
          }),
        );
      })
      .catch(console.log);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleGenerate = () => {
    setOpen(true);
  };

  React.useEffect(() => {
    // will load patients activities when the page loads
    if (selectedPatient != '') fetchPatientActivity();
  }, [selectedPatient]);

  return (
    <div>
      <div style={{width: 'auto'}}>
        <Button onClick={handleGenerate} color="secondary">
          Generate Patient Activities
        </Button>
      </div>

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
          <div className={classes.paper}>
            <List
              component="nav"
              aria-label="patient-list"
              style={{maxHeight: 500, overflowY: 'scroll'}}
              subheader={
                selectedPatient == '' ? (
                  <Typography>Patient must be picked first.</Typography>
                ) : (
                  <ListSubheader
                    component="div"
                    color="inherit"
                    classes={{sticky: classes.sticky}}
                  >
                    Activity report
                  </ListSubheader>
                )
              }
            >
              {activities.map((a) => (
                <div>
                  <ListItem key={a.activity_id}>
                    <ListItemText
                      primary={`Activity Type`}
                      secondary={a.type_activity}
                    />
                  </ListItem>
                  <ListItem key={a.activity_id}>
                    <ListItemText primary={`Duration`} secondary={a.duration} />
                  </ListItem>
                  <ListItem key={a.activity_id}>
                    <ListItemText
                      primary={`Start time`}
                      secondary={a.start_time}
                    />
                  </ListItem>
                  <ListItem key={a.activity_id}>
                    <ListItemText primary={`End time`} secondary={a.end_time} />
                  </ListItem>
                  <Divider />
                </div>
              ))}
            </List>
          </div>
        </Fade>
      </Modal>
    </div>
  );
};

export default SearchActivities;
