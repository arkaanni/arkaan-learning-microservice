import 'package:backoffice/features/subject/subject.dart';
import 'package:backoffice/core/dao_response.dart';
import 'package:dio/dio.dart';

class SubjectDao {
  final Dio _dio;

  SubjectDao(this._dio);

  Stream<Subject> getSubjects() async* {
    var response = await _dio.get("/subject");
    for (Map<String, dynamic> d in response.data) {
      yield Subject.fromJson(d);
    }
  }

  Future<DaoResponse> addSubject(Map<String, dynamic> subject) async {
    return _dio.post("/subject", data: subject, options: Options(
      headers: {
        "content-type": "application/json"
      }
    )).then((response) =>
        DaoResponse(null, Status.success, "Subject added", null)
    ).catchError((error) {
      var message = (error as DioException).response!.data;
      return DaoResponse(null, Status.failed, message, null);
    });
  }
}