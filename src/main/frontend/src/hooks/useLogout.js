import axios from "../api/axios";
import useAuth from "./useAuth";
import { useContext } from "react";
import AuthContext from "../context/AuthProvider";

const useLogout = () => {
    const {setAuth} = useAuth();
    const context = useContext(AuthContext);


    const logout = async () => {
        setAuth({})
        try {
            const response = await axios.post("user/auth/logout",JSON.stringify({refreshToken: context.auth.refreshToken}),
        {
            headers: {'Content-Type': 'application/JSON'},
        });
        } catch (error) {
            console.error(error)
        }
    }

    return logout;
}

export default useLogout;