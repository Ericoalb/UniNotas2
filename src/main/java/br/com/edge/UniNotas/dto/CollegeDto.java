package br.com.edge.UniNotas.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CollegeDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cnpj;
}
