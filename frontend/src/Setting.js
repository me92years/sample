import axios from "axios";

export const LOCAL = "http://52.79.203.163:8080";
export const api = axios.create({
  baseURL: LOCAL,
  withCredentials: true
});