import React from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import {makeStyles} from '@material-ui/core/styles';
import {connect} from 'react-redux';
import {List,ListItem, ListItemText, Typography} from '@material-ui/core';
import PTInfo from '../../Components/PTInfo/PTInfo';
import {Button} from '@material-ui/core';
import Modal from '@material-ui/core/Modal';
import Fade from '@material-ui/core/Fade';
import Backdrop from '@material-ui/core/Backdrop';
const useStyles = makeStyles((theme) => ({
    root: {
      maxHeight: '95vh',
      flexGrow: 1,
      paddingTop: 100,
      background: theme.palette.background.default,
      overflow: 'hidden',
    },
    modal: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
    },
    paper: {
      backgroundColor: theme.palette.background.paper,
      boxShadow: theme.shadows[5],
      padding: theme.spacing(2),
      outline: 'none',
    },
    paperInfo: {
      padding: theme.spacing(2),
      textAlign: 'center',
      color: theme.palette.secondary.main,
      height: 675,
      width: 350,
      marginLeft: 10,
    },
}));
const PTProfile = (props) => {
    const classes = useStyles();
  // TODO change to reflect desired PT
  const [selectedPatient, setSelectedPatient] = React.useState(1);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  console.log(props.pt)

  const handleClose = () => {
    setOpen(false);
  };
    console.log(props.pt.pt_id);
    const handlePTSetting = (e) => {
      setImmediate(e);
      
    }
    return(
        <div className={classes.root}>
        <Grid container spacing={3} direction="row">
          <Grid item md={3}>
            <Paper className={classes.paperInfo} elevation={5}>
              <Typography>Physical Therapy Profile</Typography>
              <List>
         <ListItem>
            <ListItemText primary={`Email`} secondary={props.pt.email} />
          </ListItem>
          <ListItem>
            <ListItemText primary={`First Name`} secondary={props.pt.f_name} />
          </ListItem>
          <ListItem>
            <ListItemText primary={`Last Name`} secondary={props.pt.l_name} />
          </ListItem>
          <ListItem> <ListItemText primary={`Company`} secondary={props.pt.company} />
          </ListItem>
          <ListItem> <ListItemText primary={`Description`} secondary={props.pt.description} />
          </ListItem>

          
          </List>
              {/* <PTInfo/> */}
              <Button color = "secondary" variant="outlined" type="button" onClick={handleOpen}>
              Edit Profile
          </Button>
            </Paper>
          </Grid>
          </Grid>
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
           <PTInfo/>
          </div>
        </Fade>
          </Modal> 
        </div>
    );

};

export default connect(
  (state) => ({
      pt: state.pt,
    }),
//     (dispatch) => ({
//       //  getPTByEmail: (email)=> dispatch(getPTByEmail(email))}),
 )
(PTProfile);