import React from 'react';
import List from '@material-ui/core/List';
import {ListItem, ListItemText, ListSubheader, Divider} from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import {makeStyles} from '@material-ui/core/styles';
import 'date-fns';
import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';

import axios from 'axios';

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        //   border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
        outline: 'none',
    },
}));

// TODO CLEAN UP FILE AND FIX DATE PICKER
const SearchPlan = ({
                        patients,
                        setPatients,
                        selectedPatient,
                        setSelectedPatient,
                    }) => {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [exercisePlan, setExercisePlan] = React.useState([]);
    const [workoutbyDate, setWorkoutByDate] = React.useState([]);
    const [start, setStart] = React.useState('2020-06-01');
    const [end, setEnd] = React.useState('2020-06-01');
    const [startYear, setStartYear] = React.useState('');
    const [startMonth, setStartMonth] = React.useState('');
    const [startDay, setStartDay] = React.useState('');
    const [endYear, setEndYear] = React.useState('');
    const [endMonth, setEndMonth] = React.useState('');
    const [endDay, setEndDay] = React.useState('');
    const [readySearch, setReadySearch] = React.useState(false);

    const [values, setValues] = React.useState({textmask: '    -   -   '});
    const [selectedDate, setSelectedDate] = React.useState(
        new Date('2014-08-18T21:11:54'),
    );

    const handleStartDateChange = (date) => {
        setStart(date);
        console.log('start: ', start);
    };
    const handleEndDateChange = (date) => {
        setEnd(date);
    };

    const fetchWorkouts = () => {
        axios
            .get('api/assign/all', {
                params: {
                    patient: selectedPatient,
                    start,
                    end,
                },
            })
            .then((response) => {
                console.log(response.data);
                setExercisePlan(
                    response.data.map((e) => e),
                );
            })
            .catch(console.log);
    };
    const handleClose = () => {
        setOpen(false);
    };

    const handleSearch = () => {
        setStart(`${startYear}-${startMonth}-${startDay}`);
        setEnd(`${endYear}-${endMonth}-${endDay}`);
    };
    console.log(start);
    console.log(end);

    console.log(
        exercisePlan.map((exercisePlan) => exercisePlan),
    );

    const ExercisePlan = [
        {title: 'Leg Exercise'},
        {title: 'Back Exercise'},
        {title: 'Acl Rehabilitation'},
    ];
    const handleReadySearch = () => {
        setReadySearch(true);
        setOpen(true);
        // handleSearch()
        handleStartDateChange();
        handleEndDateChange();
    };
    console.log(readySearch);

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    };

    React.useEffect(() => {
        if (readySearch) fetchWorkouts();
    }, [selectedPatient, readySearch]);

    return (
        <div>
            <div style={{width: 'auto'}}>
                {/* <FormControl>
            <InputLabel htmlFor="formatted-text-mask-input">Start Date</InputLabel>
            <Input
              value={values.textmask}
              onChange={handleChange}
              name="textmask"
              id="formatted-text-mask-input"
              
            />
      </FormControl>
      <TextField
        label="YYYY-MM-DD"
        value={values.numberformat}
        onChange={handleChange}
        name="numberformat"
        id="formatted-numberformat-input"
        InputProps={{
        }}
      /> */}
                {/* <TextField
            label="Start Year"
            id="outlined-margin-none"
            // defaultValue="Default Value"
            helperText="YYYY"
            variant="outlined"
            onChange={(e)=>{setStartYear(e.target.value)}}
        />
         <TextField
            label="Start Month"
            id="outlined-margin-none"
            // defaultValue="Default Value"
            helperText="MM"
            variant="outlined"
            onChange={(e)=>{setStartMonth(e.target.value)}}
        />
        <TextField
          label="Start Day"
          id="outlined-margin-none"
          // defaultValue="Default Value"
          helperText="DD"
          variant="outlined"
          onChange={(e)=>{setStartDay(e.target.value)}}
        />
          <TextField
            label="End Year"
            id="outlined-margin-none"
            // defaultValue="Default Value"
            helperText="YYYY"
            variant="outlined"
            onChange={(e)=>{setEndYear(e.target.value)}}
        />

         <TextField
            label="End Month"
            id="outlined-margin-none"
            // defaultValue="Default Value"
            helperText="MM"
            variant="outlined"
            onChange={(e)=>{setEndMonth(e.target.value)}}
        />
        <TextField
          label="End Day"
          id="outlined-margin-none"
          // defaultValue="Default Value"
          helperText="DD"
          variant="outlined"
          onChange={(e)=>{setEndDay(e.target.value)}}
        />
        
       <Button onClick= {handleReadySearch} color="secondary">Search</Button>
       */}
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <KeyboardDatePicker
                        disableToolbar
                        variant="inline"
                        format="yyyy/mm/dd"
                        margin="normal"
                        id="date-picker-inline"
                        label="Start Date"
                        value={start}
                        onChange={handleStartDateChange}
                        KeyboardButtonProps={{
                            'aria-label': 'change date',
                        }}
                    />
                    <KeyboardDatePicker
                        disableToolbar
                        variant="inline"
                        format="yyyy/mm/dd"
                        margin="normal"
                        id="date-picker-inline"
                        label="End Date"
                        value={end}
                        onChange={handleEndDateChange}
                        KeyboardButtonProps={{
                            'aria-label': 'change date',
                        }}
                    />
                </MuiPickersUtilsProvider>
                <Button onClick={handleReadySearch} color="secondary">
                    Search
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
                                <ListSubheader
                                    component="div"
                                    color="inherit"
                                    classes={'patient-list'}
                                >
                                    Patient Workouts
                                </ListSubheader>
                            }
                        >
                            <ListSubheader>{`${start} to ${end}`}</ListSubheader>
                            <Divider/>
                            {exercisePlan.map((e) => (
                                <div>
                                    <ListItem>
                                        <ListItemText primary={`Title`} secondary={`${e.title}`}/>
                                    </ListItem>
                                    <ListItem>
                                        <ListItemText
                                            primary={`URL`}
                                            secondary={`${e.exercise_url}`}
                                        />
                                    </ListItem>
                                    <ListItem>
                                        <ListItemText
                                            primary={`description`}
                                            secondary={`${e.description}`}
                                        />
                                    </ListItem>
                                    <Divider/>
                                </div>
                            ))}
                        </List>
                    </div>
                </Fade>
            </Modal>
        </div>
    );
};

export default SearchPlan;
