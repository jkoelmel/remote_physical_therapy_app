import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import axios from 'axios';
import ListItem from '@material-ui/core/ListItem';
import List from '@material-ui/core/List';
import { Divider } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { setSelectedPatient } from '../../Redux/actions/actions-pt';
import { putEntryComment } from '../../Redux/actions/actions-patients';
import AddCommentIcon from '@material-ui/icons/AddComment';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import Modal from '@material-ui/core/Modal';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';


const useStyles = makeStyles((theme) => ({
  sticky: {
    backgroundColor: 'white',
  },
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
    outline: 'none',
  },
  paperUpload: {
    height: 500,
    width: 500,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  gridUpload: {
    alignContent: "center",
    alignItems: 'center',
    width: 500,
  },
  UploadTextfield: {
    // borderColor: '#00559A'
  },
  cssLabel: {
    color: '#00559A'
  },

  cssOutlinedInput: {
    '&$cssFocused $notchedOutline': {
      borderColor: `#00559A !important`,
      width: 200
    }
  },

  cssFocused: {},

  notchedOutline: {
    borderWidth: '1px',
    borderColor: '#00559A !important'
  },
  comments: {
    color: '#00559A',
  }
}));

export const SearchReport = (props) => {
  const classes = useStyles();
  const [patientReport, setPatientReport] = React.useState([]);
  const [open, setOpen] = React.useState(false);
  const [comment, setComment] = React.useState('')
  const [entryID,setEntryID] = React.useState('')

  const fetchPatientsReport = () => {
    axios
      .get('/api/patient/entry/all', {
        params: {
          patient_id: props.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        console.log(response);
        console.log(response.data.patient);

        setPatientReport(
          response.data.map((pr) => pr),
        );
      })
      .catch(console.log);
  };

  const handleCommentClick = (e,entry_id) => {
    setEntryID(entry_id)
    setOpen(true)

  }
  const handleCommentClose = () => {

    setOpen(!open)

  }
  const HandleCommentSend = () => {
    //TODO UPDATECOMMENT
    // props.createExercise(exerciseTitle, exercise_url, tags)
    props.putEntryComment(entryID,comment)
  }

  React.useEffect(() => {
    // will load patients Report when the page loads
    if (props.selectedPatient.patient_id != '') fetchPatientsReport();
  }, [props.selectedPatient.patient_id]);

  return (
    <div className={classes.sticky}>
      <List
        component="nav"
        aria-label="patient-list"
        style={{ maxHeight: 580, overflowY: 'scroll' }}
      >
        {patientReport.map((pr) => (
          <div>
          <ListItem>
            <ListItemText primary={pr.entry} secondary={` posted on ${pr.created_on}`} />
            <ListItemIcon>
              <AddCommentIcon
                color="secondary"
                onClick={(event) => handleCommentClick(event,pr.entry_id)} 
              />
            </ListItemIcon>
          </ListItem>
            <ListItemText className={classes.comments} primary={pr.comment === undefined ? '' : `Comment: ${pr.comment}` }/>
          <Divider/>
          </div>
          
         
        ))}
      </List>
      <Modal
        aria-labelledby="transition-modal-upload-open"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={open}
        onClose={handleCommentClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <Paper className={classes.paperUpload}>
            <Grid container className={classes.gridUpload} direction="column" spacing={2}>
              <Grid item>
                <Typography variant='h5'> Write {props.selectedPatient.f_name} a comment on their progress:</Typography>
              </Grid>
              <Grid item>
                <TextField
                  placeholder={`Great work ${props.selectedPatient.f_name}!`}
                  label='insert comment here'
                  variant="outlined"
                  color="secondary"
                  value={comment}
                  onChange={(e) => { setComment(e.target.value) }}
                  multiline
                  rows={5}
                  InputProps={{
                    classes: {
                      root: classes.cssOutlinedInput,
                      focused: classes.cssFocused,
                      notchedOutline: classes.notchedOutline,
                    },
                    inputMode: "numeric"
                  }}
                />
              </Grid>
              <Grid item>
                <Button variant="outlined" color="secondary" onClick={HandleCommentSend}> SEND </Button>
              </Grid>
            </Grid>
          </Paper>

        </Fade>



      </Modal>
    </div>
  );
};

export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    // The state of the pt's patients, defined by reducer-pt
    patients: state.pt.patients,
    selectedPatient: state.pt.selectedPatient,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    putEntryComment: (entry_id,comment) => dispatch(putEntryComment(entry_id,comment)),
    setSelectedPatient: (patient) => dispatch(setSelectedPatient(patient)),
  }),
)(SearchReport);
