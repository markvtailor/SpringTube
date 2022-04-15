import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from "react";
import { Link } from "react-router-dom";
import WatchPage from './pages/WatchPage';



function App() {
  return (
    <div>
      <h1>Welcome To SpringTube</h1>
      <nav
        style={{
          borderBottom: "solid 1px",
          paddingBottom: "1rem",
        }}
      >
        <Link to="/videos">Videos</Link> |{" "}
        <Link to="/upload">Upload</Link> |{" "}
        <Link to="/watch/:id">Watch</Link>
      </nav>
    </div>
  )}

export default App;
