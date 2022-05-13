import axios from "axios"


const CommentVideo = (props) =>{

    const postComment = async () =>{
        console.log(props.author)
        const response = await axios.post("http://localhost:8080/videos/video/comment/",JSON.stringify({author: props.author,body: props.body,videoId: props.id}),
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