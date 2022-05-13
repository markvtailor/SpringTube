import axios from "../api/axios";
import useAuth from "./useAuth";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react";

const useRefreshToken = () => {

    const {setAuth} = useAuth();
    const context = useContext(AuthContext);

    const refresh = async () => {

        
        const response = await axios.post("user/auth/refresh",JSON.stringify({refreshToken: context.auth.refreshToken}),
        {
            headers: {'Content-Type': 'application/JSON'},
        });
        setAuth(prev => {

            console.log("Test" + JSON.stringify(prev))
            console.log(response.data.accessToken)
            
            return {...prev, roles: prev.roles, accessToken: response.data.accessToken}
        })
        return response.data.accessToken;
    }
    return refresh;
}

export default useRefreshToken;