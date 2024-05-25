import 'package:backoffice/features/room/dao/category_dao.dart' as category_dao;
import 'room.dart';

Stream<Category> getCategories() => category_dao.getCategories();

Future<Map<int, String>> getCategoriesMap() async {
  Map<int, String> map = {};
  await getCategories().forEach((category) =>
      map.putIfAbsent(category.id!, () => category.name));
  return map;
}