package com.spring.tests.springtube.Controllers;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

import com.spring.tests.springtube.Beans.Response.StatisticResponse;
import com.spring.tests.springtube.Beans.request.CommentRequest;
import com.spring.tests.springtube.Beans.request.LikeRequest;
import com.spring.tests.springtube.Entities.CommentEntity;
import com.spring.tests.springtube.Entities.LikedEntity;
import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Services.ViewService;
import reactor.core.publisher.Mono;
import com.spring.tests.springtube.Services.VideoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
@CrossOrigin //Опасно! Исправить
public class VideoController {

   @Autowired
   VideoService videoService;
   @Autowired
   ViewService viewService;


  @PostMapping("/upload")
  public ResponseEntity uploadVideo(@RequestParam String name,@RequestParam String description, @RequestParam String username, @RequestParam("file") MultipartFile file){
    try {
        System.out.println(username);
        System.out.println(name);
        System.out.println(description);
         videoService.uploading(username,name,description,file);
         return ResponseEntity.ok("Видео загружено");
      }catch (Exception e){
        return ResponseEntity.badRequest().body("Ошибка при загрузке" + e.getMessage());
     }
  }

    @GetMapping("/video/{UUID}")
    public ResponseEntity getOne(@PathVariable String UUID){
        try {
            return ResponseEntity.ok(videoService.getOne(UUID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при загрузке");
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity getAllUserVideos(@PathVariable String author){
      try {
          return ResponseEntity.ok(videoService.getAllUserVideos(author));
      }catch (Exception e){
          return ResponseEntity.badRequest().body("Ошибка при загрузке");
      }
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        try {
            return ResponseEntity.ok(videoService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при загрузке");
        }
    }


    @DeleteMapping("/delete/{UUID}")
    public ResponseEntity deleteVideo(@PathVariable String UUID){
        try {
            videoService.deleting(UUID);
            return ResponseEntity.ok("Видео удалено");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при удалении");
        }
    }

    @GetMapping("/watch/{UUID}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@PathVariable String UUID) throws URISyntaxException {
        viewService.countView(UUID);
        return Mono.just(videoService.streaming(UUID));
     }

     @PostMapping("video/comment")
     public CommentEntity postComment(@RequestBody CommentRequest request){
            return viewService.commentVideo(request.getAuthor(), request.getBody(), request.getVideoId());
     }

     @GetMapping("video/comments/{UUID}")
     public Iterable<CommentEntity> getAllComments(@PathVariable String UUID){
        return viewService.getComments(UUID);
     }

    @PutMapping("video/countview/{UUID}")
    public void countView(@PathVariable String UUID){
      viewService.countView(UUID);
    }
    @GetMapping("video/views/{UUID}")
    public int getViews(@PathVariable String UUID){
       return viewService.getViews(UUID);
    }
    @PutMapping("video/like")
    public void applyLikes(@RequestBody LikeRequest request){
      if(request.getValue().equals("1")){
          viewService.likeVideo(request.getVideoId(),request.getUser());
      }else if(request.getValue().equals("-1")){
          viewService.dislikeVideo(request.getVideoId(),request.getUser());
      }
    }

    @GetMapping("video/likes/{UUID}")
    public int countLikes(@PathVariable String UUID){
      return viewService.countLikes(UUID);
    }

    @GetMapping("/liked/{user}")
    public ArrayList<VideoEntity> getLikedVideos(@PathVariable String user){
      return viewService.getLikedVideos(user);
    }


}
