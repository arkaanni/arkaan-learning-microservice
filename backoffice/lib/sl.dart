import 'package:backoffice/features/room/dao/room_dao.dart';
import 'package:backoffice/features/room/room_service.dart';
import 'package:backoffice/features/subject/dao/subject_dao.dart';
import 'package:backoffice/features/subject/subject_service.dart';
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
  sl.registerSingleton<Dio>(dio);
  sl.registerSingleton<StudentService>(studentService);
  sl.registerSingleton<SubjectDao>(SubjectDao(dio));
  sl.registerSingleton<SubjectService>(SubjectService());
  sl.registerSingleton<RoomDao>(RoomDao());
  sl.registerSingleton<RoomService>(RoomService());
}