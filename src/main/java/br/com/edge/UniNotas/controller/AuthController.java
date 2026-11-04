package br.com.edge.UniNotas.controller;

import br.com.edge.UniNotas.config.TokenProvider;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.IStudentyRepository;
import br.com.edge.UniNotas.dto.LoginDto;
import br.com.edge.UniNotas.dto.TokenDto;
import br.com.edge.UniNotas.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final IStudentyRepository studentRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@Valid @RequestBody LoginDto loginDto) throws NotFoundException {
        StudentEntity student = studentRepository.findByEmail(loginDto.getEmail());

        if (student == null) {
            throw new NotFoundException("Usuário não encontrado com o e-mail informado");
        }

        String token = tokenProvider.gerarToken(student.getEmail());
        return new TokenDto(token);
    }
}
