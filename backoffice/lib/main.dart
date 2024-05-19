import 'package:backoffice/features/student/dao/student_dao.dart';
import 'package:backoffice/features/student/student_page.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

void main() {
  final dio = Dio(BaseOptions(
    baseUrl: "http://local.dev",
    connectTimeout: const Duration(milliseconds: 5000),
  ));
  var studentDao = StudentDao(dio: dio);
  var studentService = StudentService(dao: studentDao);
  var studentPage = StudentPage(studentService: studentService);
  runApp(MyApp(studentPage: studentPage));
}

class MyApp extends StatefulWidget {
  final StudentPage studentPage;
  const MyApp({
    super.key,
    required this.studentPage
  });

  @override
  State<StatefulWidget> createState() => _AppState();
}

class _AppState extends State<MyApp> {

  var _page = Page.student;

  @override
  Widget build(BuildContext context) {
    void changePage(Page page) {
      setState(() {
        _page = page;
      });
    }
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(),
        drawer: _Navigation(changePage, _page),
        body: switch(_page) {
          Page.student => widget.studentPage,
          Page.subject => const Text("Subject"),
          Page.room => const Text("Room"),
          Page.courseplan => const Text("Courseplan")
        },
      ),
    );
  }
}

enum Page { student, subject, room, courseplan }

class _Navigation extends StatelessWidget {
  final Function(Page) navigate;
  final Page selectedPage;

  const _Navigation(this.navigate, this.selectedPage);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: const EdgeInsets.fromLTRB(10, 20, 10, 20),
        children: [
          const Text("Student", style: TextStyle()),
          const Divider(),
          ListTile(
            selected: selectedPage == Page.student,
            title: const Text("Student list"),
            onTap: () => {
              navigate(Page.student),
              Navigator.pop(context)
            },
          ),
          const Text("Subject"),
          const Divider(),
          ListTile(
            selected: selectedPage == Page.subject,
            title: const Text("Subject list"),
            onTap: () => {
              navigate(Page.subject),
              Navigator.pop(context)
            },
          ),
          const Text("Room"),
          const Divider(),
          ListTile(
            selected: selectedPage == Page.room,
            title: const Text("Room list"),
            onTap: () => {
              navigate(Page.room),
              Navigator.pop(context)
            },
          ),
          const Text("Course plan"),
          const Divider(),
          ListTile(
            selected: selectedPage == Page.courseplan,
            title: const Text("Course plan list"),
            onTap: () => {
              navigate(Page.courseplan),
              Navigator.pop(context)
            },
          ),
        ],
      ),
    );
  }
}
