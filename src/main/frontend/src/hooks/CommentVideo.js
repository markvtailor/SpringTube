import axios from "axios"
const BASE_URL='http://localhost:8080'

const CommentVideo = (props) =>{

    const postComment = async () =>{
        const response = await axios.post(BASE_URL+"/videos/video/comment/",JSON.stringify({author: props.author,body: props.body,videoId: props.id}),
        {
            headers: {'Content-Type': 'application/JSON'},
        });

        return response.data;
    }

    return(
            <button onClick={()=>postComment()}>Отправить</button>
    )
}

export default CommentVideo;