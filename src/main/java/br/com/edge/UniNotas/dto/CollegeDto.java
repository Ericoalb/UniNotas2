package br.com.edge.UniNotas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CollegeDto {

    private Integer id;

    @NotBlank(message = "O nome da faculdade é obrigatório")
    private String name;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "O CNPJ deve estar no formato 00.000.000/0000-00")
    private String cnpj;
}
