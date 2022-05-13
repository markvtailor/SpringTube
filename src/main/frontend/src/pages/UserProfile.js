
import React, {useState} from "react";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react"; 
import UserVideos from "../components/UserVideos";
import { Link } from "react-router-dom";
import Select from 'react-select';
import LikedVideos from "../components/LikedVideos";

const UserProfile = (props) => {
  const context = useContext(AuthContext);
  const user = props.user? props.user : context.auth.user

  const [listType, setListType] = useState();
    
  const options = [
      {label: 'Ваши видео',
       value: 'your'
      },
       {
           label:'Понравившиеся видео',
           value: 'liked'
       }
  ]
    const getListType = () => {
        return listType ? options.find(type => type.value === listType) : ''
    }

    const onChange = (newValue) => {
    setListType(newValue.value)
    }

    function RenderList(props){
      const list = props.listType;
      if (list === 'liked'){
        return <LikedVideos data={user} />
      } else if (list === 'your'){
        return <UserVideos data={user} />
      }
      
    }



      return (     
        <section>
            <h1>Профиль пользователя {user}</h1>
            <ul>
                 <Select onChange={onChange} value={getListType()} options={options} />
            </ul>
            <br/>
            <RenderList listType={listType} />
            <br/>
            <div className="flexGrow">
                <Link to="/">Главная</Link>
            </div>
        </section>
   ) }
  

  export default UserProfile;