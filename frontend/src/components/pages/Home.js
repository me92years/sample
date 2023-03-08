import { useState } from "react";
import styled from "styled-components";
import Movies from "../items/Movies";
import SetupModal from "../items/SetupModal";

const $ = require("jquery");
function Home({ user }) {
  const [data, setData] = useState({ title: "", genre: "", synopsis: "" });
  const [movies, setMovies] = useState(null);
  const showModal = () => { $("[data-modal='1']").show() };
  
  return (
    <SectionWrapper>
      <ArticleWrapper>
        {
          user ? user.role === "ADMIN" ?
            <button type="button" onClick={showModal}>관리자모드로 추가</button>
            : "" 
            : ""
        }
        <SetupModal data={data} setData={setData} />
      </ArticleWrapper>
      <ArticleWrapper>
        <Movies movies={movies} setMovies={setMovies} />
      </ArticleWrapper>
    </SectionWrapper>
  );
}

const SectionWrapper = styled.section`
  width: 100%;
  height: auto;
`;

const ArticleWrapper = styled.article`
  box-sizing: border-box;
  width: 100%;
  height: auto;
  padding: 24px;
`;

export default Home;