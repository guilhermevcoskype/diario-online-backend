package com.gui.diarioOnline.infra.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    private String id;

    @DBRef
    @NotNull(message = "O usuário na review não pode ser nulo.")
    private User user;

    @DBRef
    @NotNull(message = "A mídia na review não pode ser nula.")
    private Media media;

    private Double rating;
    private String comments;
}