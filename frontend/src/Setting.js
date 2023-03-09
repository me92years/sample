import axios from "axios";

export const LOCAL = "http://localhost:8080";
export const api = axios.create({
  baseURL: LOCAL,
  withCredentials: true
});