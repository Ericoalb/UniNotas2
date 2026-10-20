package br.com.edge.UniNotas.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentDto {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotNull
    private Integer matricula;
    @NotBlank
    private String college;
    @NotBlank
    private String course;
}
