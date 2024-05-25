import 'package:backoffice/core/dao_response.dart';
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

  Future<DaoResponse> addStudent(Student student) async {
    return _dio.post("/student", data: student.toJson(), options: Options(
      headers: {
        "content-type": "application/json"
      }
    )).then((response) =>
        DaoResponse(null, Status.success, "Student added", null)
    ).catchError((error) {
      var message = (error as DioException).response!.data;
      return DaoResponse(null, Status.failed, message, null);
    });
  }
}
