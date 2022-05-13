import { useState,useEffect } from "react"
import axios from "axios";
const BASE_URL='http://localhost:8080'


const Comments = (props) =>{

    const [comments,setComments] = useState([]);
    const fetchComments = () => {
      axios.get(BASE_URL+"/videos/video/comments/" + props.id).then(res => {
        setComments(res.data);
      })
    };
    useEffect(() => {
      fetchComments();
    },[]);

    return comments.map((commentEntity,index) => {
        return (<div key={index}>
          <h1>{commentEntity.author}</h1>
          <text>{commentEntity.body}</text>
          <p>{commentEntity.date}</p>
        </div>)
      })
}

export default Comments;