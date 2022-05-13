import axios from "axios";
import React, {useState, useEffect} from "react";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react"; 
import UserVideos from "../components/UserVideos";
import { Link } from "react-router-dom";
import Select from 'react-select';
import LikedVideos from "../components/LikedVideos";

const UserProfile = (props) => {

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
      if (list == 'liked'){
        return <LikedVideos data={context.auth.user} />
      } else if (list == 'your'){
        return <UserVideos data={context.auth.user} />
      }
      
    }


  const context = useContext(AuthContext);
      return (     
        <section>
            <h1>Профиль пользователя {context.auth.user}</h1>
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