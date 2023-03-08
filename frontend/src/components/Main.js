import { Route, Routes } from "react-router-dom";
import styled from "styled-components";
import Detail from "./pages/Detail";
import Home from "./pages/Home";

function Main({ user }) {
  console.log(user);
  return (
    <MainWrapper>
      <Routes>
        <Route path="/" element={<Home user={user} />} />
        <Route path="/detail/:no" element={<Detail user={user} />} />
      </Routes>
    </MainWrapper>
  );
};

const MainWrapper = styled.main`
  width: 100%;
  height: 91vh;
  background-color: #333;
`;


export default Main;