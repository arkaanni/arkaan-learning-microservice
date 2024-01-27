import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import {
  createBrowserRouter,
  RouterProvider
} from 'react-router-dom'
import { CssBaseline } from '@mui/material';
import StudentComponent from './student';
import SubjectComponent from './subject';
import CoursePlanComponent from './courseplan';
import ScheduleComponent from './schedule';
import AddStudentForm from './student/add_form';
import RoomPage from './room';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

const router = createBrowserRouter([
  {
    path: '/',
    Component: App,
    children: [
      {
        path: 'subject',
        Component: SubjectComponent
      },
      {
        path: 'student',
        Component: StudentComponent,
      },
      {
        path: 'course-plan',
        Component: CoursePlanComponent
      },
      {
        path: 'room',
        Component: RoomPage
      },
      {
        path: 'schedule',
        Component: ScheduleComponent
      },
      {
        path: 'student/add',
        Component: AddStudentForm
      }
    ]
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <CssBaseline />
      <RouterProvider router={router} />
  </React.StrictMode>
);