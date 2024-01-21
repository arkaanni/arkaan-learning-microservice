import { Button } from "@mui/material"
import React from "react"
import { Link } from "react-router-dom"

function CoursePlanPage() {
  return (
    <>
      <h1>todo: course-plan page</h1>
      <Button component={Link} to="/course-plan/create">Create</Button>
    </>
  )
}

export default CoursePlanPage