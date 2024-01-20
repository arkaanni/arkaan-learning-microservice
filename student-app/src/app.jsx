import { Container, Grid, List, ListItem, ListItemButton, ListItemText } from "@mui/material"
import React from "react"
import { Link, Outlet, useLocation } from "react-router-dom"

const menu = ["schedule", "course-plan", "profile"]

function App() {
  const location = useLocation()
  return (
    <Grid container>
      <Grid item>
        <List>
          {menu.map(it => (
            <ListItem key={it}>
              <ListItemButton
                component={Link}
                to={it}
                selected={location.pathname.indexOf(it) > -1}
              >
                <ListItemText primary={it} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Grid>
      <Grid item>
        <Outlet />
      </Grid>
    </Grid>
  )
}

export default App