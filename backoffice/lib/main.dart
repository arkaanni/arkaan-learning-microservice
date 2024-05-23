import 'package:backoffice/features/student/add_student_page.dart';
import 'package:backoffice/features/student/student_page.dart';
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
      home: Scaffold(
        appBar: AppBar(),
        drawer: _Navigation(changePage, _page),
        body: switch(_page) {
          Page.student => const StudentPage(),
          Page.subject => const Text("Subject"),
          Page.room => const Text("Room"),
          Page.courseplan => const Text("Courseplan"),
          Page.addStudent => const AddStudentPage()
        },
      ),
    );
  }
}

final routes = {
  Page.student: const StudentPage(),
  Page.subject: const Text("Subject"),
  Page.room: const Text("Room"),
  Page.courseplan: const Text("Courseplan"),
  Page.addStudent: const AddStudentPage()
};

enum Page { student, addStudent, subject, room, courseplan }

class _Navigation extends StatelessWidget {
  final Function(Page) navigate;
  final Page selectedPage;

  const _Navigation(this.navigate, this.selectedPage);

  @override
  Widget build(BuildContext context) {
    ListTile menu(Page page, String title) => ListTile(
      selected: selectedPage == page,
      title: Text(title),
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
          menu(Page.student, "Student list"),
          menu(Page.addStudent, "Add student"),
          const Text("Subject"),
          const Divider(),
          menu(Page.subject, "Subject list"),
          const Text("Room"),
          const Divider(),
          menu(Page.room, "Room list"),
          const Text("Course plan"),
          const Divider(),
          menu(Page.courseplan, "Course plan list"),
        ],
      ),
    );
  }
}
