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

    @NotBlank
    private String name;
    @NotBlank
    private String course;
}
