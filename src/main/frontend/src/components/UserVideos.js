import axios from "axios";
import React, {useState, useEffect} from "react";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";
const BASE_URL='http://localhost:8080'

const UserVideos = (props) => {
   
    const [videosList,setVideosList] = useState([]);
    const fetchVideosList = () => {
      axios.get(BASE_URL+"/videos/"+props.data).then(res => {
          setVideosList(res.data);
         
        })
    };
    useEffect(() => {

        fetchVideosList();
      },[props]);

      return videosList.map((videoEntity,index) => {
        const deleteVideo = async () => {
            
            const response = await axios.delete(BASE_URL+"/videos/delete/" + videoEntity.uniqueVideoId)
          
            return response.data;
        }
      return (     
        <div key={index}>
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




}

export default UserVideos;