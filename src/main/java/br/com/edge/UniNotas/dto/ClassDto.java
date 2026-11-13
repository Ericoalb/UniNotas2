package br.com.edge.UniNotas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ClassDto {

    private Integer id;

    @NotBlank(message = "O nome da faculdade é obrigatório")
    private String name;

    @NotBlank(message = "O curso é obrigatório")
    private String course;
}
