package com.spring.tests.springtube.Services;

import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";

    public VideoEntity uploading(String name, String description, MultipartFile file) throws IOException, URISyntaxException {


        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        if(listBucketsResponse.buckets().isEmpty()){
            S3Waiter s3Waiter = s3.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket("bucket1").build();
            s3.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder().bucket("bucket1").build();
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println("bucket1 is ready");
        }
        final String videoUUID = String.valueOf(UUID.randomUUID());
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("bucket1")
                .key(videoUUID).contentType("video/mp4")
                .build();
        s3.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available()));
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setUniqueVideoId(videoUUID);
        videoEntity.setName(name);
        videoEntity.setDescription(description);
        videoEntity.setViews(0);
        return videoRepository.save(videoEntity);
    }

    public void deleting(String UUID) throws URISyntaxException {
        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket("bucket1").key(UUID).build();
        s3.deleteObject(deleteObjectRequest);
        videoRepository.deleteByUniqueVideoId( UUID);
    }

    public ResponseEntity<byte[]> streaming(String UUID) throws URISyntaxException  {
        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        try{
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket("bucket1").key(UUID).build();
            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(getObjectRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .header(CONTENT_TYPE, VIDEO_CONTENT + "mp4")
                    .header(CONTENT_LENGTH, String.valueOf(objectBytes.asByteArray().length))
                    .body(objectBytes.asByteArray());
        }catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
         }
        return null;
    }
    public VideoEntity getOne(String UUID){
         VideoEntity video = videoRepository.findByUniqueVideoId(UUID);
         return video;
    }

    public Iterable<VideoEntity> getAll(){
        Iterable<VideoEntity> videos = videoRepository.findAll();
        return videos;
    }

}
