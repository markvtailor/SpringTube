
import axios from "axios";
import React, {useState, useEffect} from "react";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";
const BASE_URL='http://localhost:8080'
const VideosList = () => {
    const [videosList,setVideosList] = useState([]);
    const fetchVideosList = () => {
      axios.get(BASE_URL+"/videos/all").then(res => {
        console.log(res);
        setVideosList(res.data);
      })
    };
    useEffect(() => {
      fetchVideosList();
    },[]);
    return videosList.map((videoEntity,index) => {
      return (<div key={index}>
        <h1>{videoEntity.name}</h1>
        <NavLink to={"/watch/"+videoEntity.uniqueVideoId}>
           <VideoImageThumbnail
              videoUrl={"http://localhost:4566/" + videoEntity.author + "/"+videoEntity.uniqueVideoId}
              thumbnailHandler={(thumbnail) => console.log(thumbnail)}
              width={800}
              height={600}
              alt="my test video"
              />
        </NavLink>
       
        <p>{videoEntity.uniqueVideoId}</p>
      </div>)
    })
  };

  export default VideosList;