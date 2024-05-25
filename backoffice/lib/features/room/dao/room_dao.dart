import 'package:backoffice/core/dao_response.dart';
import 'package:backoffice/sl.dart';
import 'package:dio/dio.dart';
import '../room.dart';

class RoomDao {
  final Dio _dio = sl.get<Dio>();

  Stream<Room> getRooms() async* {
    var response = await _dio.get("/room");
    for (Map<String, dynamic> r in response.data) {
      yield Room.fromJson(r);
    }
  }

  Future<DaoResponse> addRoom(Map<String, dynamic> room) async {
    return _dio.post("/room", data: room, options: Options(
        headers: {
          "content-type": "application/json"
        }
    )).then((response) =>
        DaoResponse(null, Status.success, "Room added", null)
    ).catchError((error) {
      var message = (error as DioException).response!.data;
      return DaoResponse(null, Status.failed, message, null);
    });
  }
}