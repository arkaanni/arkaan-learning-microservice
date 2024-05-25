import 'package:backoffice/sl.dart';
import 'package:dio/dio.dart';
import '../room.dart';

final Dio _dio = sl.get<Dio>();

Stream<Category> getCategories() async* {
  var response = await _dio.get("/room/category");
  for (Map<String, dynamic> c in response.data) {
    yield Category.fromJson(c);
  }
}