import 'package:backoffice/features/courseplan/courseplan_page.dart';
import 'package:backoffice/features/room/room_page.dart';
import 'package:backoffice/features/student/student_page.dart';
import 'package:backoffice/features/subject/subject_page.dart';
import 'package:flutter/material.dart';
import './sl.dart' as sl;

void main() {
  sl.init();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {

  const MyApp({super.key});

  @override
  State<StatefulWidget> createState() => _AppState();
}

class _AppState extends State<MyApp> {

  var _page = Page.student;

  void changePage(Page page) {
    setState(() {
      _page = page;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Backoffice",
      home: Scaffold(
        appBar: AppBar(
          title: pageTitles[_page],
        ),
        drawer: _Navigation(changePage, _page),
        body: routes[_page]
      ),
    );
  }
}

final pageTitles = {
  Page.student: const Text("Student list"),
  Page.subject: const Text("Subject list"),
  Page.room: const Text("Room list"),
  Page.courseplan: const Text("Course plan list")
};

final routes = {
  Page.student: const StudentPage(),
  Page.subject: const SubjectPage(),
  Page.room: const RoomPage(),
  Page.courseplan: const Text("Courseplan")
};

enum Page { student, addStudent, subject, addSubject, room, courseplan }

class _Navigation extends StatelessWidget {
  final Function(Page) navigate;
  final Page selectedPage;

  const _Navigation(this.navigate, this.selectedPage);

  @override
  Widget build(BuildContext context) {
    ListTile menu(Page page) => ListTile(
      selected: selectedPage == page,
      title: pageTitles[page],
      onTap: () => {
          navigate(page),
          Navigator.pop(context)
        },
      );

    return Drawer(
      child: ListView(
        padding: const EdgeInsets.fromLTRB(10, 20, 10, 20),
        children: [
          const Text("Student"),
          const Divider(),
          menu(Page.student),
          const Text("Subject"),
          const Divider(),
          menu(Page.subject),
          const Text("Room"),
          const Divider(),
          menu(Page.room),
          const Text("Course plan"),
          const Divider(),
          menu(Page.courseplan),
        ],
      ),
    );
  }
}
