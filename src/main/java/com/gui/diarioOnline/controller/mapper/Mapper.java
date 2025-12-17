package com.gui.diarioOnline.controller.mapper;

import com.gui.diarioOnline.business.service.MediaService;
import com.gui.diarioOnline.business.service.ReviewService;
import com.gui.diarioOnline.business.service.UserService;
import com.gui.diarioOnline.controller.dto.GameResponseDTO;
import com.gui.diarioOnline.controller.dto.MediaListUpdateResponseDTO;
import com.gui.diarioOnline.controller.dto.UserResponseDTO;
import com.gui.diarioOnline.infra.entity.Media;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

    @Autowired
    private UserService userService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ReviewService reviewService;

    public UserResponseDTO userToResponse (User user){
        List<Review> reviews = reviewService.getReviewFromUser(user.getEmail());
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                reviews.stream().map(this::createMediaDTOFromReview).toList(),
                user.getRoles()
        );
    }

    public MediaListUpdateResponseDTO MediaToResponse (Media media, String email){
        Review review = reviewService.getReviewFromUserAndMediaBusinessId(email,((Game)media).getBusinessId());
        return new MediaListUpdateResponseDTO(
                media.getId(),
                ((Game)media).getBusinessId(),
                media.getName(),
                media.getSummary(),
                media.getCover(),
                review != null? review.getRating(): null,
                review != null? review.getComments(): null
        );
    }
    public GameResponseDTO createMediaDTOFromReview(Review review) {
        if(review.getMedia().getType().equals("GAME")){
            return new GameResponseDTO(
                    review.getMedia().getId(),
                    ((Game)review.getMedia()).getBusinessId(),
                    review.getMedia().getName(),
                    review.getMedia().getSummary(),
                    review.getMedia().getCover(),
                    review.getRating(),
                    review.getComments()
            );
        }

        throw new IllegalArgumentException("Tipo de mídia desconhecido: " + review.getMedia().getType());
    }
}
