import React from "react"
import { createRoot } from "react-dom/client"

function App() {
  return (
    <h1>App</h1>
  )
}

const domNode = document.getElementById("app")
const root = createRoot(domNode)
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)