package br.com.edge.UniNotas.controller;

import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import br.com.edge.UniNotas.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody StudentDto studentDto) throws NotFoundException, BadRequestException {
        studentService.save(studentDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> findAll()
    {
        return studentService.findAll();
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable Integer id, @Valid @RequestBody StudentDto studentDto) throws NotFoundException, BadRequestException {
        studentService.update(studentDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws NotFoundException {
        studentService.delete(id);
    }

}
