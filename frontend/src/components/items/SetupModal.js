import { Form } from "react-router-dom";
import styled from "styled-components";
import { api } from "../../Setting";

function SetupModal({ data, setData }) {
  const next = () => {
    const values = Object.values(data);
    const result = values.every(value => value !== "");
    if (result) {
      $("[data-modal='1']").hide();
      $("[data-modal='2']").show();
    }
  };
  
  const $ = require("jquery");
  const prev = () => {
    $("[data-modal='2']").hide();
    $("[data-modal='1']").show();
  };
  const cancle = () => {
    $("form")[0].reset();
    $("[data-modal='1']").hide()
  };
  const preview = ({ target }) => {
    const img = $("[data-poster]")[0];
    const reader = new FileReader();
    const files = target.files;
    if (files && files[0]) {
      reader.onload = (e) => {
        img.src = e.target.result;
      }
      setData({...data, files: files[0]})
      reader.readAsDataURL(files[0]);
    } else {
      img.src = "";
    }
  };
  const complete = () => {
    if (data.files) {
      const formData = new FormData();
      for (let [key, value] of Object.entries(data)) {
        formData.append(key, value);
      };
      api.post("/apixx/add/movie", formData)
      .then(response => window.location.reload());
    } else {
      alert("포스터는 필수입니다.");
    }
  }

  return (
    <form>
      <Modal data-modal="1">
        <ModalBody>
          <InputGroup>
            <Label className="primary">제목</Label>
            <Input type="text"
              placeholder="영화 제목을 입력하세요."
              onChange={(e) => setData({ ...data, title: e.target.value })} />
          </InputGroup>
          <InputGroup>
            <Label className="primary">장르</Label>
            <Input type="text"
              placeholder="영화 장르를 입력하세요."
              onChange={(e) => setData({ ...data, genre: e.target.value })} />
          </InputGroup>
          <InputGroup>
            <Label className="primary">개요</Label>
            <Textarea placeholder="개요를 입력하세요."
              onChange={(e) => setData({ ...data, synopsis: e.target.value })}></Textarea>
          </InputGroup>
          <Button className="primary" onClick={next}>다음</Button>
          <Button className="secondary" onClick={cancle}>취소</Button>
        </ModalBody>
      </Modal>
      <Modal data-modal="2">
        <ModalBody>
          <Poster data-poster></Poster>
          <InputGroup>
            <Label className="primary">포스터</Label>
            <Input type="file" name="poster" onChange={(e) => preview(e)}></Input>
          </InputGroup>
          <Button className="primary" onClick={complete}>완료</Button>
          <Button className="secondary" onClick={prev}>이전</Button>
        </ModalBody>
      </Modal>
    </form>
  );
};

const Poster = styled.img`

`;

const Button = styled.button.attrs({ type: "button" })`
  &.primary {
    color: white;
    background-color: var(--var-primary);
  }
  &.secondary {
    color: white;
    background-color: var(--var-secondary);
  }
  padding: 9px;
  border: none;
  border-radius: 5px;
`;

export const Textarea = styled.textarea`
  resize: none;
  width: 100%;
  height: 30vh;
  padding: 12px;
  box-sizing: border-box;
`;

export const Input = styled.input`
  &[type^="file"]:hover {
    background-color: #222;
    color: white;
    
  }
  &::file-selector-button {
    padding: 4px;
  }
  width: 100%;
  height: 2rem;
  box-sizing: border-box;
  padding: 0 12px;
  border: 1px solid black;
  border-radius: 0.5rem;
`;

export const Label = styled.label`
  font-weight: bold;
`;

export const InputGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6px;
  width: 100%;
  height: auto;
  margin: 9px 0;
  padding: 0;
`;

export const ModalBody = styled.div`
  &.text-center {
    text-align: center;
  }
  width: 50vh;
  height: auto;
  margin: 25% auto;
  padding: 1rem;
  background-color: #eee;
`;

export const Modal = styled.div`
  &.show {
    display: block;
  }
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

export default SetupModal;