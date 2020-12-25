import React from 'react';
// eslint-disable-next-line import/extensions
import {Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import {loginPT} from '../Redux/actions/actions-pt.js';
import Landing from './LandingPage/index';
// TODO maybe this should be in the component folder since we can just login straight in the
// landing page
const Login = (props) => {
  // essentially handles submit on loginForm
  const [error, setError] = React.useState('');

  const handleLogin = (data) => {
    props.loginPT(data);
    setError(props.errorCode);
  };
  console.log(error);
  return (
    <div>
      {props.pt.pt_id ? <Redirect to="/dashboard" /> : null}
      <Landing handleLogin={handleLogin} error={error} />
    </div>
  );
};

export default connect(
  (state) => ({
    // The state of the pt, as defined by reducer-pt
    pt: state.pt,
    errorCode: state.errorCode,
  }),
  (dispatch) => ({
    // The action from actions-pt which will effect reducer-pt
    loginPT: (data) => dispatch(loginPT(data)),
  }),
)(Login);
