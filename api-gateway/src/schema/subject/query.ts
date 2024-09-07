import { GraphQLList, type GraphQLFieldConfigMap } from "graphql";
import { getSubjectByCode, getSubjectList } from "./api";
import { SubjectType } from "./type";

const SubjectQuery: GraphQLFieldConfigMap<any, any> = {
  getSubjectList: {
    type: new GraphQLList(SubjectType),
    resolve: getSubjectList 
  },
  getSubjectById: {
    type: SubjectType,
    resolve: getSubjectByCode
  }
};

export default SubjectQuery;