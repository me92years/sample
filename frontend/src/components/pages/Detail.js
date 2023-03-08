import { useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import Movie from "../items/Movie";
import Review from "../items/Review";

function Detail({ user }) {
  const { no } = useParams();
  const [movie, setMovie] = useState(null);
  const [reviews, setReviews] = useState(null);

  return (
    <>
      <SectionWrapper>
        <Movie no={no} movie={movie} setMovie={setMovie} />
      </SectionWrapper>
      <SectionWrapper>
        <Review no={no} reviews={reviews} setReviews={setReviews} user={user} />
      </SectionWrapper>
    </>
  );
}

const SectionWrapper = styled.section`
  width: 100%;
  height: auto;
`;

export default Detail;