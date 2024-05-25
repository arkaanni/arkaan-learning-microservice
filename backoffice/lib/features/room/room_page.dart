import 'package:backoffice/features/room/add_room_page.dart';
import 'package:backoffice/features/room/room.dart';
import 'package:backoffice/features/room/room_service.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/sl.dart';
import 'package:backoffice/features/room/category_service.dart' as category_service;

class RoomPage extends StatefulWidget {
  const RoomPage({super.key});

  @override
  State<StatefulWidget> createState() => _RoomPageState();
}

class _RoomPageState extends State {
  final RoomService roomService = sl.get<RoomService>();
  List<Room> roomList = List.empty();
  Map<int, String> categoryMap = {};
  bool isLoading = true;

  @override
  void initState() {
    fetchRooms();
    super.initState();
  }

  void fetchRooms() async {
    setState(() {
      isLoading = true;
    });
    Map<int, String> categories = await category_service.getCategoriesMap();
    List<Room> rooms = await roomService.getRooms().toList();
    setState(() {
      roomList = rooms;
      categoryMap = categories;
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
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
                      context: context,
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
                DataColumn(label: Text("Category"))
              ],
              rows: roomList
                .map((r) => DataRow(cells: [
                  DataCell(Text(r.code)),
                  DataCell(Text(categoryMap[r.categoryId] ?? ""))
              ])).toList()
            )
          )
        ],
      )
    );
  }
}
