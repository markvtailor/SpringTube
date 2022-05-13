import { useState, useEffect } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useLocation } from "react-router-dom";
import useRefreshToken from "../hooks/useRefreshToken";
import Select from 'react-select';
import UserProfile from "../pages/UserProfile";



const Users = () => {

    const [users,setUsers] = useState();
    const [currentUser, setCurrentUser] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();
    const refresh = useRefreshToken();

    const getCurrentUser = () => {
                            return currentUser ? users.find(user => user.value === currentUser) : ''
                        }

   const onChange = (newValue) => {
       setCurrentUser(newValue.value)
   }
                            
    useEffect(() => {
        let isMounted = true
        const controller = new AbortController();
        
        const getUsers = async () => {
            try {
                const response = await axiosPrivate.get('user/auth/users',{
                    signal: controller.signal
                })
                isMounted && setUsers(response.data)              
            } catch (error) {
                console.error(error)
                navigate('/login',{state: {from: location}, replace: true})
            }
        }
        
        getUsers();
      
                    
        return () => {
            isMounted = false;
            controller.abort();
        }

    },[])

    const options = (users) => users.map(user => ({
        label: user.username,
        value: user.username
      }));
    
    return(
        
        <article>
            <h2>Управление пользователями</h2>
            {users?.length
                ? (
                    <ul>
                        <Select onChange={onChange} value={getCurrentUser()} options={options(users)} />
                    </ul>
                ) : <p>Список пользователей пуст</p>
            }
            <UserProfile user={currentUser}/>
        </article>
    )
}

export default Users;