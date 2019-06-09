package com.crossover.mobiliza.presentation.dto;

import com.crossover.mobiliza.business.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String googleId;

    private String googleIdToken;

    private Boolean lastUsedAsOng;

    private Long idOng;

    private Long idVoluntario;

    public UserDto(User user) {
        this.id = user.getId();
        this.googleId = user.getGoogleId();
        this.lastUsedAsOng = user.isLastUsedAsOng();
        this.idOng = user.getOng() == null ? null : user.getOng().getId();
        this.idVoluntario = user.getVoluntario() == null ? null : user.getVoluntario().getId();
    }

}
