import express, { type Express } from "express";
import { createHandler } from "graphql-http/lib/use/express";
import schema from "./src/schema";

const app: Express = express();

app.all(
  "/graphql",
  createHandler({
    schema: schema
  })
);

app.listen(3000, () => {
  console.log("listening on port 3000...")
});