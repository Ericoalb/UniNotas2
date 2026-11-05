package br.com.edge.UniNotas.controller;

import br.com.edge.UniNotas.dto.ClassDto;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import br.com.edge.UniNotas.service.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/class")
@Validated
public class ClassController {

    private final ClassService classService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClassDto> findAllClasses() {
        return classService.findAllClasses();
    }

    @GetMapping("/{id}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> listStudentsByClassId(@PathVariable Integer id) throws NotFoundException {
        return classService.listStudentsByClassId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClass(@Valid @RequestBody ClassDto classDto) throws NotFoundException, BadRequestException {
        classService.saveClass(classDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateClass(@PathVariable Integer id, @Valid @RequestBody ClassDto classDto) throws NotFoundException {
        classService.updateClass(id, classDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable Integer id) throws NotFoundException {
        classService.deleteClass(id);
    }
}
