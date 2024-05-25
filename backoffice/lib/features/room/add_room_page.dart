import 'package:backoffice/core/dao_response.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/features/room/room_service.dart';
import 'package:backoffice/features/room/category_service.dart' as category_service;
import 'room.dart';
import 'package:backoffice/sl.dart';

class AddRoomPage extends StatefulWidget {
  final Function onCloseCallback;

  const AddRoomPage({super.key, required this.onCloseCallback});

  @override
  State<StatefulWidget> createState() => _AddRoomPageState();
}

class _AddRoomPageState extends State<AddRoomPage> {
  final RoomService roomService = sl.get<RoomService>();
  Room roomForm = Room.empty();
  final formKey = GlobalKey<FormState>();
  bool isLoading = true;
  List<Category> categories = List.empty();

  @override
  void initState() {
    category_service.getCategories().toList().then((c) {
      setState(() {
        categories = c;
        isLoading = false;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const CircularProgressIndicator();
    }
    return Center(
      child: Form(
        key: formKey,
        child: Wrap(
          direction: Axis.horizontal,
          runSpacing: 24,
          children: [
            TextFormField(
              onSaved: (value) {
                roomForm.code = value!;
              },
              keyboardType: TextInputType.text,
              decoration: const InputDecoration(labelText: "Room code"),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return "Room code required";
                }
                return null;
              },
            ),
            DropdownButtonFormField(
              onChanged: (value) {
                roomForm.categoryId = value as int;
              },
              items: categories.map((category) {
                return DropdownMenuItem(
                  value: category.id,
                  child: Text(category.name),
                );
              }).toList(),
            ),
            ElevatedButton(
              onPressed: () {
                if (formKey.currentState!.validate()) {
                  var scaffoldMessenger = ScaffoldMessenger.of(context);
                  formKey.currentState!.save();
                  Navigator.pop(context);
                  scaffoldMessenger.showSnackBar(const SnackBar(
                      content: Text("Submitting data"),
                      behavior: SnackBarBehavior.floating));
                  roomService.addRoom(roomForm).then((response) {
                    String message = response.status == Status.success
                        ? "Room added"
                        : response.message!;
                    Color bgColor = response.status == Status.success
                        ? Colors.green
                        : Colors.redAccent;
                    scaffoldMessenger.showSnackBar(SnackBar(
                        content: Text(message),
                        backgroundColor: bgColor,
                        behavior: SnackBarBehavior.floating));
                    widget.onCloseCallback();
                  });
                }
              },
              child: const Text("Submit"),
            )
          ],
        ),
      ),
    );
  }
}
