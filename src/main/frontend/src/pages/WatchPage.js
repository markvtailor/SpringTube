import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import videojs from "video.js";
import "video.js/dist/video-js.css";
import VideoJS from "../components/VideoJS";



export function WatchPage(props){
  let { id } = useParams();
  const [videoEntity,setVideoEntity] = useState([]);
  const fetchVideo = () => {
    axios.get("http://localhost:8080/videos/video/"+id).then(res => {
      setVideoEntity(res.data);
    })
  };
  useEffect(() => {
    fetchVideo();
  },[]);
  console.log("http://localhost:8080/videos/watch/"+id)
  const playerRef = React.useRef(null);
  const videoJsOptions = { 
    html5: {vhs: {withCredentials: true}},
    autoplay: false,
    controls: true,
    responsive: true,
    fluid: true,
    sources: [{
      src: "http://localhost:8080/videos/watch/"+id,
      type: 'video/mp4'
    }]
  }

  const handlePlayerReady = (player) => {
    playerRef.current = player;
    player.on('waiting', () => {
      console.log('player is waiting');
    });

    player.on('dispose', () => {
      console.log('player will dispose');
    });
  };

  
    return (<div>
      <h1>{videoEntity.name}</h1>
      <VideoJS options={videoJsOptions} onReady={handlePlayerReady} />
      <label>Описание</label>
      <p>{videoEntity.description}</p>
    </div>)
  
  
  
}
  export default WatchPage;