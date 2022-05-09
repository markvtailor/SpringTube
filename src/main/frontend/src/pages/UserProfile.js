import axios from "axios";
import React, {useState, useEffect} from "react";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react"; 

const UserProfile = (props) => {
    props.data? 
      console.log("the current is " + props.data) 
      : 
      console.log("Никого нет")
    //console.log("current is" + props.data)
    const context = useContext(AuthContext);
    const [videosList,setVideosList] = useState([]);
    const fetchVideosList = () => {
      props.data?
      axios.get("http://localhost:8080/videos/"+props.data).then(res => {
          console.log(res);
          setVideosList(res.data);
        })
      :
      axios.get("http://localhost:8080/videos/"+context.auth.user).then(res => {
          console.log(res);
          setVideosList(res.data);
        })

    };

    useEffect(() => {
      fetchVideosList();
    },[]);
    return videosList.map((videoEntity,index) => {
        const deleteVideo = async () => {
            

            console.log("id"+videoEntity.uniqueVideoId)
            
            const response = await axios.delete("http://localhost:8080/videos/delete/" + videoEntity.uniqueVideoId)
          
            return response.data;
        }
      return (     
        <div key={index}>
        <h1>Профиль пользователя {videoEntity.author}</h1>
        <h2>{videoEntity.name}</h2>
        <NavLink to={"/watch/"+videoEntity.uniqueVideoId}>
           <VideoImageThumbnail
              videoUrl={"http://localhost:4566/" + videoEntity.author + "/"+videoEntity.uniqueVideoId}
              thumbnailHandler={(thumbnail) => console.log(thumbnail)}
              width={800}
              height={600}
              alt="my test video"
              />
        </NavLink>
        <button onClick={()=>deleteVideo()}>Удалить</button>
        <p>{videoEntity.uniqueVideoId}</p>
      </div>)
    })
  };

  export default UserProfile;