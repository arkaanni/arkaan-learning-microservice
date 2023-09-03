import { useEffect, useState } from "react";

async function getAllStudents() {
  return await fetch('http://localhost:8443/students')
    .then(res => res.json());
}

function StudentComponent() {
  const [students, setStudents] = useState([]);

  useEffect(() => {
    getAllStudents().then(data => {
      setStudents(data);
    });
  }, []);

  return (
    <>
      <div>
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Semester</th>
            </tr>
          </thead>
          <tbody>
            {students.map(st => (
              <tr key={st.student_id}>
                <td>{st.student_id}</td>
                <td>{st.first_name + " " + st.last_name}</td>
                <td>{st.semester}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default StudentComponent;