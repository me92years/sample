import { useRef } from "react";
import { NavLink } from "react-router-dom";
import styled from "styled-components";
import { Button } from "../App";
import { api, LOCAL } from "../Setting";
import { Input, InputGroup, Modal, ModalBody } from "./items/SetupModal";

function Nav({ user }) {
  const nickname = useRef();
  
  const setNickname = () => {
    api.post(LOCAL + "/apix/update/nickname", { 
      nickname: nickname.current.value, id: user.id }, {
      headers: { "Content-Type": "application/json" }
    })
    .then(() => window.location.href = "/")
    .catch(() => alert("서버 에러!"))
  };
  return (
    <Wrapper>
      <Logo>
        <_NavLink to={"/"}>Logo</_NavLink>
      </Logo>
      <Mode>
        {
          user ? user.role === "USER" ? 
            user.nickname + " 일반모드"
          : user.nickname + " 관리자모드"
          : "로그인이 필요합니다."
        }
      </Mode>
      <Items>
        <Item>
          {
          !user ? 
            <_NavLink to={"http://localhost:8080/oauth2/authorization/google"}>Login</_NavLink>
          : <_NavLink to={"http://localhost:8080/apio/user/logout"}>Logout</_NavLink>  
          }
        </Item>
        <Item>
          <_NavLink>About</_NavLink>
        </Item>
      </Items>
      {(user && !user.nickname) ? 
        <Modal className="show">
          <ModalBody>
            <H1>닉네임 설정</H1>
            <InputGroup>
              <Input placeholder="사용할 닉네임을 입력 해 주세요." ref={nickname} />
            </InputGroup>
            <Button className="primary w-100" onClick={setNickname}>입력완료</Button>
          </ModalBody>
        </Modal>
        : ""}
    </Wrapper>
  )
}

const H1 = styled.h1`
  margin: 12px 0 32px 0;
  padding: 0;
  text-align: center;
`;

const Wrapper = styled.nav`
  display: grid;
  grid-template-columns: 110px auto 160px;
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-height: 9vh;
  box-sizing: border-box;
  background-color: #222;
`;

const Logo = styled.h1`
  & a {
    font-size: 24px;
  }
  &:hover {
    background-color: #111;
  }
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  margin: 0;
  padding: 0;
  color: white;
`;

const Items = styled.ul`
  display: flex;
  justify-content: center;
  align-items: center;
`
const Item = styled.li`
  &:hover {
    background-color: #111;
  }
  width: 100%;
  height: 100%;
`
const _NavLink = styled(NavLink)`
  &:hover {
    color: #ddd;
  }
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  text-decoration: none;
  color: white;
  font-size: 16px;
`;

const Mode = styled.p`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: auto;
  height: 100%;
  text-align: center;
  color: white;
  font-size: 16px;
`;

export default Nav;
