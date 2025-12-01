package com.gui.diarioOnline.infra.model;

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


    @Indexed(unique = true)
    private Long gameId;

    @Override
    public String getType(){
        return "GAME";
    }


}
