package br.com.edge.UniNotas.controller;

import br.com.edge.UniNotas.database.model.ClassEntity;
import br.com.edge.UniNotas.dto.ClassDto;
import br.com.edge.UniNotas.dto.StudentDto;
import br.com.edge.UniNotas.exception.BadRequestException;
import br.com.edge.UniNotas.exception.NotFoundException;
import br.com.edge.UniNotas.service.ClassService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/class")
@Validated
public class ClassController {

    private final ClassService classService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClassDto> findAll()
    {
        return classService.findAll();
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> listStudent(@PathVariable Integer id) throws NotFoundException {
        return classService.listStudent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody ClassDto classDto) throws NotFoundException, BadRequestException {
        classService.save(classDto);
    }

    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable Integer id, @Valid @RequestBody ClassDto classDto) throws NotFoundException {
        classService.update(id, classDto);
    }

    @DeleteMapping("/delete/{id1}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id1) throws NotFoundException {
        classService.delete(id1);
    }

}
