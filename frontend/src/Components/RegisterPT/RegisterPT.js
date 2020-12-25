import {Typography} from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Paper from '@material-ui/core/Paper';
import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import {Button,FormControl} from '@material-ui/core';
import {Formik, Form} from 'formik';
import {connect} from 'react-redux';
import {createNewPT} from '../../Redux/actions/actions-pt';
const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: 'white',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: 550,
        width: 500,
        padding: 10,
    },
    textfield: {
        // borderColor: theme.palette.secondary.main
    },
}));

const RegisterPT = (props) => {
    const classes = useStyles();
    const [emailError, setEmailError] = React.useState(false);
    const [passwordError, setPasswordError] = React.useState(false);

    return (
        <div>
            <React.Fragment>
                <Formik initialValues={{email: "",pt_id: "",password: "",f_name: "", l_name: "",description: "",company: ""}}
                        onSubmit={async (data, {setSubmitting}) => {
                            console.log(data);
                            setSubmitting(true);
                            // make async call
                            await props.createNewPT(data);
                            setSubmitting(false);
                        }}
                >
                    {({
                          values,
                          isSubmitting,
                          handleChange,
                          handleBlur
                      }) => (
                        <Form className={classes.form}>
                            <Paper className={classes.root}>
                                <Grid container direction="column" spacing={3} alignItems="center">
                                    <Grid item>
                                        <TextField
                                            id="outlined-error"
                                            label="Email"
                                            name="email"
                                            placeholder={props.pt.email}
                                            defaultValue=""
                                            variant="outlined"
                                            color="secondary"
                                            value={values.email}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <TextField
                                            error={passwordError}
                                            id="outlined-error"
                                            label="Password"
                                            name="password"
                                            helperText={props.errorCode}
                                            placeholder={''}
                                            variant="outlined"
                                            color="secondary"
                                            type="password"
                                            value={values.password}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <TextField
                                            error={passwordError}
                                            id="outlined-error"
                                            label="First Name"
                                            name="f_name"
                                            defaultValue=""
                                            variant="outlined"
                                            color="secondary"
                                            value={values.f_name}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <TextField
                                            error={passwordError}
                                            id="outlined-error"
                                            label="Last Name"
                                            name="l_name"
                                            defaultValue=""
                                            variant="outlined"
                                            color="secondary"
                                            value={values.l_name}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <TextField
                                            error={passwordError}
                                            id="outlined-error"
                                            label="Company"
                                            name="company"
                                            defaultValue=""
                                            variant="outlined"
                                            color="secondary"
                                            value={values.company}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <TextField
                                            error={passwordError}
                                            id="outlined-error"
                                            label="Description"
                                            name="description"
                                            defaultValue=""
                                            variant="outlined"
                                            color="secondary"
                                            value={values.description}
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                        />
                                    </Grid>
                                    <FormControl>
                                        <Button
                                            disabled={isSubmitting}
                                            // variant='contained'
                                            color="secondary"
                                            type="submit"
                                            style={{marginTop: '3rem'}}
                                        >
                                            Register Profile
                                        </Button>
                                    </FormControl>

                                </Grid>
                            </Paper>
                        </Form>
                    )}
                </Formik>
            </React.Fragment>
        </div>
    );
};

export default connect(
    (state) => ({
        pt: state.pt,

    }),
    (dispatch) => ({
        // The action from actions-pt which will effect reducer-pt
        createNewPT: (data) => dispatch( createNewPT(data)),
    }),

)
(RegisterPT);
