import 'package:backoffice/features/room/dao/room_dao.dart';
import 'package:backoffice/core/dao_response.dart';
import 'package:backoffice/features/room/room.dart';
import 'package:backoffice/sl.dart';

class RoomService {
  final RoomDao _roomDao = sl.get<RoomDao>();

  Stream<Room> getRooms() => _roomDao.getRooms();

  Future<DaoResponse> addRoom(Room room) => _roomDao.addRoom(room.toJson());
}