package com.spring.tests.springtube.Services;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.lambda.model.Environment;
import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Entities.ViewEntity;
import com.spring.tests.springtube.Repositories.VideoRepository;

import com.spring.tests.springtube.Repositories.ViewRepository;
import org.apache.http.client.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


@Service
public class VideoService  {

    @Value("${localstack.path:http://localhost:4566}")
    private String localstackPath;

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    ViewRepository viewRepository;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";

    AwsBasicCredentials awsCreds = AwsBasicCredentials.create("123","xyz");
    final StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCreds);
    public VideoEntity uploading(String author, String name, String description, MultipartFile file) throws IOException, URISyntaxException {
        author = author.toLowerCase();
        System.setProperty("aws.accessKeyId","123");
        final S3Client s3 = S3Client.builder().endpointOverride(new URI(localstackPath)).credentialsProvider(credentialsProvider).region(Region.EU_NORTH_1).build();
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder().bucket(author).build();
        if (!checkBucketExistence(author,s3)){
            S3Waiter s3Waiter = s3.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket(author).build();
            s3.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder().bucket(author).build();
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println("Bucket " + author + " is ready");
        }
        final String videoUUID = String.valueOf(UUID.randomUUID());
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(author)
                .key(videoUUID).contentType("video/mp4")
                .build();
        s3.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available()));
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setUniqueVideoId(videoUUID);
        videoEntity.setName(name);
        videoEntity.setDescription(description);
        videoEntity.setLikes(0);
        videoEntity.setAuthor(author);
        ViewEntity view = new ViewEntity();
        view.setUniqueVideoId(videoUUID);
        view.setViews(0);
        viewRepository.save(view);
        return videoRepository.save(videoEntity);
    }

    public void deleting(String UUID) throws URISyntaxException {
        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().
                bucket(videoRepository.findByUniqueVideoId(UUID).getAuthor().toLowerCase()).
                key(UUID).build();
        s3.deleteObject(deleteObjectRequest);
        videoRepository.deleteByUniqueVideoId( UUID);
    }

    public ResponseEntity<byte[]> streaming(String UUID) throws URISyntaxException  {
        final S3Client s3 = S3Client.builder().endpointOverride(new URI("http://localhost:4566")).region(Region.EU_NORTH_1).build();
        try{
            String author = videoRepository.findByUniqueVideoId(UUID).getAuthor().toLowerCase();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(author).key(UUID).build();
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

    public Iterable<VideoEntity> getAllUserVideos(String author){
        Iterable<VideoEntity> videos = videoRepository.findAllByAuthor(author.toLowerCase());
        return videos;
    }

    public Iterable<VideoEntity> getAll(){
        Iterable<VideoEntity> videos = videoRepository.findAll();
        return videos;
    }

    private boolean checkBucketExistence (String bucketName, S3Client s3Client){
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();

        try {
            s3Client.headBucket(headBucketRequest);
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }

}