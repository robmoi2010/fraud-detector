import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Container from '@material-ui/core/Container';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import DashboardIcon from '@material-ui/icons/Dashboard';
import BarChartIcon from '@material-ui/icons/BarChart';
import AssignmentIcon from '@material-ui/icons/Assignment';
import FolderIcon from '@material-ui/icons/Folder'
import ViewListIcon from '@material-ui/icons/ViewList'
import PowerSettingsNewIcon from '@material-ui/icons/PowerSettingsNew';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import PeopleIcon from '@material-ui/icons/People';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import { Link } from "react-router-dom";
import Router from './Router';
import { config } from './components/configuration/config';
import SignInService from './components/signin/SignInService';
import PermissionService from './components/permission/PermissionService';

const drawerWidth = 270;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  menuLink: {
    textDecoration: "none",
    color: theme.palette.text.primary,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
  },
}));
export function SuperAdminSection(props) {
  return (
    <div>
      <Link to={config.RolesRouteURL} className={props.classes.menuLink}>
        <ListItem button>
          <ListItemIcon>
            <AssignmentIndIcon />
          </ListItemIcon>
          <ListItemText primary="Roles" />
        </ListItem>
      </Link>
      <Link to={config.PaybillsRouteURL} className={props.classes.menuLink}>
        <ListItem button>
          <ListItemIcon>
            <AssignmentIndIcon />
          </ListItemIcon>
          <ListItemText primary="Paybills" />
        </ListItem>
      </Link>
    </div>);
}
export function AdminSection(props) {
  const permissionService = new PermissionService();
  const isSuperAdmin = permissionService.hasRole(props.user, config.SuperAdminRoleName, true);
  return (
    <div>
      < Divider />
      <List>
        <ListSubheader inset>Admin</ListSubheader>
        <Link to={config.UsersRouteURL} className={props.classes.menuLink}>
          <ListItem button>
            <ListItemIcon>
              <PeopleIcon />
            </ListItemIcon>
            <ListItemText primary="Users" />
          </ListItem>
        </Link>
        {isSuperAdmin ? <SuperAdminSection {...props} /> : <span></span>}
      </List>
    </div >
  );
}
export default function MainNavigation(props) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(true);
  const signInService = new SignInService();
  const permissionService = new PermissionService();
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleClick = (e) => {

  }
  const handleLogOut = () => {
    signInService.clearData();
  }
  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
          >
            <MenuIcon />
          </IconButton>
          <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
            {props.selectedTab}
          </Typography>
          <IconButton color="inherit">
            <PersonPinIcon />
            {props.userToken.user.firstName}
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>
          <Link to={config.DashboardRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("Dashboard")}>
              <ListItemIcon>
                <DashboardIcon />
              </ListItemIcon>
              <ListItemText primary="Dashboard" />
            </ListItem>
          </Link>
          <Link to={config.FilesRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("Files")}>
              <ListItemIcon>
                <FolderIcon />
              </ListItemIcon>
              <ListItemText primary="Files" />
            </ListItem>
          </Link>
          <Link to={config.FileTransRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("File Transactions")}>
              <ListItemIcon>
                <ViewListIcon />
              </ListItemIcon>
              <ListItemText primary="File Transactions" />
            </ListItem>
          </Link>
          <Link to={config.FraudulentTransRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("Fraudulent Transactions")}>
              <ListItemIcon>
                <BarChartIcon />
              </ListItemIcon>
              <ListItemText primary="Fraudulent Transactions" />
            </ListItem>
          </Link>
          <Link to={config.MissingTransRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("Missing Transactions")}>
              <ListItemIcon>
                <BarChartIcon />
              </ListItemIcon>
              <ListItemText primary="Missing Transactions" />
            </ListItem>
          </Link>
          <Link to={config.MainTransRouteURL} className={classes.menuLink}>
            <ListItem button onClick={() => handleClick("Main Transactions")}>
              <ListItemIcon>
                <ViewListIcon />
              </ListItemIcon>
              <ListItemText primary="Main Transactions" />
            </ListItem>
          </Link>
        </List>
        <Divider />
        {permissionService.hasRole(props.userToken.user, config.AdminIdentifier, false) ? (<AdminSection user={props.userToken.user} classes={classes} />) : (<span />)}
        <Divider />
        <List>
          <ListSubheader inset>Saved reports</ListSubheader>
          <ListItem button>
            <ListItemIcon>
              <AssignmentIcon />
            </ListItemIcon>
            <ListItemText primary="Current month" />
          </ListItem>
          <ListItem button>
            <ListItemIcon>
              <AssignmentIcon />
            </ListItemIcon>
            <ListItemText primary="Last quarter" />
          </ListItem>
          <ListItem button>
            <ListItemIcon>
              <AssignmentIcon />
            </ListItemIcon>
            <ListItemText primary="Year-end" />
          </ListItem>
        </List>
        <Divider />
        <Link to={config.SignInRouteURL} className={classes.menuLink}>
          <List>
            <ListItem button onClick={() => handleLogOut()}>
              <ListItemIcon>
                <PowerSettingsNewIcon />
              </ListItemIcon>
              <ListItemText primary="Logout" />
            </ListItem>
          </List>
        </Link>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container maxWidth="lg" className={classes.container}>
          <Router {...props} accessToken={props.userToken.token.access_token} currentComponent={props.selectedTab} />
        </Container>
      </main>
    </div>
  );
}

