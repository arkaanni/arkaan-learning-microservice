class Student {
  final int? id;
  final String studentId;
  final String firstName;
  final String lastName;
  final String address;
  final String phone;
  final int semester;

  Student({
    required this.id,
    required this.studentId,
    required this.firstName,
    required this.lastName,
    required this.address,
    required this.phone,
    required this.semester
  });

  Student.fromJson(Map<String, dynamic> json)
    : id = json["id"],
      studentId = json["studentId"],
      firstName = json["firstName"],
      lastName = json["lastName"],
      address = json["address"],
      phone = json["phone"],
      semester = json["semester"];
}
