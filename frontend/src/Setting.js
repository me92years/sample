import axios from "axios";

export const LOCAL = "http://localhost:80";
export const api = axios.create({
  baseURL: LOCAL,
  withCredentials: true
});