import axios from "axios"
const BASE_URL='http://localhost:8080'

const LikeVideo = (props) =>{

    const like = async () =>{
        const response = await axios.put(BASE_URL+"/videos/video/like/",JSON.stringify({user: props.user,videoId: props.videoId,value: "1"}),
        {
            headers: {'Content-Type': 'application/JSON'},
        });

        return response.data;
    }

    return(
            <button onClick={()=>like()}>Лайк</button>
    )
}

export default LikeVideo;