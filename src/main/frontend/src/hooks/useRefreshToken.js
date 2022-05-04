import axios from "../api/axios";
import useAuth from "./useAuth";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react";

const useRefreshToken = () => {

    const {setAuth} = useAuth();
    const context = useContext(AuthContext);

    const refresh = async () => {

        console.log(context.auth.refreshToken)
        
        console.log(context)
        const response = await axios.post("user/auth/refresh",JSON.stringify({refreshToken: context.auth.refreshToken}),
        {
            headers: {'Content-Type': 'application/JSON'},
            //withCredentials: true
        });
        setAuth(prev => {

            console.log("Test" + JSON.stringify(prev))
            console.log(response.data.accessToken)
            
            return {...prev,accessToken: response.data.accessToken}
        })
        return response.data.accessToken;
    }
    return refresh;
}

export default useRefreshToken;