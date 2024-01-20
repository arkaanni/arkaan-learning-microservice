import React from "react"
import { createRoot } from "react-dom/client"
import { RouterProvider } from "react-router-dom"
import router from "./src/routes"
import { CssBaseline } from "@mui/material"

const domNode = document.getElementById("app")
const root = createRoot(domNode)
root.render(
  <React.StrictMode>
    <CssBaseline />
    <RouterProvider router={router} />
  </React.StrictMode>
)