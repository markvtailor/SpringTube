package com.spring.tests.springtube.Controllers;

import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Repositories.VideoRepository;
import com.spring.tests.springtube.Services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

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
    public ResponseEntity getOne(@RequestParam Long id){
        try {
            return ResponseEntity.ok(videoService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при загрузке");
        }
    }

    @GetMapping
    public ResponseEntity getAll(){
        try {
            return ResponseEntity.ok(videoService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при загрузке");
        }
    }
    @GetMapping("/form")
    public String form(){
            return "uploadForm";

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVideo(@PathVariable Long id){
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok("Видео удалено");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при удалении");
        }
    }


}
