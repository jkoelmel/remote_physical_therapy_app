import React from "react";
import { Redirect, Link } from "react-router-dom";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import AccountCircle from "@material-ui/icons/AccountCircle";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import logo from "../../Assets/Images/logo_with_text.svg";
import {connect} from "react-redux";
import {loginPT, logoutPT} from "../../Redux/actions/actions-pt";

// TODO when you click the logo, redirect to dashboard
const useStyles = makeStyles((theme) => ({
  root: {
    margin: 3,
    flexGrow: 1,
  },
  menuButton: {
    marginLeft: 10,
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  logo: {
    width: 200,
  },
  accountCircle: {
    marginLeft: 'auto',
    marginRight: 10,
  },
}));

export const Header = (props) => {
  const classes = useStyles();
  const [auth, setAuth] = React.useState(true);
  const [anchorElLeft, setAnchorElLeft] = React.useState(null);
  const [anchorElRight, setAnchorElRight] = React.useState(null);
  const openLeft = Boolean(anchorElLeft);
  const openRight = Boolean(anchorElRight);

  const logout = () => {
    props.logoutPT(props.pt);
    window.location.href = '/';
  };

  const handleChange = (event) => {
    setAuth(event.target.checked);
  };

  const handleMenu = (event) => {
    setAnchorElLeft(event.currentTarget);
  };

  const handleClick = (event) => {
    setAnchorElRight(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorElLeft(null);
    setAnchorElRight(null);
  };
  const handleRedirect = () => {
    setAnchorElLeft(null);
    setAnchorElRight(null);
    if (anchorElLeft == null || anchorElRight == null)
      return <Redirect to="/dashboard" />;
  };

  return (
    <div className={classes.root}>
      <AppBar position="fixed">
        <Toolbar disableGutters>
          <div className={classes.menuButton}>
            <IconButton
              disabled={props.pt.email === '' ? true :false}
              edge="start"
              aria-label="drop-down"
              aria-controls="simple-menu"
              aria-haspopup="true"
              onClick={handleMenu}
              color="secondary"
            >
              <MenuIcon color="secondary" />
            </IconButton>
            <Menu
              id="simple-menu"
              anchorEl={anchorElLeft}
              getContentAnchorEl={null}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'center',
              }}
              open={openLeft}
              onClose={handleClose}
            >
              <MenuItem onClick={handleClose} component={Link} to="/dashboard">
                Dashboard
              </MenuItem>
              <MenuItem onClick={handleClose} component={Link} to="/library">
                Exercise Library
              </MenuItem>
              <MenuItem onClick={handleClose} component={Link} to="/PTProfile">
                PT Settings
              </MenuItem>
            </Menu>
          </div>

          <Button 
          disableTouchRipple={true}
           href="/"
           disabled={props.pt.email === '' ? true :false}
           >
            <img alt="company logo" src={logo} className={classes.logo} />
          </Button>
          {auth && (
            <div className={classes.accountCircle}>
              <IconButton
                disabled={props.pt.email === '' ? true :false}
                edge="start"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleClick}
                color="secondary"
              >
                <AccountCircle />
              </IconButton>
              
              <Menu
                id="menu-appbar"
                anchorEl={anchorElRight}
                getContentAnchorEl={null}
                anchorOrigin={{
                  vertical: 'center',
                  horizontal: 'left',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={openRight}
                onClose={handleClose}
              >
                <MenuItem onClick={handleClose} component={Link} to="/settings">
                  My Profile
                </MenuItem>
                <MenuItem onClick={logout}>Logout</MenuItem>
              </Menu>
            </div>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default connect(
  (state) => ({
    pt: state.pt,
  }),
  (dispatch) => ({
    // May be used if we add a login to the dropdown
    loginPT: (data) => dispatch(loginPT(data)),
    logoutPT: (pt) => dispatch(logoutPT(pt)),
  }),
)(Header);
