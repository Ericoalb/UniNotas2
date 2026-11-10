package br.com.edge.UniNotas.controller;

import br.com.edge.UniNotas.dto.LoginDto;
import br.com.edge.UniNotas.dto.RefreshTokenRequestDto;
import br.com.edge.UniNotas.dto.TokenDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import br.com.edge.UniNotas.service.StudentService;
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

    private final StudentService studentService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@Valid @RequestBody LoginDto loginDto) throws NotFoundException {
        return studentService.login(loginDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto refreshToken(@Valid @RequestBody RefreshTokenRequestDto requestDto) throws NotFoundException, BadRequestException {
        return studentService.refreshToken(requestDto);
    }
}
