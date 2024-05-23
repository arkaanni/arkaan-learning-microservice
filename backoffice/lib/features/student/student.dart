class Student {
  final int? id;
  String studentId;
  String firstName;
  String lastName;
  String address;
  String phone;
  int semester;

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

  factory Student.empty() => Student(
      id: -1,
      studentId: "",
      firstName: "",
      lastName: "",
      address: "",
      phone: "",
      semester: 1);

  toJson() => {
    "studentId": studentId,
    "firstName": firstName,
    "lastName": lastName,
    "address": address,
    "phone": phone,
    "semester": semester
  };
}
