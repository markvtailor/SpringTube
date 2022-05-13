import axios from "axios";
import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import "video.js/dist/video-js.css";
import VideoJS from "../components/VideoJS";
import Comments from "../components/Comments";
import CommentVideo from "../hooks/CommentVideo";
import LikeVideo from "../hooks/LikeVideo";
import DislikeVideo from "../hooks/DislikeVideo";
import AuthContext from "../context/AuthProvider";
const BASE_URL='http://localhost:8080'



export function WatchPage(props){

  const context = useContext(AuthContext);
  let { id } = useParams();
  const [videoComment, setComment] = useState('');
  const [videoEntity,setVideoEntity] = useState([]);
  const [likes,setLikes] = useState(0);
  const [views,setViews] = useState();
  const fetchVideo = async () => {
    await axios.get(BASE_URL+"/videos/video/"+id).then(res => {
      setVideoEntity(res.data);
    })
  };
  const countView = async () => {
    await axios.put(BASE_URL+"/videos/video/countview/"+id);
  }
  const likesCount= async () => {
    await axios.get(BASE_URL+"/videos/video/likes/"+id).then(res =>{
      setLikes(res.data)
    })
  }
  const viewsCount = async () => {
    await axios.get(BASE_URL+"/videos/video/views/"+id).then(res =>{
      setViews(res.data);
    })
  }
  useEffect(() => {
    fetchVideo();
    countView();
    likesCount();
    viewsCount();
  },[]);
  const playerRef = React.useRef(null);
  const videoJsOptions = { 
    html5: {vhs: {withCredentials: true}},
    autoplay: false,
    controls: true,
    responsive: true,
    fluid: true,
    sources: [{
      src: BASE_URL+"/videos/watch/"+id,
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
      <LikeVideo user={context.auth.user} videoId={id} />
      <DislikeVideo user={context.auth.user} videoId={id}/>
      <label>Описание</label>
      <p>Рейтинг {likes}</p>
      <p>Просмотры {views}</p>
      <p>{videoEntity.description}</p>
      <label>Комментировать...</label>
        <textarea className="textarea" value={videoComment}
        onChange={(e)=> setComment(e.target.value)} required></textarea>
      <CommentVideo author={context.auth.user} body={videoComment} id={videoEntity.uniqueVideoId} />
      <Comments id={id}/>
      
    </div>)
  
  
  
}
  export default WatchPage;