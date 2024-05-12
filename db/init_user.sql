CREATE USER IF NOT EXISTS 'student_service'@'%' IDENTIFIED BY 'student_service';
CREATE USER IF NOT EXISTS 'subject_service'@'%' IDENTIFIED BY 'subject_service';
CREATE USER IF NOT EXISTS 'room_service'@'%' IDENTIFIED BY 'room_service';
CREATE USER IF NOT EXISTS 'course_plan_service'@'%' IDENTIFIED BY 'course_plan_service';

GRANT ALL ON student.* TO 'student_service'@'%';
GRANT ALL ON subject.* TO 'subject_service'@'%';
GRANT ALL ON room.* TO 'room_service'@'%';
GRANT ALL ON course_plan.* TO 'course_plan_service'@'%';

FLUSH PRIVILEGES;
