package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.infra.entity.Media;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.MediaAlreadyExistsException;
import com.gui.diarioOnline.infra.exception.MediaWithUserNoException;
import com.gui.diarioOnline.infra.repository.MediaRepository;
import com.gui.diarioOnline.infra.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<User> getUserFromMedia(Media media){
        List<Review> reviewList = reviewRepository.findByMediaId(media.getId()).orElseThrow(MediaWithUserNoException::new);

        if (reviewList == null || reviewList.isEmpty()) {
            return Collections.emptyList();
        }

        return reviewList.stream().map(Review::getUser).toList();
    }

    public Media saveMedia(Media media){
        if (mediaRepository.findById(media.getId()).isPresent()) {
            throw new MediaAlreadyExistsException();
        }

        return mediaRepository.save(media);
    }

}
