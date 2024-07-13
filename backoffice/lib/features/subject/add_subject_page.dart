import 'package:backoffice/core/dao_response.dart';
import 'package:backoffice/features/subject/subject_service.dart';
import 'package:backoffice/features/subject/subject.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/sl.dart';

class AddSubjectPage extends StatefulWidget {
  final Function _addSubjectCallback;

  const AddSubjectPage({super.key, required Function addSubjectCallback})
      : _addSubjectCallback = addSubjectCallback;

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
                  var submitSnackBar = scaffoldMessenger.showSnackBar(
                      const SnackBar(
                        content: Text("Submitting data"),
                        behavior: SnackBarBehavior.floating,
                      ));
                  subjectService.addSubject(subjectForm).then((response) {
                    submitSnackBar.close();
                    String message = response.status == Status.success
                        ? "Subject added"
                        : response.message!;
                    Color bgColor = response.status == Status.success
                        ? Colors.green
                        : Colors.redAccent;
                    scaffoldMessenger.showSnackBar(SnackBar(
                      content: Text(message),
                      backgroundColor: bgColor,
                      behavior: SnackBarBehavior.floating,
                    ));
                    if (response.status == Status.success) {
                      widget._addSubjectCallback();
                      Navigator.pop(context);
                    }
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
