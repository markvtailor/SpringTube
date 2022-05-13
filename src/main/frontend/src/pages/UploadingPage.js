import axios from "axios";
import { useState } from "react";
import AuthContext from "../context/AuthProvider";
import { useContext } from "react"; 
const BASE_URL='http://localhost:8080'

const UploadingPage = () => {
  const context = useContext(AuthContext);
  const [videoName, setName] = useState('');
  const [videoDescription, setDescription] = useState('');
  const [file, setFile] = useState();
    
    const uploadFileHandler = (event) => {
      setFile(event.target.files[0]);
     };


  const handleSubmit = (e) => {
    e.preventDefault();
    const config = {
      headers: {
          'content-type': 'multipart/form-data'
      }
  }
    const formData = new FormData();
    formData.append('file',file)
    formData.append('description',videoDescription)
    formData.append('name',videoName)
    formData.append('username',context.auth.user)
    axios.post(BASE_URL+"/videos/upload",formData,config)
  }

  return (
    <div className="UploadingPage">
      <h1>Загрузить видео</h1>
      <form onSubmit={handleSubmit}>
        <label>Название видео</label>
        <input type="text"
        value={videoName}
        onChange={(e)=> setName(e.target.value)}
        required
        />
        <label>Описание видео</label>
        <textarea className="textarea" value={videoDescription}
        onChange={(e)=> setDescription(e.target.value)} required></textarea>
         <input
        accept="video/mp4"
        type="file"
        onChange={uploadFileHandler}
      />
      <button>Загрузить</button>
      </form>
    </div>
  )
    
  
};
  
export default UploadingPage;