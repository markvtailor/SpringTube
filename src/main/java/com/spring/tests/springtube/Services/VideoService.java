package com.spring.tests.springtube.Services;

import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;



    public VideoEntity uploading(String name, MultipartFile file) throws IOException, URISyntaxException {


        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("bucket1")
                .key(name).contentType("video/mp4")
                .build();
        s3.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available()));
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setUniqueVideoId(String.valueOf(UUID.randomUUID()));
        videoEntity.setName(name);
        return videoRepository.save(videoEntity);
    }
    public void deleting(String name) throws URISyntaxException {
        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket("bucket1").key(name).build();
        s3.deleteObject(deleteObjectRequest);
        videoRepository.deleteByName(name);
    }
    public VideoEntity getOne(String name){
         VideoEntity video = videoRepository.findByName(name);
         return video;
    }

    public Iterable<VideoEntity> getAll(){
        Iterable<VideoEntity> videos = videoRepository.findAll();
        return videos;
    }

}
