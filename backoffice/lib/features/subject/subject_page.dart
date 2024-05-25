import 'package:backoffice/features/subject/subject_service.dart';
import 'package:backoffice/sl.dart';
import 'package:flutter/material.dart';
import 'subject.dart';

class SubjectPage extends StatefulWidget {
  const SubjectPage({super.key});

  @override
  State<StatefulWidget> createState() => _SubjectPageState();
}

class _SubjectPageState extends State<SubjectPage> {
  final SubjectService subjectService = sl.get<SubjectService>();
  List<Subject> subjectList = List.empty();

  @override
  void initState() {
    fetchSubjects();
    super.initState();
  }

  void fetchSubjects() async {
    await subjectService.getSubjects().toList().then((subjects) {
      setState(() {
        subjectList = subjects;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    if (subjectList.isEmpty) {
      return const Center(
        child: CircularProgressIndicator(),
      );
    }
    return SizedBox.expand(
      child: DataTable(
        columns: const <DataColumn>[
          DataColumn(label: Text("Subject code")),
          DataColumn(label: Text("Name")),
          DataColumn(label: Text("Description"))
        ],
        rows: subjectList.map((sb) => DataRow(
          cells: [
            DataCell(Text(sb.subjectCode)),
            DataCell(Text(sb.name)),
            DataCell(Text(sb.description))
          ])).toList()
      )
    );
  }
}
