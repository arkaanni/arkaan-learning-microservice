import { GraphQLObjectType, GraphQLString } from "graphql";

interface Subject {
  subjectCode: String,
  name: String,
  description: String
}

const SubjectField = {
  subjectCode: { type: GraphQLString },
  name: { type: GraphQLString },
  description: { type: GraphQLString }
}

const SubjectType = new GraphQLObjectType({
  name: "Subject",
  fields: {
    ...SubjectField
  }
});

const SubjectInputType = new GraphQLObjectType({
  name: "SubjectInput",
  fields: {
    ...SubjectField
  }
});



export {
  SubjectType,
  SubjectInputType
}

export type {
  Subject
}