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
      theme: ThemeData.dark(),
      home: Scaffold(
        appBar: AppBar(),
        drawer: _Navigation(changePage),
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

Widget content(Page page) {
  switch(page) {
    case Page.student:
      return const Text("Student");
    case Page.subject:
      return const Text("Subject");
    case Page.room:
      return const Text("Room");
    case Page.courseplan:
      return const Text("Courseplan");
  }
}

enum Page { student, subject, room, courseplan }

class _Navigation extends StatelessWidget {
  final Function(Page) navigate;

  const _Navigation(this.navigate);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: const EdgeInsets.fromLTRB(20, 40, 20, 40),
        children: [
          ListTile(
            title: ElevatedButton(
              style: const ButtonStyle(alignment: Alignment.centerLeft),
              onPressed: () => {
                navigate(Page.student),
                Navigator.pop(context)
              },
              child: const Text("Student")
            ),
          ),
          ListTile(
            title: ElevatedButton(
              style: const ButtonStyle(alignment: Alignment.centerLeft),
              onPressed: () => {
                navigate(Page.subject),
                Navigator.pop(context)
              },
              child: const Text("Subject")
            ),
          ),
          ListTile(
            title: ElevatedButton(
              style: const ButtonStyle(alignment: Alignment.centerLeft),
              onPressed: () => {
                navigate(Page.room),
                Navigator.pop(context)
              },
              child: const Text("Room")
            ),
          ),
          ListTile(
            title: ElevatedButton(
              style: const ButtonStyle(alignment: Alignment.centerLeft),
              onPressed: () => {
                navigate(Page.courseplan),
                Navigator.pop(context)
              },
              child: const Text("Course plan")
            ),
          )
        ],
      ),
    );
  }
}
