package br.com.edge.UniNotas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {

    @NotBlank(message = "O refresh token é obrigatório")
    private String refreshToken;
}
