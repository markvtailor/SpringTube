
import axios from "axios";
import React, {useState, useEffect} from "react";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";

const VideosList = () => {
    const [videosList,setVideosList] = useState([]);
    const fetchVideosList = () => {
      axios.get("http://localhost:8080/videos/all").then(res => {
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
              videoUrl={"http://localhost:4566/bucket1/"+videoEntity.uniqueVideoId}
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