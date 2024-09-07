import graphql from "graphql";
import StudentQuery from "./student/query";
import StudentMutation from "./student/mutation";

const Query = new graphql.GraphQLObjectType({
  name: "Query",
  fields: {
    ...StudentQuery
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