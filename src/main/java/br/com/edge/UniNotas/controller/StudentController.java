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
@RequestMapping("/v1/students")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> findAllStudents() {
        return studentService.findAllStudents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@Valid @RequestBody StudentDto studentDto) throws NotFoundException, BadRequestException {
        studentService.saveStudent(studentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDto studentDto) throws NotFoundException, BadRequestException {
        studentService.updateStudent(studentDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Integer id) throws NotFoundException {
        studentService.deleteStudent(id);
    }
}
