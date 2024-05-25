class Subject {
  final int? id;
  String subjectCode;
  String name;
  String description;

  Subject(this.id, this.subjectCode, this.name, this.description);

  factory Subject.empty() => Subject(-1, "", "", "");

  Subject.fromJson(Map<String, dynamic> json)
    : id = json["id"],
      subjectCode = json["subjectCode"],
      name = json["name"],
      description = json["description"];
  
  Map<String, dynamic> toJson() => {
    "id": id,
    "subjectCode": subjectCode,
    "name": name,
    "description": description
  };
}