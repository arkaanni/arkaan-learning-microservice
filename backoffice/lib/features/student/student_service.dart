import 'package:backoffice/features/student/dao/student_dao.dart';
import 'package:backoffice/features/student/student.dart';

class StudentService {
  final StudentDao _dao;

  StudentService({required StudentDao dao}) : _dao = dao;

  Stream<Student> getStudents() => _dao.getStudents();
}