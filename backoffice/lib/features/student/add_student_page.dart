import 'package:backoffice/features/student/student.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:flutter/material.dart';

import '../../sl.dart';

class AddStudentPage extends StatefulWidget {

  const AddStudentPage({super.key});

  @override
  State<StatefulWidget> createState() => _AddStudentPageState();
}

class _AddStudentPageState extends State<AddStudentPage> {
  StudentService studentService = sl.get<StudentService>();
  Student studentForm = Student.empty();

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Text("Add student"),
    );
  }
}