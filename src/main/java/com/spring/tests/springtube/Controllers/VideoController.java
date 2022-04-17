package com.spring.tests.springtube.Controllers;


import java.net.URISyntaxException;
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
   private VideoService videoService;


  @PostMapping("/upload")
  public ResponseEntity uploadVideo(@RequestParam String name,@RequestParam String description, @RequestParam("file") MultipartFile file){
    try {
        System.out.println(name);
        System.out.println(description);
         videoService.uploading(name,description,file);
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

    @GetMapping("/all")
    public ResponseEntity getAll(){
        try {
            return ResponseEntity.ok(videoService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при загрузке");
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity deleteVideo(@RequestParam String UUID){
        try {
            videoService.deleting(UUID);
            return ResponseEntity.ok("Видео удалено");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при удалении");
        }
    }

    @GetMapping("/watch/{UUID}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@PathVariable String UUID) throws URISyntaxException {

        String videoId = UUID;
        return Mono.just(videoService.streaming(videoId));
     }


}
