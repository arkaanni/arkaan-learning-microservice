import { GraphQLList, GraphQLString, type GraphQLFieldConfigMap } from "graphql";
import { StudentType } from "./type";
import { getStudentById, getStudentList } from "./api";

const StudentQuery: GraphQLFieldConfigMap<any, any> = {
  getStudentList: {
    type: new GraphQLList(StudentType),
    resolve: async () =>  await getStudentList()
  },
  getStudentById: {
    type: StudentType,
    args: {
      id: { type: GraphQLString }
    },
    resolve: async (_, { id }) => getStudentById(id)
  }
};

export default StudentQuery;