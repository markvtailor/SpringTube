package com.spring.tests.springtube.Controllers;


import com.spring.tests.springtube.Services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
public class VideoController {

   @Autowired
   private VideoService videoService;


  @PostMapping("/upload")
  public ResponseEntity uploadVideo(@RequestParam String name, @RequestParam("file") MultipartFile file){
    try {
         videoService.uploading(name,file);
         return ResponseEntity.ok("Видео загружено");
      }catch (Exception e){
        return ResponseEntity.badRequest().body("Ошибка при загрузке");
     }
  }

    @GetMapping("/video")
    public ResponseEntity getOne(@RequestParam String name){
        try {
            return ResponseEntity.ok(videoService.getOne(name));
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
    public ResponseEntity deleteVideo(@RequestParam String name){
        try {
            videoService.deleting(name);
            return ResponseEntity.ok("Видео удалено");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при удалении");
        }
    }


}
