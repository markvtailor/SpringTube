import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
import WatchPage from './pages/WatchPage';
import useLogout from './hooks/useLogout';


function App() {

  const logout = useLogout();
  const navigate = useNavigate();
  const signOut = async () => {
    await logout();
    navigate("/login")

  }
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
        <Link to="/registration">Registration</Link>|{" "}
        <Link to="/login">Login</Link>|{" "}
        <Link to="/profile">Profile</Link>
        <button onClick={signOut}>Logout</button>
      </nav>
    </div>
  )}

export default App;
