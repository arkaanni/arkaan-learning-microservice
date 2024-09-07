import graphql from "graphql";
import StudentQuery from "./student/query";
import StudentMutation from "./student/mutation";
import SubjectQuery from "./subject/query";

const Query = new graphql.GraphQLObjectType({
  name: "Query",
  fields: {
    ...StudentQuery,
    ...SubjectQuery
  }
});

const Mutation = new graphql.GraphQLObjectType({
  name: "Mutation",
  fields: {
    ...StudentMutation
  }
});

const schema = new graphql.GraphQLSchema({
  query: Query,
  mutation: Mutation
});

export default schema;