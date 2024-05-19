import 'package:backoffice/features/student/student.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudentPage extends StatefulWidget {
  final StudentService studentService;

  const StudentPage({
    super.key,
    required this.studentService
  });

  @override
  State createState() => _StudentPageState();
}

class _StudentPageState extends State<StudentPage> {

  List<Student> _studentList = List.empty(growable: true);

  @override
  void initState() {
    super.initState();
    fetchStudents();
  }

  void fetchStudents() async {
    await Future.delayed(const Duration(milliseconds: 2000));
    var studentList = await widget.studentService.getStudents().toList();
    setState(() {
      _studentList = studentList;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_studentList.isEmpty) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    var student1 = _studentList.first.studentId;
    return Row(
      children: [
        Center(
          child: Text(student1.toString()),
        )
      ],
    );
  }
}