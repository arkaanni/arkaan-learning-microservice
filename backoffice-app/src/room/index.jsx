import React, { useEffect } from "react"
import { Button, Card, Container, Grid, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material"
import api from "../api"

async function getRooms() {
  return fetch(`${api}/room`, {
    headers: {
      "accept": "application/json"
    },
    mode: "cors"
  }).then(res => res.json())
  .catch(e => console.log(e))
}

function RoomPage() {
  useEffect(() => {
    getRooms().then(data => {
      console.log(data)
    })
  }, [])

  return (
    <Container>
      <Grid container direction="column" gap={2}>
        <Grid item>
          <Button variant="contained">Add</Button>
        </Grid>
        <Grid>
          <Card variant="outlined">
            <Table>
              <TableHead>
                <TableRow style={{ fontWeight: 700}}>
                  <TableCell>#</TableCell>
                  <TableCell>Code</TableCell>
                  <TableCell>Category</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell>1</TableCell>
                  <TableCell>A2</TableCell>
                  <TableCell>Classroom</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </Card>
        </Grid>
      </Grid>
    </Container>
    )
  }
  
  export default RoomPage