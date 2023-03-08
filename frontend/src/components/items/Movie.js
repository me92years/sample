import { useEffect } from "react";
import styled from "styled-components";
import { api, LOCAL } from "../../Setting";

function Movie({ no, movie, setMovie }) {

  useEffect(() => {
    api.get(LOCAL + "/apio/get/detail/" + no)
    .then(res => setMovie(res.data));
  }, []);
  if (!movie) {
    return ( <div>Loading...</div> );
  };

  return (
    <ArticleWrapper>
      <Items>
        <Item>
          <Img src={LOCAL + "/apio/get/poster?posterUrl=" + movie.posterUrl} />
        </Item>
        <Item>
          <Title>{movie.title}</Title>
        </Item>
        <Item>{movie.genre}</Item>
        <Item>{movie.synopsis}</Item>
      </Items>
    </ArticleWrapper>
  );
}

const ArticleWrapper = styled.article`
  width: 100%;
  height: auto;
  margin: 0;
  padding: 24px;
  box-sizing: border-box;
  color: white;
  background-color: #333;
`;

const Items = styled.ul`
  display: grid;
  grid: 60px 40px auto / 223px auto;
  gap: 16px;
  overflow: hidden;
`;

const Item = styled.li`
  &:nth-child(1) {
    grid-row: span 3;
  }
  border: none;
  margin: 0;
  padding: 0;
  list-style-type: none;
`;

const Img = styled.img`
  width: 222px
  height: 332px;
  border: none;
  border-radius: 5px;  
`;

const Title = styled.h1`
  margin: 0;
  font-weight: 500;
`;

export default Movie;