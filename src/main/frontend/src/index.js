import React from 'react';

import { render } from "react-dom";
import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";
import App from "./App";
import VideosList from './pages/VideosList';
import UploadingPage from './pages/UploadingPage';
import WatchPage from './pages/WatchPage';
const rootElement = document.getElementById("root");
render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="videos" element={<VideosList />} />
      <Route path="upload" element={<UploadingPage />} />
      <Route path="watch/:id" element={<WatchPage/>}/>
    </Routes>
  </BrowserRouter>,
  rootElement
);



// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
//reportWebVitals();
