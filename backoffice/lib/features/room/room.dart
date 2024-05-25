class Room {
  final int? id;
  String code;
  int categoryId;

  Room(this.id, this.code, this.categoryId);

  Room.fromJson(Map<String, dynamic> json)
    : id = json["id"],
      code = json["code"],
      categoryId = json["categoryId"];

  factory Room.empty() => Room(-1, "", -1);

  Map<String, dynamic> toJson() => {
    "id": id,
    "code": code,
    "categoryId": categoryId
  };
}

class Category {
  final int? id;
  String name;

  Category(this.id, this.name);

  factory Category.empty() => Category(-1, "");

  Category.fromJson(Map<String, dynamic> json)
    : id = json["id"],
      name = json["name"];

  Map<String, dynamic> toJson() => {
    "id": id,
    "name": name
  };
}