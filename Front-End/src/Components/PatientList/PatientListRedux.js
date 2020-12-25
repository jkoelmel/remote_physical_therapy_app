import React from 'react';
import {connect} from 'react-redux';
import {ListItem, ListItemText} from '@material-ui/core';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListSubheader from '@material-ui/core/ListSubheader';
import {makeStyles} from '@material-ui/core/styles';
import {getPatients} from '../../Redux/actions/patientActions';

const useStyles = makeStyles((theme) => ({
    sticky: {
        backgroundColor: 'white',
        fontSize: 18,
    },
    subheader: {
        fontSize: 18,
    },
    patientList: {},
}));

const PatientListRedux = (props) => {
    const classes = useStyles();
    const {patients} = props.patients;
    console.log(props.patients); // Test

    React.useEffect(() => {
        // props.getPatients()
    }, [getPatients]);

    return (
        <div>
            <List
                component="nav"
                aria-label="patient-list"
                className={classes.patientList}
                subheader={
                    <ListSubheader
                        component="div"
                        color="inherit"
                        className={classes.sticky}
                    >
                        Patient List
                    </ListSubheader>
                }
            >
                <Divider/>
                {patients.map((p) => (
                    <React.Fragment key={p.patient_id}>
                        <ListItem key={p.patient_id} button>
                            <ListItemText primary={`${p.f_name} ${p.l_name}`}/>
                        </ListItem>
                        <Divider/>
                    </React.Fragment>
                ))}
            </List>
        </div>
    );
};

function mapStateToProps(state) {
    return {
        patients: state.patients,
    };
}

// function mapDispatchToProps(dispatch){
//     return bindActionCreators({getPatients: getPatients},dispatch)
// }

export default connect(mapStateToProps, {getPatients})(PatientListRedux);
