package com.gui.diarioOnline.infra.repository;

import com.gui.diarioOnline.infra.entity.Media;
import com.gui.diarioOnline.infra.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MediaRepository extends MongoRepository<Media, String> {

    Optional<Game> findByIdAndBusinessId (String id, String businessId);
    Optional<Game> findByBusinessId (String businessId);

}
