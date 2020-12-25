import React from 'react';
import axios from 'axios';
import List from '@material-ui/core/List';
import {
    ListItem
} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        outline: 'none',
    },
    sticky: {
        backgroundColor: 'white',
    },
}));

export const ActivitySummary = ({selectedPatient}) => {
    const classes = useStyles();
    const [activity, setActivity] = React.useState([]);

    const fetchSummaryInfo = () => {
        axios
            .get('api/pt/patient-activity', {
                params: {
                    patient: 1,
                    pt: 100,
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

    React.useEffect(() => {
        // will load patients-PT activity summary when the page loads
        fetchSummaryInfo();
    }, []);

    React.useEffect(() => {
        // will load patients-PT activity summary when the page loads
        if (selectedPatient != '') fetchSummaryInfo();
    }, [selectedPatient]);

    return (
        <List
            className={classes.paper}
            aria-label="activity-list"
            style={{maxHeight: 300}}
        >
            <ListItem className={classes.modal}>
                <u>
                    <b>Activity : Minutes</b>
                </u>
            </ListItem>
            {activity.map((a) => (
                <div>
                    <ListItem>{`${a.type_activity} : ${a.duration}`}</ListItem>
                </div>
            ))}
        </List>
    );
};

export default ActivitySummary;
