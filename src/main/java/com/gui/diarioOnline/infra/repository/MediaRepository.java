package com.gui.diarioOnline.infra.repository;

import com.gui.diarioOnline.infra.entity.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String> {

}
