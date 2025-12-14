package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.MediaRequestDTO;
import com.gui.diarioOnline.controller.dto.SaveMediaRequestDTO;
import com.gui.diarioOnline.controller.dto.UserMediaUpdateRequestDTO;
import com.gui.diarioOnline.infra.entity.Media;
import com.gui.diarioOnline.infra.entity.Review;
import com.gui.diarioOnline.infra.entity.User;
import com.gui.diarioOnline.infra.exception.*;
import com.gui.diarioOnline.infra.model.Game;
import com.gui.diarioOnline.infra.repository.MediaRepository;
import com.gui.diarioOnline.infra.repository.ReviewRepository;
import com.gui.diarioOnline.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewFromUserAndMedia(String email, String mediaId) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Optional<Review> optionalReview = reviewRepository.findByUserIdAndMediaId(userId, mediaId);

        return optionalReview.orElseThrow(ReviewNotFoundException::new);

    }

    public Review saveReview(Media media, User user, String comments, Double ratings) {
        Optional<Review> reviewListOptional = reviewRepository.findByUserIdAndMediaId(user.getId(), ((Game) media).getGameId());

        if (reviewListOptional.isPresent()) {
            throw new ReviewAlreadyExistsException();
        }

        return reviewRepository.save(Review.builder().user(user).media(media).comments(comments).rating(ratings).build());
    }

    public List<Media> getMediaFromUser(String email) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Optional<List<Review>> optionalReviews = reviewRepository.findByUserId(userId);

        return optionalReviews
                .orElse(Collections.emptyList())
                .stream()
                .map(Review::getMedia)
                .collect(Collectors.toList());
    }

    @Transactional
    public Review updateMediaOnUser(UserMediaUpdateRequestDTO userMediaUpdateRequestDTO) {
        String userId = userRepository.findByEmail(userMediaUpdateRequestDTO.email()).orElseThrow(UserNotFoundException::new).getId();
        Review reviewToUpdate = reviewRepository.findByUserIdAndMediaId(userId, userMediaUpdateRequestDTO.media().gameId()).orElseThrow(UserWithNoMediaException::new);
        reviewToUpdate.setComments(userMediaUpdateRequestDTO.media().comments());
        reviewToUpdate.setRating(userMediaUpdateRequestDTO.media().rating());

        return reviewRepository.save(reviewToUpdate);
    }

    @Transactional
    public Review saveMediaOnUser(SaveMediaRequestDTO saveMediaRequestDTO) {
        User user = userRepository.findByEmail(saveMediaRequestDTO.email()).orElseThrow(UserNotFoundException::new);
        Optional<Media> mediaOptional = mediaRepository.findById(saveMediaRequestDTO.mediaRequestDTO().gameId());
        Media media;
        media = mediaOptional.orElseGet(() -> mediaRepository.save(createMediaFromDTO(saveMediaRequestDTO.mediaRequestDTO())));
        return this.saveReview(media, user, saveMediaRequestDTO.comments(), saveMediaRequestDTO.rating());

    }

    private Media createMediaFromDTO(MediaRequestDTO mediaRequestDTO) {
        if ("GAME".equalsIgnoreCase(mediaRequestDTO.type())) {
            return Game.builder()
                    .gameId(mediaRequestDTO.gameId())
                    .name(mediaRequestDTO.name())
                    .summary(mediaRequestDTO.summary())
                    .cover(mediaRequestDTO.cover())
                    .build();
        }
        throw new IllegalArgumentException("Tipo de mídia desconhecido: " + mediaRequestDTO.type());
    }
}
