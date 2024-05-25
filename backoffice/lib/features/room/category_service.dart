import 'package:backoffice/features/room/dao/category_dao.dart' as category_dao;
import 'room.dart';

Stream<Category> getCategories() => category_dao.getCategories();