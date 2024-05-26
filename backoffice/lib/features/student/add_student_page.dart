import 'package:backoffice/core/dao_response.dart';
import 'package:backoffice/features/student/student.dart';
import 'package:backoffice/features/student/student_service.dart';
import 'package:flutter/material.dart';
import 'package:backoffice/sl.dart';

class AddStudentPage extends StatefulWidget {
  const AddStudentPage({super.key});

  @override
  State<StatefulWidget> createState() => _AddStudentPageState();
}

class _AddStudentPageState extends State<AddStudentPage> {
  StudentService studentService = sl.get<StudentService>();
  Student studentForm = Student.empty();
  final formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Form(
        key: formKey,
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.6,
          child: Wrap(
            direction: Axis.horizontal,
            runSpacing: 24,
            children: [
              TextFormField(
                onSaved: (value) {
                  studentForm.studentId = value!;
                },
                keyboardType: TextInputType.text,
                decoration: const InputDecoration(labelText: "Student Id"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Student Id required";
                  }
                  return null;
                },
              ),
              TextFormField(
                onSaved: (value) {
                  studentForm.firstName = value!;
                },
                keyboardType: TextInputType.name,
                decoration: const InputDecoration(labelText: "First name"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "First name required";
                  }
                  return null;
                },
              ),
              TextFormField(
                onSaved: (value) {
                  studentForm.lastName = value!;
                },
                keyboardType: TextInputType.name,
                decoration: const InputDecoration(labelText: "Last name"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Last name required";
                  }
                  return null;
                },
              ),
              TextFormField(
                onSaved: (value) {
                  studentForm.address = value!;
                },
                keyboardType: TextInputType.streetAddress,
                decoration: const InputDecoration(labelText: "Address"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Address required";
                  }
                  return null;
                },
              ),
              TextFormField(
                onSaved: (value) {
                  studentForm.phone = value!;
                },
                keyboardType: TextInputType.phone,
                decoration: const InputDecoration(labelText: "Phone"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Phone required";
                  }
                  return null;
                },
              ),
              TextFormField(
                onSaved: (value) {
                  studentForm.semester = int.parse(value!);
                },
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(labelText: "Semester"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Semester required";
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
                    studentService.addStudent(studentForm).then((response) {
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
