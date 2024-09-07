import { fetch } from "bun";
import type { Subject } from "./type";

const ENDPOINT: string = Bun.env.SUBJECT_API!;

async function getSubjectList(): Promise<Array<Subject>> {
  return fetch(ENDPOINT, {
    headers: {
      "Content-Type": "application/json"
    }
  }).then(res => res.json());
}

async function getSubjectByCode(code: string): Promise<Subject> {
  return fetch(`${ENDPOINT}/${code}`, {
    headers: {
      "Content-Type": "application/json"
    }
  }).then(res => res.json());
}

export {
  getSubjectList,
  getSubjectByCode
}