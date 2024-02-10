import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './src/app';
import {
  createBrowserRouter,
  RouterProvider
} from 'react-router-dom'
import { CssBaseline } from '@mui/material';
import StudentComponent from './src/student';
import SubjectComponent from './src/subject';
import CoursePlanComponent from './src/courseplan';
import ScheduleComponent from './src/schedule';
import AddStudentForm from './src/student/add_form';
import RoomPage from './src/room';
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

const domNode = ReactDOM.createRoot(document.getElementById('app'));
domNode.render(
  <React.StrictMode>
      <CssBaseline />
      <RouterProvider router={router} />
  </React.StrictMode>
);