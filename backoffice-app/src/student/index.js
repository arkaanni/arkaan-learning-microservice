import { useEffect, useState } from "react";
import api from "../api";
import { Button, Container, Grid } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { Link } from 'react-router-dom'

async function getAllStudents() {
  return await fetch(`${api}/students`)
    .then(res => res.json());
}

function StudentComponent() {
  const [students, setStudents] = useState([]);

  useEffect(() => {
    getAllStudents().then(data => {
      setStudents(data.map((value, index) => ({no: index + 1, ...value})));
    });
  }, []);

  const columns = [
    { field: 'no', headerName: 'No.'},
    { field: 'student_id', headerName: 'ID', flex: 1, sortable: false },
    {
      field: 'name',
      headerName: 'Name',
      valueGetter: (params) => `${params.row.first_name} ${params.row.last_name}`,
      flex: 2
    },
    { field: 'semester', headerName: 'Semester', flex: 0.5, sortable: false },
  ]

  return (
    <Container>
      <Grid container direction='column' gap={2}>
        <Grid item flex={1}>
          <Button variant='contained' component={Link} to='add' color='primary'>ADD</Button>
        </Grid>
        <Grid item flex={4}>
          <DataGrid
            rows={students}
            columns={columns}
            getRowId={row => row.student_id}
          />
        </Grid>
      </Grid>
    </Container>
  );
}

export default StudentComponent;