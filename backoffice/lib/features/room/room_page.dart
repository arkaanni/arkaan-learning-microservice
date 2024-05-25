import 'package:backoffice/features/room/add_room_page.dart';
import 'package:backoffice/features/room/room.dart';
import 'package:backoffice/features/room/room_service.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/sl.dart';
import 'package:backoffice/core/state.dart' as app_state;

class RoomPage extends StatefulWidget {
  const RoomPage({super.key});

  @override
  State<StatefulWidget> createState() => _RoomPageState();
}

class _RoomPageState extends State {
  final RoomService roomService = sl.get<RoomService>();
  List<Room> roomList = List.empty();

  @override
  void initState() {
    fetchRooms();
    super.initState();
  }

  void fetchRooms() async {
    roomService.getRooms().toList().then((rooms) {
      setState(() {
        roomList = rooms;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    if (roomList.isEmpty) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    return Padding(padding: const EdgeInsetsDirectional.only(start: 20, end: 20),
      child: Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            ElevatedButton(
              onPressed: () {
                showModalBottomSheet(
                    // isScrollControlled: true,
                    context: context,
                    // constraints: BoxConstraints(
                    //     maxWidth: MediaQuery.of(context).size.width * 0.8,
                    //     maxHeight: MediaQuery.of(context).size.height * 0.8),
                    builder: (BuildContext ctx) {
                      return Dialog(
                        insetPadding: const EdgeInsets.all(60),
                        child: AddRoomPage(onCloseCallback: fetchRooms)
                      );
                    });
              },
              child: const Text("Add room")),
          ],
        ),
        Expanded(
            child: DataTable(
                columns: const <DataColumn>[
              DataColumn(label: Text("Code")),
              DataColumn(label: Text("Category id"))
            ],
                rows: roomList
                    .map((r) => DataRow(cells: [
                          DataCell(Text(r.code)),
                          DataCell(Text(r.categoryId.toString()))
                        ]))
                    .toList()))
      ],
      )
    );
  }
}
