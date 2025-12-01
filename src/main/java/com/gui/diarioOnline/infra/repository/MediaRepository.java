package com.gui.diarioOnline.infra.repository;

import com.gui.diarioOnline.infra.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String> {

}
