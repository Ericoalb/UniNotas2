package br.com.edge.UniNotas.handler;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse {
    private String message;
    private Integer status;
}
