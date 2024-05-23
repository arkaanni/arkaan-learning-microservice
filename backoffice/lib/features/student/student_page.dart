import 'package:backoffice/features/student/student.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:flutter/material.dart';

import '../../sl.dart';

class StudentPage extends StatefulWidget {
  
  const StudentPage({super.key});

  @override
  State createState() => _StudentPageState();
}

class _StudentPageState extends State<StudentPage> {
  List<Student> _studentList = List.empty(growable: true);
  final StudentService studentService = sl.get<StudentService>();

  @override
  void initState() {
    super.initState();
    fetchStudents();
  }

  void fetchStudents() async {
    await Future.delayed(const Duration(milliseconds: 1000));
    var studentList = await studentService.getStudents().toList();
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
    return SizedBox.expand(
        child: DataTable(
            columns: const <DataColumn>[
          DataColumn(label: Text("Student ID")),
          DataColumn(label: Text("Name")),
          DataColumn(label: Text("Address")),
          DataColumn(label: Text("Phone")),
          DataColumn(label: Text("Semester")),
        ],
            rows: _studentList
                .map((st) => DataRow(cells: [
                      DataCell(Text(st.studentId)),
                      DataCell(Text('${st.firstName} ${st.lastName}')),
                      DataCell(Text(st.address)),
                      DataCell(Text(st.phone)),
                      DataCell(Text(st.semester.toString())),
                    ]))
                .toList()));
  }
}
