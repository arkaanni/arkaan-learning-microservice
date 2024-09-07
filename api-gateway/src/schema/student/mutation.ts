import { GraphQLString, type GraphQLFieldConfigMap } from "graphql";
import { StudentInputType, type Student } from "./type";

type Param = {
  student: Student
}

const StudentMutation: GraphQLFieldConfigMap<any, any> = {
  addStudent: {
    type: GraphQLString,
    args: {
      student: { type: StudentInputType }
    },
    resolve: (_, { student }: Param) => {
      return student.firstName;
    }
  }
};

export default StudentMutation;