import React, { useState } from 'react';
import './app.css';
import StudentComponent from './student';
import SubjectComponent from './subject';
import CoursePlanComponent from './courseplan';
import ScheduleComponent from './schedule';

function Sidebar(props) {
  function switching(key) {
    props.onMenuClick(menuMap[key])
  }

  return (
    <ul className="menu">
      <li><button onClick={() => switching('Student')}>Students</button></li>
      <li><button onClick={() => switching('Subject')}>Subjects</button></li>
      <li><button onClick={() => switching('CousePlan')}>Course Plan</button></li>
      <li><button onClick={() => switching('Schedule')}>Schedule</button></li>
    </ul>
  );
}

const menuMap = {
  Student: <StudentComponent />,
  Subject: <SubjectComponent />,
  CousePlan: <CoursePlanComponent />,
  Schedule: <ScheduleComponent />,
};

function App() {
  const [currentMenu, setCurrentMenu] = useState(menuMap.Student);

  return (
    <div className="container mx-auto flex">
      <Sidebar onMenuClick={setCurrentMenu} />
      <div>
        {currentMenu}
      </div>
    </div>
  );
}

export default App;
