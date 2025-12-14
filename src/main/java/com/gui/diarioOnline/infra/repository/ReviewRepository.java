package com.gui.diarioOnline.infra.repository;

import com.gui.diarioOnline.infra.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Optional<List<Review>> findByUserId(String id);

    Optional<List<Review>> findByMediaId(String id);

    void deleteByUserId(String id);

    // Método para encontrar uma review específica entre um user e uma media
    Optional<Review> findByUserIdAndMediaId(String id, String mediaId);
}
