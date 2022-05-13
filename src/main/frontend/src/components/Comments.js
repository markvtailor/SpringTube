import { useState,useEffect } from "react"
import axios from "axios";



const Comments = (props) =>{

    const [comments,setComments] = useState([]);
    const fetchComments = () => {
        console.log("the props" + props.id)
      axios.get("http://localhost:8080/videos/video/comments/" + props.id).then(res => {
        console.log(res);
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