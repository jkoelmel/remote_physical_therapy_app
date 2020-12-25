import React, { useEffect } from 'react';
import axios from 'axios';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import List from '@material-ui/core/List';
import {Divider,ListItem,ListSubheader} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ReactPlayer from 'react-player';
import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import { connect } from 'react-redux';
import { PlayArrow, TextFieldsRounded } from '@material-ui/icons';
import Button from '@material-ui/core/Button';
import {
  createExercise,
  fetchExerciseVideos,
  selectedExercises,
  filterExercises,
} from '../../Redux/actions/actions-pt';

const useStyles = makeStyles((theme) => ({
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
  sticky: {
    backgroundColor: 'white',
  },
  thumbnail: {
    maxHeight: '175px',
  },
  title: {
    // need to set up for dynamic scaling
    marginLeft: 125,
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
    }
  },

  cssFocused: {},

  notchedOutline: {
    borderWidth: '1px',
    borderColor: '#00559A !important'
  },
}));

export const Library = (props) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [openUpload, setOpenUpload] = React.useState(false);
  const [exercise_url, setExercise_url] = React.useState('');
  const [exerciseTitle, setExerciseTitle] = React.useState('');
  const [selectedVideo, setSelectedVideo] = React.useState([]);
  const [tags, setTags] = React.useState('');
  const [URL, setURL] = React.useState('');
  // const [searchKey, setSearchKey] = React.useState('');


  useEffect(() => {
    // fetchExerciseVideos();
    props.fetchExerciseVideos();
  }, []);

  const handleVideoClick = (event, exercise_id) => {
    const index = selectedVideo.indexOf(exercise_id);
    const newIndex = [...selectedVideo];

    if (index === -1) {
      newIndex.push(exercise_id);
    } else {
      newIndex.splice(index, 1);
    }

    setURL(props.exercises[newIndex - 1].exercise_url);
    // console.log(URL);
    setOpen(true);
  };
  const HandleUploadSubmit = () => {
    //TODO set props.add exercise here
    props.createExercise(exerciseTitle, exercise_url, tags)
  }

  const handleAddExercise = () => {
    setOpenUpload(true)
  }
  const handleClose = () => {
    setOpen(false);
  };
  const handleCloseUpload = () => {
    setOpenUpload(false);
  };
  // Handles checked videos and adds video ids into Checked
  const handleCheckToggle = (value) => () => {
    const currentIndex = props.selectedVideos.indexOf(value);
    const newChecked = [...props.selectedVideos];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    // setChecked(newChecked);
    props.selectedExercises(newChecked);
  };

  return (
    // TODO add search field and update query to return tags

    <div className={classes.root}>
      <List component="nav" aria-label="video-list">
        <ListSubheader color="inherit" className={classes.sticky}>

          <Typography>Exercise Library</Typography>
          <TextField
                  value={props.searchKey}
                  margin="dense"
                  id="name"
                  label="Search Exercises"
                  placeholder="i.e knee, elbow"
                  type="input"
                  variant="outlined"
                  color="secondary"
                  onChange={(e) => { props.filterExercises(props.exercises,e.target.value) }}
                  InputLabelProps={{
                    classes: {
                      root: classes.cssLabel,
                      focused: classes.cssFocused,
                    },
                  }}
                  InputProps={{
                    classes: {
                      root: classes.cssOutlinedInput,
                      focused: classes.cssFocused,
                      notchedOutline: classes.notchedOutline,
                    },
                    inputMode: "numeric"
                  }}
                />
          <Button aria-describedby="transition-modal-exercise" variant="contained" color="inherit" onClick={handleAddExercise}>
            Add Exercise
          </Button>
        </ListSubheader>

        {props.exercises.map((ev, k) => (
            <React.Fragment key={k}>
              <Divider />
              <ListItem className={classes.title}>{ev.title}</ListItem>
              <ListItem>
                <ListItemIcon>
                  <PlayArrow
                      edge="start"
                      checked={props.selectedVideos.indexOf(ev.exercise_id) !== -1}
                      tabIndex={-1}
                      onClick={(event) => handleVideoClick(event, ev.exercise_id)}
                      inputprops={{
                        "aria-labelledby": `checkbox-list-label-${ev.exercise_id}`,
                      }}
                  />
                </ListItemIcon>
                <img className={classes.thumbnail} src={ev.thumbnail} />
                <ListItemSecondaryAction>
                  <Checkbox
                      edge="end"
                      tabIndex={-1}
                      disableRipple
                      onChange={handleCheckToggle(ev.exercise_id)}
                      checked={props.selectedVideos.indexOf(ev.exercise_id) !== -1}
                      inputProps={{
                        "aria-labelledby": `checkbox-list-label-${ev.exercise_id}`,
                      }}
                  />
                </ListItemSecondaryAction>
              </ListItem>
            </React.Fragment>
        ))}
      </List>

      <Modal
        aria-labelledby="transition-modal-upload-open"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={openUpload}
        onClose={handleCloseUpload}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={openUpload}>
          <Paper className={classes.paperUpload}>
            <Grid container className={classes.gridUpload} direction="column" spacing={2}>
              <Grid item>
                <Typography variant='h5'>Upload Exercise Video</Typography>
              </Grid>
              <Grid item>
                <TextField
                  value={exerciseTitle}
                  className={classes.UploadTextfield}
                  autoFocus
                  margin="dense"
                  id="name"
                  label="Exercise Title"
                  required={true}
                  type="upload-title"
                  variant="outlined"
                  color="secondary"
                  onChange={(e) => { setExerciseTitle(e.target.value) }}
                  InputLabelProps={{
                    classes: {
                      root: classes.cssLabel,
                      focused: classes.cssFocused,
                    },
                  }}
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
                <TextField
                  value={exercise_url}
                  margin="dense"
                  id="name"
                  label="Enter Video URL"
                  required={true}
                  type="upload-url"
                  variant="outlined"
                  color="secondary"
                  onChange={(e) => { setExercise_url(e.target.value) }}
                  InputLabelProps={{
                    classes: {
                      root: classes.cssLabel,
                      focused: classes.cssFocused,
                    },
                  }}
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
                <TextField
                  placeholder="i.e legs, ankle workout, hamstring curls, .. etc"
                  label="video tags"
                  variant="outlined"
                  color="secondary"
                  value={tags}
                  onChange={(e) => {
                    setTags(e.target.value);
                  }}
                  // onBlur={(e) => {
                  //   submitDescription(e.target.value, k);
                  // }}
                  multiline
                  rows={4}
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
                <Button variant="outlined" color="secondary" onClick={HandleUploadSubmit}> Submit </Button>
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
    // The state of exercise, as defined by RootReducer
    exercises: state.exercises.searchKey === '' ?  state.exercises.exercises : state.exercises.filteredExercises,
    searchKey: state.exercises.searchKey,
    selectedVideos: state.exercises.selectedVideos,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    createExercise: (title, exercise_url, tags) => dispatch(createExercise(title, exercise_url, tags)),
    fetchExerciseVideos: () => dispatch(fetchExerciseVideos()),
    selectedExercises: (selectedVideos) =>
      dispatch(selectedExercises(selectedVideos)),
    filterExercises: (exercises,searchKey) => dispatch(filterExercises(exercises, searchKey)),
  }),
)(Library);
