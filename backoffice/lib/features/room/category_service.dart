import 'package:backoffice/features/room/dao/category_dao.dart' as category_dao;
import 'room.dart';

Stream<Category> getCategories() => category_dao.getCategories();

Future<Map<int, String>> getCategoriesMap() => getCategories()
    .fold<Map<int, String>>({}, (prev, category) => {
      ...prev,
      category.id!: category.name
    });