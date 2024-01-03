import { Box, Button, Card, CardContent, CardHeader, Container, FormControl, Grid, Input, TextField, Typography } from "@mui/material"
import React from "react"
import { Link, useNavigate } from "react-router-dom"
import api from "../api"

function AddStudentForm() {
  const navigate = useNavigate()
  const formData = {
    first_name: "",
    last_name: "",
    student_id: "",
    phone: "",
    semester: "",
    address: ""
  }

  const changeInput = (e) => {
    formData[e.target.name] = e.target.value
  }

  const submitNewStudent = () => {
    fetch(`${api}/students`, {
      method: "post",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData),
      mode: "cors"
    }).then(res => {
      if (res.status == 200) {
        alert("Success")
        navigate("/student")
      }
    }).catch(e => alert(e))
  }

  return (
    <Container maxWidth="sm">
      <Card>
        <CardContent>
          <Grid container gap={4} direction='column' p={3} component="form">
            <Grid container gap={2}>
              <Grid item flex={1}>
                <TextField fullWidth variant="standard" label="first_name" name="first_name" required onChange={changeInput} />
              </Grid>
              <Grid item flex={1}>
                <TextField fullWidth variant="standard" label="last_name" name="last_name" required onChange={changeInput} />
              </Grid>
            </Grid>
            <TextField variant="standard" label="student_id" name="student_id" type="number"required onChange={changeInput} />
            <TextField variant="standard" label="phone" name="phone" type="tel" required onChange={changeInput} />
            <TextField variant="standard" label="semester" name="semester" type="number" required onChange={changeInput} />
            <TextField variant="standard" label="address" name="address" rows={4} multiline required onChange={changeInput} />
            <Grid container direction="row" gap={4}>
              <Grid item flex={1}>
                <Button fullWidth variant="contained" color="error" component={Link} to="/student">CANCEL</Button>
              </Grid>
              <Grid item flex={1}>
                <Button fullWidth variant="contained" onClick={submitNewStudent}>SAVE</Button>
              </Grid>
            </Grid>
          </Grid>
        </CardContent>
      </Card>
    </Container>
  )
}

export default AddStudentForm