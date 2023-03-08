import { useEffect } from "react";
import styled from "styled-components";
import { api } from "../Setting";
import Nav from "./Nav";

const Wrapper = styled.header`
  width: 100%;
  height: 9vh;
  background-color: #222;
`;

function Header({ user, setUser }) {
  useEffect(() => {
    api.get("/apix/session/user")
    .then(response => {
      setUser(response.data);
    });
  }, []);
  
  return (
    <Wrapper>      
      <Nav user={user} />
    </Wrapper>
  )
}

export default Header;