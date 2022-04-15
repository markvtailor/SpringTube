import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './Home';
//import Signup from '../';

const Main = () => {
  return (
    <Routes>
      <Route path='/' element={Home}></Route>
      <Route path='/upload' element={Upload}></Route>
    </Routes>
  );
}

export default Main;