import graphql, { GraphQLInt, GraphQLString } from "graphql";

interface Student {
  studentId: String,
  firstName: String,
  lastName: String,
  address: String,
  phone: String,
  semester: Number
}

const StudentField = {
  studentId: { type: GraphQLString },
  firstName: { type: GraphQLString },
  lastName: { type: GraphQLString },
  address: { type: GraphQLString },
  phone: { type: GraphQLString },
  semester: { type: GraphQLInt }
};

const StudentType = new graphql.GraphQLObjectType({
  name: "Student",
  fields: {
    id: { type: GraphQLInt },
    ...StudentField
  }
});

const StudentInputType = new graphql.GraphQLInputObjectType({
  name: "StudentInput",
  fields: {
    ...StudentField
  }
});

export {
  StudentInputType,
  StudentType
};

export type { Student };
