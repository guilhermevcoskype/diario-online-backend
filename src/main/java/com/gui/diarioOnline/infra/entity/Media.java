package com.gui.diarioOnline.infra.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "media")
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public abstract class Media {

    private String id;
    private String name;
    private String summary;
    private String cover;

    public abstract String getType();
}
