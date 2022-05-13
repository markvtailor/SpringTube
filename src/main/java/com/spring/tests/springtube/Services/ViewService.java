package com.spring.tests.springtube.Services;


import com.spring.tests.springtube.Entities.CommentEntity;
import com.spring.tests.springtube.Entities.LikedEntity;
import com.spring.tests.springtube.Entities.VideoEntity;
import com.spring.tests.springtube.Entities.ViewEntity;
import com.spring.tests.springtube.Repositories.CommentRepository;
import com.spring.tests.springtube.Repositories.LikedRepository;
import com.spring.tests.springtube.Repositories.VideoRepository;
import com.spring.tests.springtube.Repositories.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ViewService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ViewRepository viewRepository;
    @Autowired
    LikedRepository likedRepository;
    @Autowired
    VideoRepository videoRepository;

    public CommentEntity commentVideo(String author, String body,String videoId){
        CommentEntity comment = new CommentEntity();
        comment.setAuthor(author);
        comment.setBody(body);
        comment.setVideo(videoId);
        comment.setDate(new Date());
        return commentRepository.save(comment);
    }

    public Iterable<CommentEntity> getComments(String videoId){
        return commentRepository.findAllByVideo(videoId);
    }

    public void countView(String videoId){
        if (viewRepository.findByUniqueVideoId(videoId) != null) {
            ViewEntity view = viewRepository.findByUniqueVideoId(videoId);
            int currentViews = viewRepository.findByUniqueVideoId(videoId).getViews();
            view.setViews(currentViews + 1);
            viewRepository.save(view);
        }
    }

    public int getViews(String videoId){
        return viewRepository.findByUniqueVideoId(videoId).getViews();
    }

    public void likeVideo(String videoId,String user){
        VideoEntity video = videoRepository.findByUniqueVideoId(videoId);
        if(likedRepository.findByUniqueVideoIdAndUsername(videoId,user) != null && likedRepository.findByUniqueVideoIdAndUsername(videoId,user).getValue() == 1){
           video.setLikes(video.getLikes()-1);
           likedRepository.delete(likedRepository.findByUniqueVideoIdAndUsername(videoId,user));
        }else if(likedRepository.findByUniqueVideoIdAndUsername(videoId,user) != null && likedRepository.findByUniqueVideoIdAndUsername(videoId,user).getValue() == -1){
            video.setLikes(video.getLikes()+2);
            likedRepository.delete(likedRepository.findByUniqueVideoIdAndUsername(videoId, user));
            LikedEntity like = new LikedEntity();
            like.setUniqueVideoId(videoId);
            like.setUsername(user);
            like.setValue(1);
            likedRepository.save(like);
        }else{
            video.setLikes(video.getLikes()+1);
            LikedEntity like = new LikedEntity();
            like.setUniqueVideoId(videoId);
            like.setUsername(user);
            like.setValue(1);
            likedRepository.save(like);
        }
    }

    public void dislikeVideo(String videoId,String user){
        VideoEntity video = videoRepository.findByUniqueVideoId(videoId);
        if(likedRepository.findByUniqueVideoIdAndUsername(videoId,user) != null && likedRepository.findByUniqueVideoIdAndUsername(videoId,user).getValue() == -1){
            video.setLikes(video.getLikes()+1);
            likedRepository.delete(likedRepository.findByUniqueVideoIdAndUsername(videoId,user));
        } else if(likedRepository.findByUniqueVideoIdAndUsername(videoId,user) != null && likedRepository.findByUniqueVideoIdAndUsername(videoId,user).getValue() == 1) {
            video.setLikes(video.getLikes()-2);
            likedRepository.delete(likedRepository.findByUniqueVideoIdAndUsername(videoId, user));
            LikedEntity like = new LikedEntity();
            like.setUniqueVideoId(videoId);
            like.setUsername(user);
            like.setValue(-1);
            likedRepository.save(like);
        }else{
            video.setLikes(video.getLikes()-1);
            LikedEntity like = new LikedEntity();
            like.setUniqueVideoId(videoId);
            like.setUsername(user);
            like.setValue(-1);
            likedRepository.save(like);
        }
    }

    public int countLikes(String uniqueVideoId) {
        AtomicInteger sum = new AtomicInteger();
        Iterable<LikedEntity> likes = likedRepository.findAllByUniqueVideoId(uniqueVideoId);
        likes.forEach(likedEntity -> {
            sum.addAndGet(likedEntity.getValue());
        });
        return sum.get();
    }

    public ArrayList<VideoEntity> getLikedVideos(String user) {
        ArrayList<VideoEntity> videos = new ArrayList<>();
       likedRepository.findAllByUsernameAndValue(user,1).
                forEach(likedEntity -> videos.add(videoRepository.findByUniqueVideoId(likedEntity.getUniqueVideoId())));
       return videos;
    }
}
