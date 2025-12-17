package com.gui.diarioOnline.infra.model;

import com.gui.diarioOnline.infra.entity.Media;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Game extends Media {

    @NotBlank(message = "O ID de negócio (businessId) é obrigatório para Games.")
    @Indexed(unique = true)
    private String businessId;

    @Override
    public String getType(){
        return "GAME";
    }


}
