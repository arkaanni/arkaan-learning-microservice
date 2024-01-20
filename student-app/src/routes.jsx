import React from "react"
import { createBrowserRouter } from "react-router-dom"
import App from "./app"
import SchedulePage from "./schedule"
import CoursePlanPage from "./courseplan"
import ProfilePage from "./profile"

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "schedule",
        element: <SchedulePage />
      },
      {
        path: "course-plan",
        element: <CoursePlanPage />
      },
      {
        path: "profile",
        element: <ProfilePage />
      }
    ]
  }
])

export default router