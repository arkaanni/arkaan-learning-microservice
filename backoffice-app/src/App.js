import React from 'react'
import { Grid, List, ListItem, ListItemButton, ListItemText } from '@mui/material'
import { Link, Outlet, useLocation } from 'react-router-dom'

const mainMenu = [ "student", "subject", "course-plan", "room", "schedule"]

function Sidebar() {
  const location = useLocation()
  return (
    <List>
      {mainMenu.map(it =>
        <ListItem key={it}>
          <ListItemButton
            component={Link}
            to={it}
            selected={location.pathname.indexOf(it) > -1}
            >
            <ListItemText primary={it} />
          </ListItemButton>
        </ListItem>
      )}
    </List>
  )
}


function App() {
  return (
    <Grid container direction="row">
      <Grid item flex={0.5}>
        <Sidebar />
      </Grid>
      <Grid item flex={4} paddingTop={2}>
        <Outlet />
      </Grid>
    </Grid>
  );
}

export default App;
