import React from 'react';
import {TextField, FormControl} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import {connect} from 'react-redux';
import {EditPT} from '../../Redux/actions/actions-pt';
import Grid from '@material-ui/core/Grid';
import {Button} from '@material-ui/core';
import {Formik, Form} from 'formik';

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    large: {
        width: 100,
        height: 100,
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

const PTInfo = (props) => {
    const classes = useStyles();
    //Testing the Description on pT
    console.log(props.description)

    return (
        <div>

            <React.Fragment>
                <Formik
                    initialValues={{
                        email: props.pt.email,
                        pt_id: props.pt.pt_id,
                        password: props.pt.password,
                        f_name: props.pt.f_name,
                        l_name: props.pt.l_name,
                        description: props.pt.description,
                        company: props.pt.company
                    }}
                    onSubmit={async (data, {setSubmitting}) => {
                        console.log(data);
                        setSubmitting(true);
                        // make async call
                        await props.EditPT(data);
                        setSubmitting(false);
                    }}
                >
                    {({
                          values,
                          isSubmitting,
                          handleChange,
                          handleBlur,
                          touched,
                          errors,
                      }) => (

                        <Form className={classes.form}>
                            <Grid container direction="column" spacing={3} alignItems="center">

                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="Email"
                                        name="email"
                                        placeholder={props.pt.email}
                                        variant="outlined"
                                        color="secondary"
                                        value={values.email}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                    />
                                </Grid>
                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="Password"
                                        name="password"
                                        placeholder={props.pt.password}
                                        variant="outlined"
                                        color="secondary"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        type="password"
                                        value={values.password}
                                    />
                                </Grid>
                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="First Name"
                                        name="f_name"
                                        placeholder={props.pt.f_name}
                                        variant="outlined"
                                        color="secondary"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.f_name}
                                    />
                                </Grid>
                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="Last Name"
                                        name="l_name"
                                        placeholder={props.pt.l_name}
                                        variant="outlined"
                                        color="secondary"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.l_name}
                                    />
                                </Grid>
                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="Company"
                                        name="company"
                                        placeholder={props.pt.company}
                                        variant="outlined"
                                        color="secondary"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.company}
                                    />
                                </Grid>
                                <Grid item>
                                    <TextField
                                        id="outlined-error"
                                        label="Description"
                                        name="description"
                                        placeholder={props.pt.description}
                                        variant="outlined"
                                        color="secondary"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.description}
                                    />
                                </Grid>
                            </Grid>
                            <Grid item>

                            </Grid>
                            <FormControl>
                                <Button
                                    disabled={isSubmitting}
                                    // variant='contained'
                                    color="secondary"
                                    type="submit"
                                    style={{marginTop: '3rem'}}
                                >
                                    Update Profile
                                </Button>
                            </FormControl>
                        </Form>
                    )}
                </Formik>
            </React.Fragment>


        </div>
    )
}

export default connect(
    (state) => ({
        pt: state.pt,

    }),
    (dispatch) => ({
        // The action from actions-pt which will effect reducer-pt
        EditPT: (data) => dispatch(EditPT(data)),
    }),
)
(PTInfo);