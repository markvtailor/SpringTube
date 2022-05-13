

import axios from "axios"


const DislikeVideo = (props) =>{

    const dislike = async () =>{
        console.log(props.user)
        const response = await axios.put("http://localhost:8080/videos/video/like/",JSON.stringify({user: props.user,videoId: props.videoId,value: "-1"}),
        {
            headers: {'Content-Type': 'application/JSON'},
        });

        return response.data;
    }

    return(
            <button onClick={()=>dislike()}>Дизлайк</button>
    )
}

export default DislikeVideo;