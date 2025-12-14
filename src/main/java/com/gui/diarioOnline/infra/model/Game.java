package com.gui.diarioOnline.infra.model;

import com.gui.diarioOnline.infra.entity.Media;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Game extends Media {


    @Id
    private String gameId;

    @Override
    public String getType(){
        return "GAME";
    }


}
