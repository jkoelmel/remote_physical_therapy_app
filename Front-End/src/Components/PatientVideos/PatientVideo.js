import React from 'react';
import axios from 'axios';
import Button from '@material-ui/core/Button';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import List from '@material-ui/core/List';
import {
  Divider,
  ListItem,
  ListItemText} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import {makeStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ReactPlayer from 'react-player';
import {connect} from 'react-redux';
import {
  setSelectedPatient,
} from '../../Redux/actions/actions-pt';
import AddCommentIcon from '@material-ui/icons/AddComment';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import { putVideoComment } from '../../Redux/actions/actions-patients';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  videos: {
    padding: theme.spacing(2, 4, 3),
  },
  modalComment: {
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

  sticky: {
    backgroundColor: 'white',
  },
  thumbnail: {
    maxHeight: "150px",

  },
  comments: {
    color: '#00559A',
  }
}));

export const PatientVideos = (props) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [videos, setVideos] = React.useState([]);
  const [selectedVideo, setSelectedVideo] = React.useState([]);
  const [URL, setURL] = React.useState('');
  const [feedback, setFeedback] = React.useState('');
  const [comment, setComment] = React.useState('')
  const [videoID,setVideoID] = React.useState('')
  const [openComment, setOpenComment] = React.useState(false);

  const handleVideoClick = (e, video_url) => {
    setURL(video_url);
    setOpen(true);
  };

  const fetchPatientVideos = () => {
    axios
      .get('api/patient/video/id', {
        params: {
          patient: props.selectedPatient.patient_id,
        },
      })
      .then((response) => {
        setVideos(
          response.data.map((pv) => {
            console.log(response.data.patient_video);
            return pv;
          }),
        );
      })
      .catch(console.log);
  };


  console.log( videos.map((pv) => {
    return pv;
  }))


  const handleClose = () => {
    setOpen(false);
  };

  // const handleChange = () => {
  //   setFeedback(feedback);
  // };

  // const handleSubmit = () => {
  //   alert(`Feedback was submitted: ${  feedback}`);
  //   handleClose();
  // };
  const handleCommentClick = (e,patient_video_id) => {
    setVideoID(patient_video_id)
    e.stopPropagation()
    // setVideoID(video_id)
    setOpenComment(true)

  }
  console.log(videoID)

  const handleCommentClose = () => {

    setOpenComment(!openComment)

  }
  const HandleCommentSend = () => {
    props.putVideoComment(videoID,comment)

  }

  React.useEffect(() => {
    // will load patients video when the page loads
    if (props.selectedPatient != '') fetchPatientVideos();
  }, [props.selectedPatient]);

  return (
    <div className={classes.root}>
      <List
        component="nav"
        aria-label="patient-list"
        style={{maxHeight: 600, overflowY: 'scroll'}}
      >
        {videos.map((v) => (
          <div>
            <ListItem >{v.uploaded}</ListItem>
            <ListItem
            
              key={v.patient_video_id}
              button
              selected={selectedVideo == v.patient_video_id}
              onClick={(event) => handleVideoClick(event, v.video_url)}
            >

              <img
              className={classes.thumbnail}
                src={
                  `https://img.youtube.com/vi/${ 
                  v.video_url.split('=')[1] 
                  }/0.jpg`
                }
              />
              
              <ListItemIcon className={classes.videos}>
              <AddCommentIcon
                color="secondary"
                onClick={(event) => handleCommentClick(event,v.patient_video_id)} 
              />
            </ListItemIcon>
            </ListItem>
            <ListItemText className={classes.comments} primary={v.comment === undefined ? '' : `Comment: ${v.comment}` }/>
            <Divider/>
          </div>
        ))}
      </List>
      <Modal
        aria-labelledby="transition-modal-comment-open"
        aria-describedby="transition-modal-comment"
        className={classes.modalComment}
        open={openComment}
        onClose={handleCommentClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={openComment}>
          <Paper className={classes.paperUpload}>
            <Grid container className={classes.gridUpload} direction="column" spacing={2}>
              <Grid item>
                <Typography variant='h5'> Write {props.selectedPatient.f_name} a comment on their video: </Typography>
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
            <ReactPlayer controls={true} url={URL} />
          </div>
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
    putVideoComment: (videoID, comment) => dispatch(putVideoComment(videoID, comment))
  }),
)(PatientVideos);
