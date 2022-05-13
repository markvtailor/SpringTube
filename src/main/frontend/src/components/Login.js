import { useRef, useState, useEffect} from "react";
import useAuth from "../hooks/useAuth";
import axios from "../api/axios";
import {useNavigate, useLocation } from 'react-router-dom';

const LOGIN_URL = 'user/auth/login';


const Login = () => {
    const {setAuth} = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');


    useEffect(()=> {
        userRef.current.focus();
    },[])

    useEffect(()=> {
        setErrMsg('');
    },[user,pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({username: user, password: pwd}),
                {
                    headers: {'Content-Type': 'application/JSON'},
                });
            const accessToken = response?.data?.accessToken;
            const refreshToken = response?.data?.refreshToken;
            const roles = response?.data?.roles;
            
            setAuth({user,pwd,roles,accessToken,refreshToken}); //Куда убрать рефреш?
            setUser('');
            setPwd('');
            navigate(from, {replace:true});
        } catch (error) {
            if(!error?.response){
                setErrMsg('No server response');
            } else if (error.response?.status === 400){
                setErrMsg('Неверные данные')
            } else{
                setErrMsg('Вход не удался')
            }
            errRef.current.focus();
            
        }

    }
    return(
        <section>
             <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
             <h1>Вход</h1>
             <form onSubmit={handleSubmit}>
                 <label htmlFor="username">Логин:</label>
                        <input
                            type="text"
                            id="username"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(e) => setUser(e.target.value)}
                            value={user}
                            required
                        />

                 <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            onChange={(e) => setPwd(e.target.value)}
                            value={pwd}
                            required
                        />

                    <button>Войти</button>
             </form>
                         <p>
                            Нет аккаунта?<br />
                            <span className="line">
                                <a href="/registration">Регистрация</a>
                            </span>
                        </p>
        </section>            
    )
}

export default Login