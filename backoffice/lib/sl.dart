import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:backoffice/features/student/dao/student_dao.dart';

final sl = GetIt.instance;

void init() {
  final dio = Dio(BaseOptions(
    baseUrl: "http://local.dev",
    connectTimeout: const Duration(milliseconds: 5000),
  ));
  var studentDao = StudentDao(dio: dio);
  var studentService = StudentService(dao: studentDao);
  sl.registerSingleton<StudentService>(studentService);
}