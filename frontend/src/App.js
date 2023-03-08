import './App.css';
import { BrowserRouter } from 'react-router-dom';
import Header from './components/Header';
import Main from './components/Main';
import { useState } from 'react';
import styled from 'styled-components';

function App() {
  const [ user, setUser ] = useState(null);
  return (
    <BrowserRouter>
      <Wrapper>
        <Header user={user} setUser={setUser} />
        <Main user={user} />
      </Wrapper>
    </BrowserRouter>
  );
}

const Wrapper = styled.div`
  --var-primary: #0d6efd;
  --var-primary-hover: #025fea;
  --var-danger: #dc3545;
  --var-danger-hover: #c12232;
  --var-secondary: #8a9198;
  --var-secondary-hover: #767d85;
  position: relative;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
`;


export const Button = styled.button`
  &.w-100 {
    width: 100%;
  }
  &.primary         { background-color: var(--var-primary); }
  &.primary:hover   { background-color: var(--var-primary-hover);}
  &.secondary       { background-color: var(--var-secondary); }
  &.secondary:hover { background-color: var(--var-secondary-hover); }
  &.danger          { background-color: var(--var-danger); }
  &.danger:hover    { background-color: var(--var-danger-hover); }
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 9px;
  font-size: 16px;
  color: white;
`;


export default App;
