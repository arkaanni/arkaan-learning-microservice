import 'package:backoffice/core/dao_response.dart';
import 'package:backoffice/features/subject/subject_service.dart';
import 'package:backoffice/features/subject/subject.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/sl.dart';

class AddSubjectPage extends StatefulWidget {

  const AddSubjectPage({super.key});

  @override
  State<StatefulWidget> createState() => _AddSubjectPageState();
}

class _AddSubjectPageState extends State<AddSubjectPage> {

  final subjectService = sl.get<SubjectService>();
  final formKey = GlobalKey<FormState>();
  final subjectForm = Subject.empty();

  @override
  Widget build(BuildContext context) {
    return Form(
      key: formKey,
      child: SizedBox(
        width: MediaQuery.of(context).size.width * 0.5,
        child: Wrap(
          direction: Axis.horizontal,
          runSpacing: 24,
          children: [
            TextFormField(
              onSaved: (value) {
                subjectForm.subjectCode = value!;
              },
              keyboardType: TextInputType.text,
              decoration: const InputDecoration(labelText: "Subject code"),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return "Subject code required";
                }
                return null;
              },
            ),
            TextFormField(
              onSaved: (value) {
                subjectForm.name = value!;
              },
              keyboardType: TextInputType.name,
              decoration: const InputDecoration(labelText: "Name"),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return "Name required";
                }
                return null;
              },
            ),
            TextFormField(
              onSaved: (value) {
                subjectForm.description = value!;
              },
              keyboardType: TextInputType.name,
              decoration: const InputDecoration(labelText: "Description"),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return "Description required";
                }
                return null;
              },
            ),
            ElevatedButton(
              onPressed: () {
                if (formKey.currentState!.validate()) {
                  var scaffoldMessenger = ScaffoldMessenger.of(context);
                  formKey.currentState!.save();
                  scaffoldMessenger.showSnackBar(
                    const SnackBar(content: Text("Submitting data"))
                  );
                  subjectService.addSubject(subjectForm).then((response) {
                    String message = response.status == Status.success ? "Student added": response.message!;
                    Color bgColor = response.status == Status.success ? Colors.white : Colors.redAccent;
                    scaffoldMessenger.showSnackBar(
                      SnackBar(content: Text(message), backgroundColor: bgColor)
                    );
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