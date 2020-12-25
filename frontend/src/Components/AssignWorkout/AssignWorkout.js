import React from 'react';
import List from '@material-ui/core/List';
import {
  Divider,
  ListItem,
  ListItemText,
  ListSubheader,
} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import {connect} from 'react-redux';
import Checkbox from '@material-ui/core/Checkbox';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import Button from '@material-ui/core/Button';
import {assignWorkout, fetchPTsPatients} from '../../Redux/actions/actions-pt';

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
}));

export const AssignWorkout = (props) => {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [checked, setChecked] = React.useState([]);

  React.useEffect(() => {
    props.fetchPTsPatients(props.pt.pt_id);
  }, []);

  const handleCheckToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  // console.log(checked);
  

  const assignToPatients = () => {
    props.assignWorkout(props.pt, checked, props.selectedWorkouts);
  };

  return (
    <div>
      <List
        component="nav"
        aria-label="workout-list"
        style={{maxHeight: 275, overflowY: 'scroll'}}
        subheader={
          <ListSubheader
            component="div"
            color="inherit"
            className={classes.sticky}
          >
            Assign To...
          </ListSubheader>
        }
      >
        {props.patients.map((p, k) => (
          <div key={k}>
            <ListItem>
              <ListItemText primary={`${p.f_name} ${p.l_name}`} />
              <ListItemSecondaryAction>
                <Checkbox
                  edge="end"
                  tabIndex={-1}
                  disableRipple
                  onChange={handleCheckToggle(p.patient_id)}
                  checked={checked.indexOf(p.patient_id) !== -1}
                  inputProps={{
                    'aria-labelledby': `checkbox-list-label-${p.patient_id}`,
                  }}
                />
              </ListItemSecondaryAction>
            </ListItem>
          </div>
        ))}
      </List>

      <Button variant="contained" color="inherit" onClick={assignToPatients}>
        ASSIGN
      </Button>
    </div>
  );
};
export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    pt: state.pt,
    // The state of the pt's patients, defined by reducer-pt
    patients: state.pt.patients,
    selectedWorkouts: state.exercises.selectedWorkouts,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    fetchPTsPatients: (pt_id) => dispatch(fetchPTsPatients(pt_id)),
    assignWorkout: (pt, checked, selectedWorkouts) =>
      dispatch(assignWorkout(pt, checked, selectedWorkouts)),
  }),
)(AssignWorkout);
