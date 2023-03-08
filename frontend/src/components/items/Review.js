import { useEffect, useRef } from "react";
import styled from "styled-components";
import { Button } from "../../App";
import { api, LOCAL } from "../../Setting";

const $ = require('jquery');
function Review({ no, reviews, setReviews, user }) {
  const textarea = useRef();
  const show = () => { $("[data-modal]").show(); }
  const cancle = () => { $("[data-modal]").hide(); }
  const complete = () => {
    const text = textarea.current.value;
    if (!text) {
      alert("리뷰가 작성되지 않았습니다.");
      return;
    }
    const data = { ref: no, reviewer: user.nickname, text: text };
    api.post("/apix/add/review", data)
      .then(() => {
        alert("댓글이 등록되었습니다.");
        window.location.href = "/detail/" + no;
      });
  }
  const mod = (rno, reviewer) => { 

    // api.post(LOCAL + "/apix/mod/review", 
    // { no: rno, reviewer: reviewer }, 
    // { headers: { "Content-Type": "application/json" } })
    // .then(res => (res) ? window.location.href = "/detail/" + no : alert("리뷰를 수정하지 못했습니다. 다시 시도 해 주세요."));
   };
  const del = (rno, reviewer) => { 
    if (!window.confirm("정말 삭제하시겠습니까?")) return;
    console.log(rno, reviewer);
    api.post(LOCAL + "/apix/del/review", 
    { no: rno, reviewer: reviewer }, 
    { headers: { "Content-Type": "application/json" } })
    .then(res => (res) ? window.location.href = "/detail/" + no : alert("리뷰를 삭제하지 못했습니다. 다시 시도 해 주세요."));
  };

  useEffect(() => {
    api.get("/apio/get/review/" + no)
      .then(res => setReviews(res.data));
  }, []);

  console.log(reviews);
  return (
    <>
      <ArticleWrapper>
        {user ?
          <Button className="primary" onClick={show}>리뷰달기</Button>
          : ""}
        <Modal data-modal>
          <form>
            <ModalBody>
              <InputGroup>
                <Label>리뷰</Label>
                <Textarea ref={textarea}></Textarea>
              </InputGroup>
              <Button className="primary"   type="button" onClick={complete}>완료</Button>
              <Button className="secondary" type="button" onClick={cancle}>취소</Button>
            </ModalBody>
          </form>
        </Modal>
      </ArticleWrapper>
      <ArticleWrapper>
        {!reviews ? null : reviews.map(review => (
          <Reviews key={review.no}>
            <Info>{review.no}</Info>
            <Info>{review.reviewer}</Info>
            <Info>{review.text}</Info>
            {(user && review.reviewer === user.nickname) ? 
            <Info>
              <Button className="primary" onClick={ (e) => mod(review.no) }>수정</Button>
              <Button className="danger"  onClick={ (e) => del(review.no, review.reviewer) }>삭제</Button>
            </Info> : ""}
          </Reviews>
        ))}
      </ArticleWrapper>
    </>
  );
}
const Info = styled.div`
  &:first-of-type{
    border: none;
  }
  padding: 16px;
  border-left: 1px solid black;
`;
const Reviews = styled.div`
  display: grid;
  grid-template-columns: 100px 250px auto 133px;
  box-sizing: border-box; 
  width: 100%;
  margin-bottom: 9px;
  border-radius: 16px;
  background-color: white;
`;

const Label = styled.label`
  font-weight: bold;
`;

const Textarea = styled.textarea`
  resize: none;
  width: 100%;
  height: 30vh;
  padding: 12px;
  box-sizing: border-box;
`;

const InputGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%;
  height: auto;
  margin: 9px 0;
  padding: 0;
`;

const ArticleWrapper = styled.article`  
  width: 100%;
  height: auto;
  padding: 24px;
  box-sizing: border-box;
`;
const ModalBody = styled.div`
  width: 50vh;
  height: auto;
  margin: 25% auto;
  padding: 1rem;
  background-color: #eee;
`;
const Modal = styled.div`
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1;
`;

export default Review;