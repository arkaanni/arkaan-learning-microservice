import 'package:backoffice/features/student/student.dart';
import 'package:dio/dio.dart';

class StudentDao {
  final Dio _dio;

  StudentDao({required Dio dio}) : _dio = dio;

  Stream<Student> getStudents() async* {
    var response = await _dio.get("/student");
    var data = response.data;
    for (dynamic s in data) {
       yield Student.fromJson(s);
    }
  }
}
