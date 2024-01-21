import React from "react"
import { Box, Button, Card, CardContent, CardHeader, Container, Grid, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from "@mui/material"
import { useNavigate, Link } from "react-router-dom"
import api from "../api"

const days = ["day", "room", "class"]

function SubmitCoursePlanComponent() {
  const navigate = useNavigate()
  const formData = {
    student_id: "",
    subject_code: "",
    semester: "",
    year: "",
    schedule_id: "",
  }
  
  const changeInput = (e) => {
    formData[e.target.name] = e.target.value
  }
  
  const submitNewCoursePlan = () => {
    fetch(`${api}/course-plan`, {
      method: "post",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData),
      mode: "cors"
    }).then(res => {
      if (res.status == 200) {
        alert("Success")
        navigate("/course-plan")
      }
    }).catch(e => alert(e))
  }

  function CoursePlanTableHeader() {
    return (
      <TableRow>
        {days.map((it, i) => (
          <TableCell key={i}>{it}</TableCell>
        ))}
      </TableRow>
    )
  }
  
  return (
    <Container sx={{ padding: 4}}>
      <Card elevation={3}>
        <CardHeader title="Submit Course Plan" />
        <CardContent>
          <Box>
            <TextField variant="outlined" size="small" sx={{mx: 1}} />
            <Button variant="contained" sx={{mx: 1}}>Search</Button>
          </Box>
          <TableContainer>
            <Table>
              <TableHead>
                <CoursePlanTableHeader />
              </TableHead>
              <TableBody>
                <TableRow>
                  <TableCell>monday, 7am - 9am</TableCell>
                  <TableCell>A-1</TableCell>
                  <TableCell>A</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>monday, 8am - 10am</TableCell>
                  <TableCell>C-1</TableCell>
                  <TableCell>B</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>tuesday, 7am - 9am</TableCell>
                  <TableCell>A-1</TableCell>
                  <TableCell>C</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>tuesday, 1pm - 3pm</TableCell>
                  <TableCell>A-1</TableCell>
                  <TableCell>D</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>thursday, 7am - 9am</TableCell>
                  <TableCell>A-1</TableCell>
                  <TableCell>E</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
                <TableRow>
                  <TableCell>friday, 7am - 9am</TableCell>
                  <TableCell>A-1</TableCell>
                  <TableCell>F</TableCell>
                  <TableCell>
                    <Button variant="contained">Select</Button>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </CardContent>
      </Card>
    </Container>
    )
  }
  
  export default SubmitCoursePlanComponent