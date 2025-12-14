package com.gui.diarioOnline.controller.mapper;

import com.gui.diarioOnline.business.service.MediaService;
import com.gui.diarioOnline.business.service.ReviewService;
import com.gui.diarioOnline.business.service.UserService;
import com.gui.diarioOnline.controller.dto.MediaListUpdateResponseDTO;
import com.gui.diarioOnline.controller.dto.UserResponseDTO;
import com.gui.diarioOnline.infra.entity.Media;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Autowired
    private UserService userService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ReviewService reviewService;

    public UserResponseDTO userToResponse (User user){
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                reviewService.getMediaFromUser(user.getEmail()),
                user.getRoles()
        );
    }

    public MediaListUpdateResponseDTO MediaToResponse (Media media, String email){
        Review review = reviewService.getReviewFromUserAndMedia(email,((Game)media).getGameId());
        return new MediaListUpdateResponseDTO(
                media.getName(),
                media.getSummary(),
                media.getCover(),
                review.getRating(),
                review.getComments()
        );

    }
}
