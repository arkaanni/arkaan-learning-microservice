import "package:backoffice/core/dao_response.dart";
import "package:backoffice/features/subject/dao/subject_dao.dart";
import "package:backoffice/features/subject/subject.dart";
import "package:backoffice/sl.dart";


class SubjectService {
  final SubjectDao _dao = sl.get<SubjectDao>();

  Stream<Subject> getSubjects() => _dao.getSubjects();

  Future<DaoResponse> addSubject(Subject subject) => _dao.addSubject(subject.toJson());
}