import { useEffect, useState } from "react";
import api from "../api";
import { Container } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";

async function getAllSubject() {
  return await fetch(`${api}/subject`)
    .then(res => res.json());
}

function SubjectComponent() {
  const [subjects, setSubjects] = useState([]);
  
  useEffect(() => {
    getAllSubject().then(data => {
      setSubjects(data.map((value, index) => ({no: index + 1, ...value})));
    });
  }, []);
  
  const columns = [
    { field: 'no', headerName: 'No.'},
    { field: 'subjectCode', headerName: 'Code', flex: 0.5, sortable: false },
    {
      field: 'name',
      headerName: 'Name',
      flex: 1
    },
    { field: 'description', headerName: 'Description', flex: 2, sortable: false },
  ]
  
  return (
    <Container>
      <DataGrid
        rows={subjects}
        columns={columns}
        getRowId={row => row.subjectCode}
        />
    </Container>
  );
}

export default SubjectComponent;