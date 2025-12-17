package com.gui.diarioOnline.business.service;

import com.gui.diarioOnline.controller.dto.DeleteMediaRequestDTO;
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

@Service
public class ReviewService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewFromUserAndMediaId(String email, String mediaId) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Optional<Review> optionalReview = reviewRepository.findByUserIdAndMediaId(userId, mediaId);

        return optionalReview.orElseThrow(ReviewNotFoundException::new);

    }

    public Review getReviewFromUserAndMediaBusinessId(String email, String mediaBusinessId) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();
        Optional<Game> gameOptional = mediaRepository.findByBusinessId(mediaBusinessId);

        if(gameOptional.isPresent()){
            Game game = gameOptional.get();
            Optional<Review> optionalReview = reviewRepository.findByUserIdAndMediaId(userId, game.getId());
            return optionalReview.orElse(null);
        }

        return null;

    }

    public List<Review> getReviewFromUser(String email) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Optional<List<Review>> optionalReview = reviewRepository.findByUserId(userId);

        return optionalReview.orElseThrow(ReviewNotFoundException::new);

    }

    public Review saveReview(Media media, User user, String comments, Double ratings) {
        Optional<Review> reviewListOptional = reviewRepository.findByUserIdAndMediaId(user.getId(), media.getId());

        if (reviewListOptional.isPresent()) {
            Review review = reviewListOptional.get();
            review.setRating(ratings);
            review.setComments(comments);
            return reviewRepository.save(review);
        }

        return reviewRepository.save(Review.builder().user(user).media(media).comments(comments).rating(ratings).build());
    }

    public List<Media> getMediaFromUser(String email) {
        String userId = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        Optional<List<Review>> optionalReviews = reviewRepository.findByUserId(userId);

        return optionalReviews.orElse(Collections.emptyList())
                .stream()
                .map(Review::getMedia)
                .toList();
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
        Optional<Game> gameOptional = mediaRepository.findByBusinessId(saveMediaRequestDTO.mediaRequestDTO().gameId());
        Game game;
        game = gameOptional.orElseGet(() -> mediaRepository.save(((Game) createMediaFromDTO(saveMediaRequestDTO.mediaRequestDTO()))));
        return this.saveReview(game, user, saveMediaRequestDTO.comments(), saveMediaRequestDTO.rating());

    }

    @Transactional
    public void deleteGameFromUser(DeleteMediaRequestDTO deleteMediaRequestDTO) {
        User user = userRepository.findByEmail(deleteMediaRequestDTO.email()).orElseThrow(UserNotFoundException::new);
        Media media = mediaRepository.findByBusinessId(deleteMediaRequestDTO.gameId()).orElseThrow(MediaNotFoundException::new);
        Review review = this.getReviewFromUserAndMediaId(user.getEmail(), media.getId());
        reviewRepository.delete(review);
        this.deleteMediaWithNoPreview(media);
    }

    @Transactional
    public void deleteMediaFromUser(String email, String id) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Media media = mediaRepository.findById(id).orElseThrow(MediaNotFoundException::new);
        Review review = this.getReviewFromUserAndMediaId(user.getEmail(), media.getId());
        reviewRepository.delete(review);
        this.deleteMediaWithNoPreview(media);
    }

    private Media createMediaFromDTO(MediaRequestDTO mediaRequestDTO) {
        if ("GAME".equalsIgnoreCase(mediaRequestDTO.type())) {
            return Game.builder()
                    .businessId(mediaRequestDTO.gameId())
                    .name(mediaRequestDTO.name())
                    .summary(mediaRequestDTO.summary())
                    .cover(mediaRequestDTO.cover())
                    .build();
        }
        throw new IllegalArgumentException("Tipo de mídia desconhecido: " + mediaRequestDTO.type());
    }

    private void deleteMediaWithNoPreview(Media media) {
        Optional<List<Review>> reviewsFromMedia = reviewRepository.findByMediaId(media.getId());
        if (reviewsFromMedia.isPresent()) {
            List<Review> lista = reviewsFromMedia.get();
            if (lista.isEmpty()) {
                mediaRepository.deleteById(media.getId());
            }
        }
    }
}
