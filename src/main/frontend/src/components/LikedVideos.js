import { useState, useEffect } from "react";
import axios from "axios";
import VideoImageThumbnail from 'react-video-thumbnail-image';
import { NavLink } from "react-router-dom";
const BASE_URL='http://localhost:8080'


const LikedVideos = (props) => {
    const [videosList,setVideosList] = useState([]);
    const fetchVideosList = () => {
      axios.get(BASE_URL+"/videos/liked/"+props.data).then(res => {
          setVideosList(res.data);
        })
    }

                            
    useEffect(() => {
        let isMounted = true
        const controller = new AbortController();
        fetchVideosList()         
        return () => {
            isMounted = false;
            controller.abort();
        }

    },[])

     return videosList.map((videoEntity,index) => {
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
        <p>{videoEntity.uniqueVideoId}</p>   
      </div>)
    })
}

export default LikedVideos;