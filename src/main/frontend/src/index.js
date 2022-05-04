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
import Register from './components/Register';
import Login from './components/Login';
import { AuthProvider } from './context/AuthProvider';
import RequireAuth from './components/RequireAuth';
import Unauthorized from './components/Unauthorized';
import AdminBoard from './pages/AdminBoard';

const ROLES = {
  'User': "USER",
  'Admin': "ROLE_ADMIN"
}

const rootElement = document.getElementById("root");
render(
  <AuthProvider>
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="videos" element={<VideosList />} />
      <Route path="watch/:id" element={<WatchPage/>}/>
      <Route path="registration" element={<Register/>}/>
      <Route path="login" element={<Login/>}/>
      <Route path="unauthorized" element={<Unauthorized/>}/>
      
      <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
        <Route path="upload" element={<UploadingPage />} />
        <Route path="adminboard" element={<AdminBoard />} />
      </Route>
      
    </Routes>
  </BrowserRouter>
  </AuthProvider>,

  rootElement
);



// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
//reportWebVitals();
