import { useEffect } from "react";
import styled from "styled-components";
import { api, LOCAL } from "../../Setting";

const _ = require("lodash");

function Movies({ movies, setMovies }) {

  const getDetail = (no) => { window.location.href = "/detail/" + no; }

  useEffect(() => {
    api.get("/apio/get/movies", { headers: { "Content-Type": "application/json" }})
    .then(res => setMovies(res.data));
  }, []);
  
  if (!movies) return;

  return (
    <Items>
      {movies.map(movie => (
        <Item key={movie.no} onClick={() => getDetail(movie.no)}>
          <Img src={LOCAL + "/apio/get/poster?posterUrl=" + movie.posterUrl} />
          <Title>{movies[0].title}</Title>
        </Item>
        ))}
    </Items>
  );

}

const Items = styled.ul`
  display: grid;
  overflow-x: scroll;
  grid-template-columns: repeat(10, 200px);
  width: auto;
  height: 320px;
  background-color: #white;
`;

const Item = styled.li`
  &:hover {
    & img {
      width: 82%;
    }
    & p {
      font-size: 15px;
    }
  }
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: auto;
  min-height: 250px;
  
`;

const Img = styled.img`
  width: 80%;
  height: auto;
  border: none;
  border-radius: 24px;
  transition: 0.2s;
`;

const Title = styled.p`
  margin: 12px 0;
  color: white;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  transition: 0.2s;
`;

export default Movies;