package br.com.edge.UniNotas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentDto {

    private Integer id;

    @NotBlank(message = "O nome do aluno é obrigatório")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;

    @NotNull(message = "A matrícula é obrigatória")
    @Positive(message = "A matrícula deve ser um número positivo")
    private Integer matricula;

    @NotBlank(message = "A faculdade é obrigatória")
    private String college;

    @NotBlank(message = "O curso é obrigatório")
    private String course;
}
