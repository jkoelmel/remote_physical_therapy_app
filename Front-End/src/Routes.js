import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Landing from './Pages/LandingPage/index';
import Dashboard from './Pages/Dashboard/Dashboard';
import LoginPage from './Pages/LoginPage/LoginPage';
import Profile from './Pages/PatientProfile/Profile';
import Exercise from './Pages/ExerciseLibrary/Exercise';
import PTProfile from './Pages/PTProfile/PTProfile';
import Login from './Pages/Login';
// Will handle all page routing
// TODO ask if we still need to keep about me pages
export const Routes = () => (
  <Switch>
    <Route exact={true} path="/">
      <Login />
    </Route>
    <Route exact={true} path="/dashboard">
      <Dashboard />
    </Route>
    <Route exact={true} path="/loginpage">
      <LoginPage />
    </Route>
    <Route exact={true} path="/profile">
      <Profile />
    </Route>
    <Route exact={true} path="/library">
      <Exercise />
    </Route>
    <Route exact={true} path="/PTProfile">
      <PTProfile />
    </Route>
  </Switch>
);
