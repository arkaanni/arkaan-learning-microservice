import { fetch } from "bun";
import type { Student } from "./type";

const ENDPOINT: string = Bun.env.STUDENT_API!;

const getStudentList = async (): Promise<Array<Student>> => {
  return fetch(ENDPOINT, {
    headers: {
      "Content-Type": "application/json"
    }
  }).then(res => res.json());
};

const getStudentById = async (id: string): Promise<Student> => {
  return fetch(`${ENDPOINT}/${id}`, {
    headers: {
      "Content-Type": "application/json"
    }
  }).then(res => res.json());
}

export {
  getStudentList,
  getStudentById
}