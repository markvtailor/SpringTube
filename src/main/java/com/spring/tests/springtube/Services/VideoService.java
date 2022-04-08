package com.spring.tests.springtube.Services;

import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Value("${upload.path}")
    private String uploadPath;


    public VideoEntity uploading(String name, MultipartFile file) throws IOException {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setName(name);
        if(!file.isEmpty()){
            File uploadedVideos = new File(uploadPath);
            if (!uploadedVideos.exists()){
                uploadedVideos.mkdir();
            }

            String fileUniqId = UUID.randomUUID().toString();
            String fileName = fileUniqId + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            videoEntity.setUniqid(fileUniqId);
        }
        return videoRepository.save(videoEntity);
    }

    public VideoEntity getOne(Long id){
         VideoEntity video = videoRepository.findById(id).get();
         return video;
    }

    public VideoEntity getAll(){
        VideoEntity videos = videoRepository.findAll().iterator().next();
        return videos;
    }

    public void deleteVideo(Long id){
        videoRepository.deleteById(id);
    }
}
